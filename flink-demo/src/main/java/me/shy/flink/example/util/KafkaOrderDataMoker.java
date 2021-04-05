/**
 * @Date        : 2021-04-03 09:06:49
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : Kafka 生产者
 */
package me.shy.flink.example.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.google.gson.Gson;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import me.shy.flink.example.pojo.Goods;
import me.shy.flink.example.pojo.Order;

public class KafkaOrderDataMoker {
    private static Random r = new Random();
    private static List<Integer> userIds = new ArrayList<>();
    private static List<Goods> goodses = new ArrayList<>();

    public static void main(String[] args) throws IOException, InterruptedException {
        getCache();
        Properties properties = new Properties();
        properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("example-kafka-producer.properties"));

        Producer<String, String> producer = new KafkaProducer<>(properties);
        for (int i = 0; i < 1000000000; i++) {
            String message = randomOrder();
            producer.send(
                    new ProducerRecord<String, String>(properties.getProperty("topic"), Long.toString(System.currentTimeMillis()), message));
            TimeUnit.MILLISECONDS.sleep(r.nextInt(500));
            // TimeUnit.MILLISECONDS.sleep(500);
        }
        producer.close();
    }

    public static String randomOrder() {
        String orderId = UUID.randomUUID().toString();
        Integer userId = userIds.get(r.nextInt(userIds.size()));
        Goods goods = goodses.get(r.nextInt(goodses.size()));
        Integer goodsCount = r.nextInt(5) + 1;
        BigDecimal amount = goods.getPrice().multiply(new BigDecimal(goodsCount));
        Long eventTime = System.currentTimeMillis();
        Order order = new Order(orderId, userId, goods.getGategoryId(), goods.getId(), goodsCount, amount, eventTime);
        System.out.println("Produce order: " + order);
        return new Gson().toJson(order);
    }

    public static void getCache() {
        Connection connection = JDBCTool.connect();

        JDBCTool.query(connection, "select id from t_user", null, new JDBCTool.QueryCallback() {
            @Override
            public void callback(ResultSet rs) throws SQLException {
                while (rs.next()) {
                    userIds.add(rs.getInt(1));
                }
                rs.close();
            }
        });

        JDBCTool.query(connection, "select * from t_goods", null, new JDBCTool.QueryCallback() {
            @Override
            public void callback(ResultSet rs) throws SQLException {
                while (rs.next()) {
                    goodses.add(new Goods(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getBigDecimal(4)));
                }
                rs.close();
            }
        });

        JDBCTool.close(connection);
    }

}
