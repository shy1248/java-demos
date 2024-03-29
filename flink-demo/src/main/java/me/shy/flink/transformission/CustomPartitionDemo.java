package me.shy.flink.transformission;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.Partitioner;
import org.apache.flink.api.java.tuple.Tuple1;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import me.shy.flink.datasource.CustomSignleDataSource;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: 自定义 flink 流分区
 * <p>
 * flink 分区操作有以下几种：
 * 1.global      -- 全部发往第一个 task
 * 2.broadcast   -- 广播，数据给下游的 task 都发送一份
 * 3.forward     -- 上下游并行度一样时一对一发送
 * 4.shuffle      -- 随机均匀分配
 * 5.rebalence   -- 轮训分配
 * 3.rescale     -- 基于上下游Operator的并行度，将记录以循环的方式输出到下游Operator的每个实例，例如：
 *                  上游并行度是2，下游是4，则上游一个并行度以循环的方式将记录输出到下游的两个并行度上;
 *                  上游另一个并行度以循环的方式将记录输出到下游另两个并行度上。若上游并行度是4，下游并
 *                  行度是2，则上游两个并行度将记录输出到下游一个并行度上；上游另两个并行度将记录输出到下游另一个并行度上
 * 4.custom      -- 自定义分区
 * rebalence 与 rescale 的区别是：
 * 假设当前流的上游分区为2，下游分区为4，那么 rebalence 的2个分区均会向下游的4个分区发送数据，
 * 而 rescale 方式是，上游第一个分区的数据会发送个下游的前2个分区，上游的第二个分区发送数据给
 * 下游分区的后2个分区。因此 rebalence 会处理更多的数据。
 * <p>
 * 自定义分区需要实现 Partitioner<T> 接口
 */
public class CustomPartitionDemo implements Partitioner<String> {
    private static final long serialVersionUID = 1L;

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        // 因为分区是根据奇数和偶数进行分区，因此为了看效果，将作业并行度设置为2，默认并行度为 cpu 的核数
        environment.setParallelism(2);
        DataStream<String> data = environment.addSource(new CustomSignleDataSource()).setParallelism(1);
        // 使用自定义分区前需要将数据类型由 String 转换为 Tuple，且必须指定 Tuple的数据类型
        DataStream<Tuple1<String>> dataTuple = data.map(new MapFunction<String, Tuple1<String>>() {
            private static final long serialVersionUID = -25653726260325105L;

            @Override
            public Tuple1<String> map(String value) throws Exception {
                return new Tuple1<String>(value);
            }
        });
        // 使用 Tuple DataStream 的 partitionCustom 对流进行分区
        dataTuple.partitionCustom(new CustomPartitionDemo(), t -> t.f0)
            // 输出看效果
            .map(new MapFunction<Tuple1<String>, String>() {
                private static final long serialVersionUID = 1L;

                @Override
                public String map(Tuple1<String> value) throws Exception {
                    System.out.println(
                        "Current thread is: " + Thread.currentThread().getId() + ", and the value is: " + value);
                    return value.getField(0).toString();
                }
            }).print();

        environment.execute();

    }

    // 根据基偶数来进行分区
    @Override
    public int partition(String key, int numPartitions) {
        System.out.println("Current partition num is: " + numPartitions);
        Long longKey = Long.parseLong(key);
        return (int)(longKey % 2);
    }
}
