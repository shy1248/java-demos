/**
 * @Date        : 2021-04-07 21:07:56
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : rebalance 分区，可解决数据倾斜问题
 */
package me.shy.flink.transformission;

import org.apache.flink.api.common.RuntimeExecutionMode;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class RebalanceDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setRuntimeMode(RuntimeExecutionMode.BATCH).setParallelism(3);
        DataStreamSource<Long> numbers = env.fromSequence(0, 100);

        // 统计每个 subtask 中数据的和
        // 不使用 rebalance 分区，有可能出现数据倾斜问题
        numbers.filter(num -> num > 10).map(new RichMapFunction<Long,Tuple2<Integer,Long>>(){
            private static final long serialVersionUID = 1L;

            @Override
            public Tuple2<Integer, Long> map(Long value) throws Exception {
                int partitionId = getRuntimeContext().getIndexOfThisSubtask();
                return Tuple2.of(partitionId, value);
            }
        }).keyBy(t -> t.f0).sum(1).printToErr("No rebalanced");

        // 先使用 rebalance 分区后再求和
        numbers.filter(num -> num > 10).rebalance().map(new RichMapFunction<Long,Tuple2<Integer,Long>>(){
            private static final long serialVersionUID = 1L;

            @Override
            public Tuple2<Integer, Long> map(Long value) throws Exception {
                int partitionId = getRuntimeContext().getIndexOfThisSubtask();
                return Tuple2.of(partitionId, value);
            }
        }).keyBy(t -> t.f0).sum(1).printToErr("Rebalanced");

        // 观察每个分区最后求出的和，可以看到使用了 rebalance 之后每个分区的和比较接近，说明数据没有出现倾斜
        env.execute("Rebalance demo application");
    }
}
