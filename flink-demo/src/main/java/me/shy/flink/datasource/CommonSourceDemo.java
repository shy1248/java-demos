package me.shy.flink.datasource;

import java.util.Arrays;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @Date        : 2021-04-06 10:12:42
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : common source demo
 */
public class CommonSourceDemo {
    public static void main(String[] args) throws Exception {
        // 1: env
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // 2: source
        // 常用于开发测试的数据源
        // env.fromElements(各种类型的可变参数)
        // env.fromCollection(各种类型的集合)
        // env.fromSequence(开始，结束)
        // env.socketTextStream(host, port)
        // env.readTextFile()，读取文件，可读取本地文件、hdfs文件，其中文件可以是gz压缩文件或者目录
        DataStreamSource<String> source = env
                // .fromElements("hello hadoop", "hello flink", "hello spark");
                // .fromCollection(Arrays.asList("hello hadoop", "hello flink", "hello spark"));
                // .fromSequence(0, 100);
                // .socketTextStream("localhost", 9999);
                .readTextFile("hdfs://hadoop-nn-svc:9000/flink-demo/a.txt.gz");

        // 3: transformissions
        SingleOutputStreamOperator<Tuple2<String, Integer>> result = source
                .flatMap((FlatMapFunction<String, Tuple2<String, Integer>>) (line, out) -> {
                    if (null != line && !"".equals(line.trim())) {
                        String[] words = line.split(" ");
                        Arrays.stream(words).forEach(word -> out.collect(Tuple2.of(word, 1)));
                    }
                }).returns(Types.TUPLE(Types.STRING, Types.INT)).keyBy(t -> t.f0).sum(1);

        // 4: sink
        result.printToErr();
        // 5: execute
        env.execute();
    }
}
