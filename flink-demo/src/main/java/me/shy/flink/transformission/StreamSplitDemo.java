/**
 * @Date        : 2021-04-07 20:38:23
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : flink 流拆分示例
 *
 * 使用 OutputTag 和 Select 来完成流拆分。
 */
package me.shy.flink.transformission;

import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;
import org.apache.flink.util.OutputTag;

public class StreamSplitDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<Long> numbers = env.fromSequence(0, 10);

        OutputTag<Long> oddNumbers = new OutputTag<Long>("odds", TypeInformation.of(Long.class));
        OutputTag<Long> evenNumbers = new OutputTag<Long>("evens", TypeInformation.of(Long.class));
        SingleOutputStreamOperator<Long> splited = numbers.process(new ProcessFunction<Long, Long>() {
            private static final long serialVersionUID = 1L;

            @Override
            public void processElement(Long in, ProcessFunction<Long, Long>.Context ctx, Collector<Long> out)
                    throws Exception {
                if(in % 2 == 0){
                    ctx.output(evenNumbers, in);
                } else {
                    ctx.output(oddNumbers, in);
                }
            }
        });

        DataStream<Long> oddNumbersOutput = splited.getSideOutput(oddNumbers);
        DataStream<Long> evenNumbersOutput = splited.getSideOutput(evenNumbers);

        oddNumbersOutput.printToErr("Odd number");
        evenNumbersOutput.printToErr("Even number");

        env.execute("Split demo application");
    }
}
