/**
 * @Date        : 2021-04-07 21:25:28
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : flink 累加器示例
 *
 * flink 内置了 IntCounter、LongCounter、DubboleCounter 等几种累加器，这些累加器都实现了 Accumulator 接口
 */
package me.shy.flink.transformission;

import org.apache.flink.api.common.JobExecutionResult;
import org.apache.flink.api.common.accumulators.IntCounter;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;

public class AccumulatorDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<Long> numbers = env.fromSequence(0, 999);

        numbers.process(new ProcessFunction<Long, Void>() {
            private static final long serialVersionUID = 2795002913166799776L;
            // 创建累加器
            private IntCounter accumulator = new IntCounter();
            // 创建普通计数器
            private int counter = 0;

            @Override
            public void open(Configuration parameters) throws Exception {
                // 注册累加器
                getRuntimeContext().addAccumulator("ElementsCounter", accumulator);
            }

            @Override
            public void processElement(Long in, ProcessFunction<Long, Void>.Context ctx, Collector<Void> out)
                    throws Exception {
                // 累加器计数
                this.accumulator.add(1);
                counter += 1;
            }

            @Override
            public void close() throws Exception {
                System.out.println("[Normal Counter]: Total elements counter is " + counter);
            }
        });

        JobExecutionResult jobResult = env.execute("Accumulator demo application");
        // 获取累加器的结果
        Object accumulatorResult = jobResult.getAccumulatorResult("ElementsCounter");
        System.out.println("[Accumulator]: Total elements counter is " + accumulatorResult);
    }
}
