/**
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Licence: GPLv3
 * @Description: A word count demo using spark with java api
 * @Since: 2019-05-01 21:43:03
 * @LastTime: 2020-08-08 22:48:57
 */

package me.shy.demo;

import java.util.Arrays;
import java.util.Iterator;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;
import scala.Tuple2;

public class WordCount {

    public static void main(String[] args) {

        // Issue:
        // ERROR Shell: Failed to locate the winutils binary in the hadoop binary path
        System.setProperty("hadoop.home.dir", "d:/applications/winutils-master/hadoop-2.8.3");

        // Construct a conf for spark context
        SparkConf conf = new SparkConf().setAppName("WordCount").setMaster("local[1]");
        JavaSparkContext sparkContext = new JavaSparkContext(conf);

        // Get JavaRDD object from Local Filesystem
        JavaRDD<String> linesRdd = sparkContext.textFile("D:/demo.workspace/wordcount.txt");
        // Split line with whitespace to words
        JavaRDD<String> wordsRdd = linesRdd.flatMap(new FlatMapFunction<String, String>() {

            private static final long serialVersionUID = 1L;

            @Override
            public Iterator<String> call(String line) throws Exception {
                return Arrays.asList(line.split(" ")).iterator();
            }
        });

        // Transform word to <word, 1>
        JavaPairRDD<String, Integer> wordCountRdd = wordsRdd.mapToPair(new PairFunction<String, String, Integer>() {

            private static final long serialVersionUID = 1L;

            @Override
            public Tuple2<String, Integer> call(String word) throws Exception {
                return new Tuple2<String, Integer>(word, 1);
            }
        });

        // Reduce and total count
        JavaPairRDD<String, Integer> wordCountsRdd = wordCountRdd
                .reduceByKey(new Function2<Integer, Integer, Integer>() {

                    private static final long serialVersionUID = 1L;

                    @Override
                    public Integer call(Integer v1, Integer v2) throws Exception {
                        return v1 + v2;
                    }
                });

        // Action: print result
        wordCountsRdd.foreach(new VoidFunction<Tuple2<String, Integer>>() {

            private static final long serialVersionUID = 1L;

            @Override
            public void call(Tuple2<String, Integer> t) throws Exception {
                System.out.println("Word \"" + t._1 + "\" appear " + t._2 + " times.");
            }
        });

        // Close spark context
        sparkContext.close();
    }
}
