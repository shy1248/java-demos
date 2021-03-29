/**
 * @Date        : 2020-10-01 20:44:13
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : -
 */

package me.shy.demo.examples.orderSumExample;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.apache.flink.streaming.api.functions.source.RichParallelSourceFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderSumExample {

    public static final Logger logger = LoggerFactory.getLogger(OrderSumExample.class);

    public static class DataMokeSource extends RichParallelSourceFunction<Tuple2<String, Integer>> {
        private static final long serialVersionUID = 5174231698181464982L;
        private volatile boolean isRunning = true;

        @Override
        public void run(SourceContext<Tuple2<String, Integer>> ctx) throws Exception {
            Random random = new Random();
            while (this.isRunning) {
                TimeUnit.SECONDS.sleep(random.nextInt(5) + 1);
                String key = "商品类别" + (char) ('A' + random.nextInt(3));
                int value = random.nextInt(10) + 1;
                System.out.println(String.format("Emits:\t(%s, %s)", key, value));
                ctx.collect(new Tuple2<String, Integer>(key, value));
            }
        }

        @Override
        public void cancel() {
            this.isRunning = false;
        }
    }

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(2);
        DataStream<Tuple2<String, Integer>> values = env.addSource(new DataMokeSource());

        // values.addSink(new SinkFunction<Tuple2<String, Integer>>() {
        //     private static final long serialVersionUID = -1891660393287006546L;
        //     public void invoke(Tuple2<String, Integer> value, Context ctx) {
        //         System.out.println(String.format("Get:\t(%s, %d)", value.f0, value.f1));
        //     }
        // });

        values.keyBy(0).sum(1).addSink(new SinkFunction<Tuple2<String, Integer>>() {
            private static final long serialVersionUID = -1891660393287006547L;

            public void invoke(Tuple2<String, Integer> value, Context ctx) {
                System.out.println(String.format("Sum by type:\t(%s, %d)", value.f0, value.f1));
            }
        });

            // values.keyBy(0).sum(1)
            // // .keyBy(new KeySelector<Tuple2<String, Integer>, Object>() {
            // //     private static final long serialVersionUID = -1891660393287006548L;

            // //     @Override
            // //     public Object getKey(Tuple2<String, Integer> value) throws Exception {
            // //         return "";
            // //     }
            // // }).reduce(null)
        // .addSink(new SinkFunction<HashMap<String, Integer>>() {
        //     private static final long serialVersionUID = 2191709578885216504L;

        //     public void invoke(HashMap<String, Integer> value, Context ctx) {
        //         // Sum by type
        //         logger.info("Sum by type:\t {}", value);
        //         // Sum global
        //         logger.info("Sum:\t {}", value.values().stream().mapToInt(v -> v).sum());
        //     }
        // });
        env.execute("Order Sum Example");
    }
}

class OrderAggregateFunction
        implements AggregateFunction<Tuple2<String, Integer>, Map<String,Integer>, Map<String, Integer>> {
    private static final long serialVersionUID = 552316042290567963L;

    @Override
    public Map<String, Integer> createAccumulator() {
        return new HashMap<>();
    }

    @Override
    public Map<String, Integer> add(Tuple2<String, Integer> value, Map<String, Integer> accumulator) {
        accumulator.compute(value.f0, (k, v) -> v != null ? v + accumulator.get(k) : v);
        return accumulator;

    }

    @Override
    public Map<String, Integer> getResult(Map<String, Integer> accumulator) {
        return accumulator;
    }

    @Override
    public Map<String, Integer> merge(Map<String, Integer> a, Map<String, Integer> b) {
        b.forEach((k, v) -> a.merge(k, v, (v1, v2) -> v1 + v2));
        return null;
    }

}
