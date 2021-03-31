/**
 * @Since: 2019-08-06 12:13:57
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 * @LastTime: 2019-08-06 14:51:18
 */

package me.shy.vertx.hbase;

import com.google.common.base.Charsets;
import com.stumbleupon.async.Callback;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.zookeeper.ZKConfig;
import org.apache.log4j.Logger;
import org.hbase.async.GetRequest;
import org.hbase.async.HBaseClient;
import org.hbase.async.KeyValue;
import org.hbase.async.PutRequest;

public class AsyncHbaseClient {

    public static final String DEFAULT_ZK_DIR = "/hbase";
    private static final Logger LOGGER = Logger.getLogger(AsyncHbaseClient.class);
    // HBase Client
    private HBaseClient hBaseClient;

    public void init(String zkQuorum, String tableName, String columnFamily) throws Exception {
        if (zkQuorum == null || zkQuorum.isEmpty()) {
            // Follow the default path
            Configuration conf = HBaseConfiguration.create();
            zkQuorum = ZKConfig.getZKQuorumServersString(conf);
        }
        this.hBaseClient = new HBaseClient(zkQuorum, DEFAULT_ZK_DIR, Executors.newCachedThreadPool());

        // Lets ensure that Table and Cf exits
        final CountDownLatch latch = new CountDownLatch(1);
        final AtomicBoolean fail = new AtomicBoolean(false);
        this.hBaseClient.ensureTableFamilyExists(tableName, columnFamily).addCallbacks(new Callback<Object, Object>() {
            @Override public Object call(Object arg) throws Exception {
                latch.countDown();
                return null;
            }
        }, new Callback<Object, Object>() {
            @Override public Object call(Object arg) throws Exception {
                fail.set(true);
                latch.countDown();
                return null;
            }
        });

        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new Exception("Interrupted", e);
        }

        if (fail.get()) {
            throw new Exception("Table or Column Family doesn't exist");
        }
    }

    public void putData(String tableName, String columnFamily, String rowKey, String value) throws Exception {
        PutRequest putRequest = new PutRequest(tableName.getBytes(Charsets.UTF_8), rowKey.getBytes(Charsets.UTF_8),
            columnFamily.getBytes(Charsets.UTF_8), "payload".getBytes(Charsets.UTF_8), value.getBytes(Charsets.UTF_8));
        final CountDownLatch latch = new CountDownLatch(1);
        final AtomicBoolean fail = new AtomicBoolean(false);
        this.hBaseClient.put(putRequest).addCallbacks(new Callback<Object, Object>() {
            @Override public Object call(Object arg) throws Exception {
                latch.countDown();
                return null;
            }
        }, new Callback<Object, Exception>() {
            @Override public Object call(Exception arg) throws Exception {
                fail.set(true);
                latch.countDown();
                return null;
            }
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new Exception("Interrupted", e);
        }

        if (fail.get()) {
            throw new Exception("Put request failed");
        }
    }

    public Handler<Message<Object>> get() {
        return message -> {
            JsonObject hbaseGetParams = (JsonObject)message.body();
            String tableName = hbaseGetParams.getString("tableName");
            String userId = hbaseGetParams.getString("userId");
            GetRequest getRequest = new GetRequest(tableName, Bytes.toBytes(userId));
            // ArrayList<KeyValue> kvs = hBaseClient.get(getRequest).join();
            // return kvs.get(0).value();

            long start = System.currentTimeMillis();
            this.hBaseClient.get(getRequest).addCallbacks(new Callback<Object, ArrayList<KeyValue>>() {
                @Override public Object call(ArrayList<KeyValue> rss) throws Exception {
                    LOGGER.info("Async hbase get cost: " + (System.currentTimeMillis() - start));
                    JsonObject hbaseResult = new JsonObject();
                    if (!rss.isEmpty()) {
                        KeyValue rs = rss.get(0);
                        hbaseResult.put("value", Bytes.toString(rs.value()));
                        hbaseResult.put("timestamp", rs.timestamp());
                    }
                    message.reply(hbaseResult);
                    return null;
                }
            }, new Callback<Object, Exception>() {
                @Override public Object call(Exception e) throws Exception {
                    LOGGER.error("AsyncHbase get failed: " + e.getStackTrace());
                    message.fail(-1, "AsyncHbase get failed");
                    return null;
                }
            });
        };
    }
}
