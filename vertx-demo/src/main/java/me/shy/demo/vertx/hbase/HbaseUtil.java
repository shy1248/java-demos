/**
 * @Since: 2019-08-05 20:55:06
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 * @LastTime: 2019-08-06 15:28:50
 */

package me.shy.demo.vertx.hbase;

import com.google.protobuf.ServiceException;
import java.io.IOException;
import java.util.concurrent.Executors;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.zookeeper.ZKConfig;
import org.hbase.async.HBaseClient;

public class HbaseUtil {

    private static Connection connection;
    private static HBaseClient asyncHbaseClient;

    public static Connection getConnection() {
        return connection;
    }

    public static void init(Configuration conf) throws IOException, ServiceException {

        // ExecutorService executor = Executors.newFixedThreadPool(100);
        // connection = ConnectionFactory.createConnection(conf, executor);
        connection = ConnectionFactory.createConnection(conf);
        HBaseAdmin.checkHBaseAvailable(conf);
    }

    public static HBaseClient getAsyncClient() {
        return asyncHbaseClient;
    }

    public static void asyncInit(String zkQuorum, String zkPath) {
        if (zkQuorum == null || zkQuorum.isEmpty()) {
            // Follow the default path
            Configuration conf = HBaseConfiguration.create();
            zkQuorum = ZKConfig.getZKQuorumServersString(conf);
        }
        asyncHbaseClient = new HBaseClient(zkQuorum, zkPath, Executors.newCachedThreadPool());
    }
}
