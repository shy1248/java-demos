/**
 * @Date : 2021-03-29 14:37:30
 * @Author : shy
 * @Email : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version : v1.0
 * @Description : 使用ListState存储offset模拟Kafka的offset维护
 */
package me.shy.demo.state;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.SystemUtils;
import org.apache.flink.api.common.RuntimeExecutionMode;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.common.state.ListState;
import org.apache.flink.api.common.state.ListStateDescriptor;
import org.apache.flink.runtime.state.FunctionInitializationContext;
import org.apache.flink.runtime.state.FunctionSnapshotContext;
import org.apache.flink.runtime.state.filesystem.FsStateBackend;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.checkpoint.CheckpointedFunction;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.RichParallelSourceFunction;

public class OperatorStateDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // 设置并行度为1，方便观察
        env.setParallelism(1);
        env.setRuntimeMode(RuntimeExecutionMode.AUTOMATIC);
        //每隔1s执行一次Checkpoint
        env.enableCheckpointing(1000);
        // 指定 checkpoint 的存放位置
        String checkpointDirectory = null;
        if (SystemUtils.IS_OS_WINDOWS) {
            checkpointDirectory = "file:///c/Users/shy/Downloads/flink-demo-checkpoint";
        } else {
            checkpointDirectory = "file:///Users/shy/Downloads/flink-demo-checkpoint";
        }
        env.setStateBackend(
                new FsStateBackend(checkpointDirectory));
        env.getCheckpointConfig().enableExternalizedCheckpoints(
                CheckpointConfig.ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION);
        env.getCheckpointConfig().setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE);
        // 固定延迟重启策略: 程序出现异常的时候，重启3次，每次延迟3秒钟重启，超过3次，程序退出
        // 注意：重启 3 次包含了每个并行任务的次数，如果当前并行度为 4，则该重启次数必须大于 4 才能看到效果
        env.setRestartStrategy(RestartStrategies.fixedDelayRestart(3, 3000));

        env.addSource(new KafkaSourceMoke()).printToErr();

        env.execute();
    }
}


/**
 * Kafka consumer 模拟，使用 ListState 记录分区偏移量并存储到 checkpoint 中
 */
class KafkaSourceMoke extends RichParallelSourceFunction<String> implements CheckpointedFunction {
    private static final long serialVersionUID = 6984667696723422595L;
    // 声明 ListState，存放格式为 map，其中 map 的 key 为 kafka 分区的 id，map 的 value 为分区的 offset
    private ListState<Map<Integer, Long>> offsetState = null;
    // 存放当前的偏移量
    private Map<Integer, Long> offset = new HashMap<>();
    boolean flag = true;

    // 初始化状态
    @Override
    public void initializeState(FunctionInitializationContext context) throws Exception {
        // 创建状态描述器
        @SuppressWarnings("unchecked")
        ListStateDescriptor<Map<Integer, Long>> stateDescriptor =
                new ListStateDescriptor<Map<Integer, Long>>("OffsetState",
                        (Class<Map<Integer, Long>>) offset.getClass());
        // 使用状态描述器初始化 ListState
        offsetState = context.getOperatorStateStore().getListState(stateDescriptor);
    }

    @Override
    public void run(SourceContext<String> ctx) throws Exception {
        while (flag) {
            // 从 ListState 中获取当前状态的值
            Iterator<Map<Integer, Long>> iterator = offsetState.get().iterator();
            // 由于此处只存储了一个 map 在 list 中，因此不需要迭代
            if (iterator.hasNext()) {
                offset = iterator.next();
            }
            // 获取子任务 ID，也即分区的 ID
            int subTaskId = getRuntimeContext().getIndexOfThisSubtask();
            Long currentOffset = offset.get(subTaskId);
            if (null == currentOffset) {
                currentOffset = 1L;
            } else {
                currentOffset += 1L;
            }
            offset.put(subTaskId, currentOffset);
            ctx.collect(String.format("Partition %d consume's offset is: %d", subTaskId,
                    currentOffset));
            // 模拟数据产生间隔
            TimeUnit.MILLISECONDS.sleep(1000);
            // 模拟故障
            if (currentOffset % 5 == 0) {
                throw new Exception("A bug Trigged...");
            }
        }
    }

    @Override
    public void snapshotState(FunctionSnapshotContext context) throws Exception {
        // 该方法会将内存中状态信息刷新到 checkpoind 指定的 backend 中并清空内存中状态数据
        offsetState.clear();
        // 更新状态数据
        offsetState.add(offset);
    }

    @Override
    public void cancel() {
        flag = false;
    }
}
