/**
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Licence: GPLv3
 * @Description: -
 * @Since: 2019-05-02 16:41:49
 * @LastTime: 2019-05-04 20:40:12
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

public class SortedWordCount {

    public static void main(String[] args) {
        System.setProperty("hadoop.home.dir", "D:/source/demo/hadoop/");

        SparkConf conf = new SparkConf().setAppName("SortedWordCount").setMaster("local");
        JavaSparkContext sparkContext = new JavaSparkContext(conf);

        JavaRDD<String> linesRdd = sparkContext.textFile("./wordcount.txt");
        JavaRDD<String> wordsRdd = linesRdd.flatMap(new FlatMapFunction<String, String>() {

            private static final long serialVersionUID = 1L;

            @Override
            public Iterator<String> call(String t) throws Exception {
                return (Iterator<String>) Arrays.asList(t.split(" "));
            }
        });

        JavaPairRDD<String, Integer> wordsCountRdd = wordsRdd.mapToPair(new PairFunction<String, String, Integer>() {

            private static final long serialVersionUID = 1L;

            @Override
            public Tuple2<String, Integer> call(String t) throws Exception {
                return new Tuple2<String, Integer>(t, 1);
            }
        });

        JavaPairRDD<String, Integer> wordsTotalRdd = wordsCountRdd
            .reduceByKey(new Function2<Integer, Integer, Integer>() {

                private static final long serialVersionUID = 1L;

                @Override
                public Integer call(Integer v1, Integer v2) throws Exception {
                    return v1 + v2;
                }

            });

        // reverse <k-v>
        JavaPairRDD<Integer, String> reverseWordsCountRdd = wordsTotalRdd
            .mapToPair(new PairFunction<Tuple2<String, Integer>, Integer, String>() {

                private static final long serialVersionUID = 1L;

                @Override
                public Tuple2<Integer, String> call(Tuple2<String, Integer> t) throws Exception {
                    return new Tuple2<Integer, String>(t._2, t._1);
                }
            });

        JavaPairRDD<Integer, String> sortedWordsCountRdd = reverseWordsCountRdd.sortByKey(false);

        sortedWordsCountRdd.foreach(new VoidFunction<Tuple2<Integer, String>>() {

            private static final long serialVersionUID = 1L;

            @Override
            public void call(Tuple2<Integer, String> t) throws Exception {
                System.out.println(t._2 + " : " + t._1);
            }
        });

        sparkContext.close();
    }
}
