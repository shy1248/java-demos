/**
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Licence: GPLv3
 * @Description: -
 * @Since: 2019-05-02 15:58:48
 * @LastTime: 2019-05-02 16:08:50
 */

package me.shy.demo;

import java.util.Arrays;
import java.util.List;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.broadcast.Broadcast;

public class BroadcastVariableDemo {

    public static void main(String[] args) {
        System.setProperty("hadoop.home.dir", "D:/source/demo/hadoop/");

        SparkConf conf = new SparkConf().setAppName("BroadcastVariableDemo").setMaster("local");
        JavaSparkContext sparkContext = new JavaSparkContext(conf);

        // define a broadcast variable
        final int factor = 5;
        Broadcast<Integer> broadcastFactor = sparkContext.broadcast(factor);

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        JavaRDD<Integer> numbersRdd = sparkContext.parallelize(numbers, 5);

        JavaRDD<Integer> mutipleNumbersRdd = numbersRdd.map(new Function<Integer, Integer>() {

            private static final long serialVersionUID = 1L;

            @Override
            public Integer call(Integer v1) throws Exception {
                return v1 * broadcastFactor.value();
            }

        });

        System.out.println(mutipleNumbersRdd.collect());

        sparkContext.close();
    }
}
