/**
 * @Date        : 2021-04-08 21:55:25
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : flink 分布式缓存系统使用示例
 *
 * Flink提供了一个类似于Hadoop的分布式缓存，让并行运行实例的函数可以在本地访问。
 * 这个功能可以被使用来分享外部静态的数据，例如：机器学习的逻辑回归模型等。
 *
 * 分布式缓存是将文件缓存到各个TaskManager节点上。
 */
package me.shy.flink.transformission;

import java.io.File;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.tuple.Tuple4;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;

public class DistributedCacheDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // 注册分布式缓存文件
        // env.registerCachedFile("hdfs:///path/file", "cachefilename");
        env.registerCachedFile("data/input/stu.txt", "stu_cached");

        SingleOutputStreamOperator<Tuple4<Integer, String, String, Integer>> result = env
                .fromElements(Tuple3.of(1, "Chinese", 85), Tuple3.of(2, "English", 79), Tuple3.of(3, "Math", 96))
                .process(
                        new ProcessFunction<Tuple3<Integer, String, Integer>, Tuple4<Integer, String, String, Integer>>() {
                            private static final long serialVersionUID = 1L;
                            Map<Integer, String> studentsMap = new HashMap<>();

                            @Override
                            public void open(Configuration parameters) throws Exception {
                                // 加载分布式缓存文件
                                File cache = getRuntimeContext().getDistributedCache().getFile("stu_cached");
                                List<String> lines = FileUtils.readLines(cache, Charset.forName("utf-8"));
                                for (String line : lines) {
                                    if (null != line && !"".equals(line.trim())) {
                                        String[] fields = line.split(",");
                                        studentsMap.put(Integer.parseInt(fields[0]), fields[1]);
                                    }
                                }

                            }

                            @Override
                            public void processElement(Tuple3<Integer, String, Integer> in,
                                    ProcessFunction<Tuple3<Integer, String, Integer>, Tuple4<Integer, String, String, Integer>>.Context ctx,
                                    Collector<Tuple4<Integer, String, String, Integer>> out) throws Exception {
                                out.collect(Tuple4.of(in.f0, studentsMap.get(in.f0), in.f1, in.f2));
                            }
                        });

        result.printToErr();

        env.execute("Distributed cache file demo");
    }
}
