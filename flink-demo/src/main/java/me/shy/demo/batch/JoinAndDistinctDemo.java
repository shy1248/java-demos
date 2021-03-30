package me.shy.demo.batch;

import java.util.Arrays;
import java.util.List;

import org.apache.flink.api.common.functions.JoinFunction;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.operators.JoinOperator.EquiJoin;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.tuple.Tuple4;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 */
public class JoinAndDistinctDemo {

    public static void main(String[] args) throws Exception {
        ExecutionEnvironment environment = ExecutionEnvironment.getExecutionEnvironment();
        List<Tuple2<Integer, String>> names = Arrays.asList(new Tuple2<Integer, String>(1, "Jerry"),
            new Tuple2<Integer, String>(2, "Tom"), new Tuple2<Integer, String>(3, "Mickey"));

        List<Tuple3<Integer, String, String>> attrs =
            Arrays.asList(new Tuple3<Integer, String, String>(1, "mouse", "female"),
                new Tuple3<Integer, String, String>(2, "cat", "male"),
                new Tuple3<Integer, String, String>(3, "mouse", "male"),
                new Tuple3<Integer, String, String>(4, "dog", "unkown"));

        DataSource<Tuple2<Integer, String>> namesSource = environment.fromCollection(names);
        DataSource<Tuple3<Integer, String, String>> attrsSource = environment.fromCollection(attrs);
        EquiJoin<Tuple2<Integer, String>, Tuple3<Integer, String, String>,
            Tuple4<Integer, String, String, String>> joins = namesSource.join(attrsSource).where(0).equalTo(0)

                // 此处 with 也可使用 map 代替
                .with(new JoinFunction<Tuple2<Integer, String>, Tuple3<Integer, String, String>,
                    Tuple4<Integer, String, String, String>>() {
                    private static final long serialVersionUID = 1L;

                    @Override
                    public Tuple4<Integer, String, String, String> join(Tuple2<Integer, String> first,
                        Tuple3<Integer, String, String> second) throws Exception {
                        return new Tuple4<Integer, String, String, String>(first.f0, first.f1, second.f1, second.f2);
                    }
                });

        //            .with(new FlatJoinFunction<Tuple2<Integer, String>, Tuple3<Integer, String, String>, Tuple4<Integer,
        //                String, String, String>>() {
        //                @Override
        //                public void join(Tuple2<Integer, String> first, Tuple3<Integer, String, String> second,
        //                    Collector<Tuple4<Integer, String, String, String>> out) throws Exception {
        //                    out.collect(new Tuple4<Integer, String, String, String>(first.f0, first.f1, second.f1, second.f2));
        //                }
        //            });

        //            .map(
        //                new MapFunction<Tuple2<Tuple2<Integer, String>, Tuple3<Integer, String, String>>, Tuple4<Integer, String, String, String>>() {
        //                    @Override
        //                    public Tuple4<Integer, String, String, String> map(
        //                        Tuple2<Tuple2<Integer, String>, Tuple3<Integer, String, String>> value) throws Exception {
        //                        return new Tuple4<Integer, String, String, String>(value.f0.f0, value.f0.f1, value.f1.f1,
        //                            value.f1.f2);
        //                    }
        //                });

        joins.print();

        System.out.println("==============================================================");

        joins.distinct(2).print();

    }
}
