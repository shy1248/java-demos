package me.shy.demo.batch;

import org.apache.flink.api.common.functions.MapPartitionFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.util.Collector;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: flink DataSet api - mapPartition
 * <p>
 * mapPatition 算子类似于 map 算子，不过是针对 flink 的 micro batch api，即 spark 的处理方式
 * mapPartition 是一个分区一个分区的处理，主要应用场景是当有数据要与外部数据交互时，为了减少与外部
 * 平台进行资源连接操作。例如，当要与数据库进行交互时，如果使用 map 算子，则每条数据都要进行一次连
 * 接，而使用 mapPartition 时，则是一个分区打开一个连接。
 * <p>
 * 如果没有这种特殊需求，则不建议使用，因为 mapPartition 会将一个分区的数据集中在一个节点上处理，
 * 性能会有所下降
 */
public class MapPartitionDemo {
    public static void main(String[] args) throws Exception {
        ExecutionEnvironment environment = ExecutionEnvironment.getExecutionEnvironment();
        List<String> lines = Arrays.asList("hello you", "hello me", "hello scala");

        DataSet<String> source = environment.fromCollection(lines);

        //        source.map(new MapFunction<String, String>() {
        //            @Override
        //            public String map(String value) throws Exception {
        //                // 打开数据库连接 -- 注意，此处是每条数据都要进行，极大的浪费资源
        //                // 处理数据
        //                // 关闭连接
        //                return null;
        //            }
        //        });

        source.mapPartition(new MapPartitionFunction<String, String>() {
            private static final long serialVersionUID = 1L;

            @Override
            public void mapPartition(Iterable<String> values, Collector<String> out) throws Exception {
                // 打开数据库连接 -- 注意，此处是每个分区打开一次连接
                // 处理数据
                // 关闭连接
                Iterator<String> it = values.iterator();
                while (it.hasNext()) {
                    String[] words = it.next().split("\\s+");
                    for (String word : words) {
                        out.collect(word);
                    }
                }
            }
        }).print();
    }
}
