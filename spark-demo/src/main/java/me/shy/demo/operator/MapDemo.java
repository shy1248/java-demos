/**
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Licence: GPLv3
 * @Description: -
 * @Since: 2019-05-01 23:19:03
 * @LastTime: 2019-05-02 09:51:00
 */

package me.shy.demo.operator;

import java.util.Arrays;
import java.util.List;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;

public class MapDemo {

    public static void main(String[] args) {

        System.setProperty("hadoop.home.dir", "D:\\source\\demo\\hadoop");

        SparkConf conf = new SparkConf().setAppName("MapDemo").setMaster("local");
        JavaSparkContext sparkContext = new JavaSparkContext(conf);

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        JavaRDD<Integer> numbersRdd = sparkContext.parallelize(numbers);

        JavaRDD<Integer> squareRdd = numbersRdd.map(new Function<Integer, Integer>() {

            private static final long serialVersionUID = 1L;

            @Override
            public Integer call(Integer v1) throws Exception {
                return v1 * v1;
            }
        });

        // squareRdd.foreach(new VoidFunction<Integer>() {

        // private static final long serialVersionUID = 1L;

        // @Override
        // public void call(Integer t) throws Exception {
        // System.out.println("Numbers square is " + t);
        // }

        // });

        System.out.println(squareRdd.collect());

        sparkContext.close();
    }
}
