/**
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Licence: GPLv3
 * @Description: -
 * @Since: 2019-05-02 09:52:35
 * @LastTime: 2019-05-04 20:38:47
 */

package me.shy.demo.operator;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;

public class FlatMapDemo {

    public static void main(String[] args) {
        System.setProperty("hadoop.home.dir", "D:/source/demo/hadoop/");

        SparkConf conf = new SparkConf().setAppName("FlatMapDemo").setMaster("local");
        JavaSparkContext sparkContext = new JavaSparkContext(conf);

        List<String> lines = Arrays.asList("Hello hadoop", "Hello spark", "Hello storm");
        JavaRDD<String> linesRdd = sparkContext.parallelize(lines, 2);
        JavaRDD<String> WordsRdd = linesRdd.flatMap(new FlatMapFunction<String, String>() {

            private static final long serialVersionUID = 1L;

            @Override
            public Iterator<String> call(String t) throws Exception {
                return (Iterator<String>) Arrays.asList(t.split(" "));
            }
        });

        System.out.println(WordsRdd.collect());

        sparkContext.close();
    }
}
