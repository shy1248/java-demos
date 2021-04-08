/**
 * @Date        : 2021-04-07 20:27:02
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : flink 流合并示例
 *
 * Connect：连接 2 个流，2 个 原始流的数据可以不相同。
 */
package me.shy.flink.transformission;

import org.apache.flink.streaming.api.datastream.ConnectedStreams;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.co.CoMapFunction;

public class StreamConnectDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStreamSource<String> letters = env.fromElements("a", "b", "c", "d", "e");
        DataStreamSource<Long> numbers = env.fromSequence(1, 5);

        ConnectedStreams<String, Long> connected = letters.connect(numbers);
        SingleOutputStreamOperator<String> result = connected.map(new CoMapFunction<String, Long, String>() {
            private static final long serialVersionUID = 1L;

            @Override
            public String map1(String value) throws Exception {
                return "Letter: " + value;
            }

            @Override
            public String map2(Long value) throws Exception {
                return "Number: " + value;
            }

        });

        result.printToErr("Connected demo application");

        env.execute();
    }
}
