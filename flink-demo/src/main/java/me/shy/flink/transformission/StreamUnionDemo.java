/**
 * @Date        : 2021-04-07 20:16:55
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : flink 流合并实例
 *
 * Union：将多个（2个或者2个以上）相同数据类型的流按照先进先出的顺序合并到一个流中，并且不去重。
 */
package me.shy.flink.transformission;

import java.util.Arrays;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class StreamUnionDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStreamSource<String> letters = env.fromElements("a", "b", "c", "d");
        DataStreamSource<String> numbers = env.fromCollection(Arrays.asList("1", "2", "3", "4", "5"));

        DataStream<String> unioned = letters.union(numbers);

        unioned.printToErr("Union demo application");

        env.execute();
    }
}
