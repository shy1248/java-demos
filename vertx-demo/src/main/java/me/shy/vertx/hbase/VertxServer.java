/**
 * @Since: 2019-08-05 20:05:49
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 * @LastTime: 2019-12-24 14:34:36
 */

package me.shy.vertx.hbase;

import com.google.protobuf.ServiceException;
import com.stumbleupon.async.Callback;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.Message;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.util.Bytes;
import org.hbase.async.GetRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VertxServer extends AbstractVerticle {

    private static final Logger LOGGER = LoggerFactory.getLogger(VertxServer.class);
    private static final String ZK_HOSTS = "10.25.205.131:2181,10.25.205.132:2181,10.25.205.133:2181";
    private static Configuration conf;

    public static void main(String[] args) {
        conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", ZK_HOSTS);
        conf.set("zookeeper.znode.parent", "/hbase");

        try {
            HbaseUtil.init(conf);
            HbaseUtil.asyncInit(ZK_HOSTS, "/hbase");
        } catch (IOException e) {
            LOGGER.error("An error occourded during connect hbase cluster, reason: " + e.getMessage() + "\n" + e
                .getStackTrace());
            System.exit(-1);
        } catch (ServiceException e) {
            LOGGER.error("An error occourded during connect hbase cluster, reason: " + e.getMessage() + "\n" + e
                .getStackTrace());
            System.exit(-1);
        }

        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(VertxServer.class.getName());
    }

    @Override public void start() {
        Router router = Router.router(vertx);
        router.route("/sync").handler(this::syncHandleGet);
        router.route("/async").blockingHandler(this::asyncHandleGet, false);
        vertx.createHttpServer().requestHandler(router).listen(7081);
    }

    private void syncHandleGet(RoutingContext context) {
        String table = "video_cring_real_time";
        String family = "cf";
        String column = "result";
        String userId = context.request().getParam("userId");
        LOGGER.info("Got request parameter: userId=" + userId);
        HttpServerResponse response = context.response();
        if (null == userId || userId.trim().equals("")) {
            this.sendError(404, response);
        } else {
            HbaseAccsessor accsessor = new HbaseAccsessor();
            KeyValue rs = accsessor.get(table, userId, family, column);
            LOGGER.info("Hbase get return, the result is null ? : " + (rs == null));
            if (null == rs) {
                this.sendError(404, response);
            } else {
                JsonObject hbaseResult = new JsonObject();
                hbaseResult.put("value", Bytes.toString(rs.getValue()));
                hbaseResult.put("timestamp", rs.getTimestamp());
                LOGGER.info("Hbase get success, the result is: " + hbaseResult.encodePrettily());
                response.putHeader("context-type", "application/json").end(hbaseResult.encodePrettily());
            }
        }
    }

    private void asyncHandleGet(RoutingContext context) {
        String table = "video_cring_real_time";
        String userId = context.request().getParam("userId");
        LOGGER.info("Got request parameter: userId=" + userId);
        HttpServerResponse response = context.response();
        if (null == userId || userId.trim().equals("")) {
            this.sendError(404, response);
        } else {
            JsonObject hbaseGetParam = new JsonObject();
            hbaseGetParam.put("tableName", table);
            hbaseGetParam.put("userId", userId);
            vertx.eventBus().send(VertxServer.class.getName() + ".asyncHbaseGet", hbaseGetParam, res -> {
                if (res.succeeded()) {
                    JsonObject hbaseResult = (JsonObject)res.result().body();
                    LOGGER.info("Hbase get success, the result is: " + hbaseResult.encodePrettily());
                    response.putHeader("context-type", "application/json").end(hbaseResult.encodePrettily());
                } else {
                    this.sendError(404, response);
                }
            });
        }
    }

    public Handler<Message<Object>> asyncHbaseGet() {
        return message -> {
            JsonObject hbaseGetParams = (JsonObject)message.body();
            String tableName = hbaseGetParams.getString("tableName");
            String userId = hbaseGetParams.getString("userId");
            GetRequest getRequest = new GetRequest(tableName, Bytes.toBytes(userId));
            long start = System.currentTimeMillis();
            HbaseUtil.getAsyncClient().get(getRequest)
                .addCallbacks(new Callback<Object, ArrayList<org.hbase.async.KeyValue>>() {
                    @Override public Object call(ArrayList<org.hbase.async.KeyValue> rss) throws Exception {
                        LOGGER.info("Async hbase get cost: " + (System.currentTimeMillis() - start));
                        JsonObject hbaseResult = new JsonObject();
                        if (!rss.isEmpty()) {
                            org.hbase.async.KeyValue rs = rss.get(0);
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

    private void sendError(int statusCode, HttpServerResponse response) {
        response.setStatusCode(statusCode).end();
    }

}
