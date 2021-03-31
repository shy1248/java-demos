package me.shy.flink.datasink;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;

import io.lettuce.core.RedisURI;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection;
import io.lettuce.core.cluster.api.sync.RedisClusterCommands;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: flink sink operator for redis
 * <p>
 * 要自定义 sink，类似于 source，需要继承 SinkFunction<T> 或者 继承自 RichSinkDunction<T>
 * <p>
 */
public class RedisSinkDemo {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        environment.setParallelism(1);
        DataStreamSource<String> textStream = environment.socketTextStream("localhost", 9999);

        textStream.map(value -> new Tuple2<>("l_words", value)).addSink(new MyRedisSink());

        environment.execute("RedisSinkDemo");
    }

    public static class MyRedisSink extends RichSinkFunction<Tuple2<String, String>> {
        private static final long serialVersionUID = -1848960554825750510L;
        private RedisClusterClient client = null;
        private StatefulRedisClusterConnection<String, String> connection = null;
        private RedisClusterCommands<String, String> syncCommand = null;

        @Override
        public void open(Configuration parameters) throws Exception {
            RedisURI redisURI = RedisURI.Builder.redis("demos01", 6379).build();

            //            List<RedisURI> SERVERS_URIS = new ArrayList<RedisURI>();
            //            SERVERS_URIS.add(RedisURI.create("redis://demos01:6379/0"));
            //            SERVERS_URIS.add(RedisURI.create("redis://demos01:6380/0"));
            //            SERVERS_URIS.add(RedisURI.create("redis://demos01:6381/0"));
            //            SERVERS_URIS.add(RedisURI.create("redis://demos01:6382/0"));
            //            SERVERS_URIS.add(RedisURI.create("redis://demos01:6383/0"));
            //            SERVERS_URIS.add(RedisURI.create("redis://demos01:6384/0"));
            //            SERVERS_URIS.add(RedisURI.create("redis://demos01:6385/0"));
            //            SERVERS_URIS.add(RedisURI.create("redis://demos01:6386/0"));
            //            SERVERS_URIS.add(RedisURI.create("redis://demos01:6387/0"));
            //            SERVERS_URIS.add(RedisURI.create("redis://demos01:6388/0"));
            //            this.client = RedisClusterClient.create(SERVERS_URIS);
            this.client = RedisClusterClient.create(redisURI);
            this.connection = this.client.connect();
            this.syncCommand = connection.sync();
            System.out.println("Redis connect successed!");
        }

        @Override
        public void close() throws Exception {
            this.connection.close();
            this.client.shutdown();
            System.out.println("Redis client closed!");
        }

        @Override
        public void invoke(Tuple2<String, String> value, Context context) throws Exception {
            if (value instanceof Tuple2) {
                String k = ((Tuple2<String, String>)value).f0;
                String v = ((Tuple2<String, String>)value).f1;
                System.out.println("Data recevied: key=" + k + ", value=" + v);
                this.syncCommand.lpush(k, v);
            }
        }
    }
}
