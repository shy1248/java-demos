/**
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Licence: GPLv3
 * @Description: -
 * @Since: 2019-05-02 12:24:11
 * @LastTime: 2019-05-02 12:53:35
 */

package me.shy.spark.operator;

import java.util.Arrays;
import java.util.List;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

public class CogroupDemo {

    public static void main(String[] args) {
        System.setProperty("hadoop.home.dir", "D:/source/demo/hadoop/");

        SparkConf conf = new SparkConf().setAppName("JoinDemo").setMaster("local");
        JavaSparkContext sparkContext = new JavaSparkContext(conf);

        List<Tuple2<Integer, String>> students = Arrays.asList(new Tuple2<Integer, String>(1, "Tom"),
            new Tuple2<Integer, String>(2, "Jack"), new Tuple2<Integer, String>(3, "Scorrt"),
            new Tuple2<Integer, String>(4, "Anord"), new Tuple2<Integer, String>(5, "Rose"));

        List<Tuple2<Integer, Tuple2<String, Integer>>> scores = Arrays.asList(
            new Tuple2<Integer, Tuple2<String, Integer>>(1, new Tuple2<String, Integer>("math", 95)),
            new Tuple2<Integer, Tuple2<String, Integer>>(2, new Tuple2<String, Integer>("math", 96)),
            new Tuple2<Integer, Tuple2<String, Integer>>(3, new Tuple2<String, Integer>("math", 88)),
            new Tuple2<Integer, Tuple2<String, Integer>>(4, new Tuple2<String, Integer>("math", 92)),
            new Tuple2<Integer, Tuple2<String, Integer>>(5, new Tuple2<String, Integer>("math", 65)),
            new Tuple2<Integer, Tuple2<String, Integer>>(1, new Tuple2<String, Integer>("english", 75)),
            new Tuple2<Integer, Tuple2<String, Integer>>(2, new Tuple2<String, Integer>("english", 72)),
            new Tuple2<Integer, Tuple2<String, Integer>>(3, new Tuple2<String, Integer>("english", 87)),
            new Tuple2<Integer, Tuple2<String, Integer>>(4, new Tuple2<String, Integer>("english", 91)),
            new Tuple2<Integer, Tuple2<String, Integer>>(5, new Tuple2<String, Integer>("english", 98)),
            new Tuple2<Integer, Tuple2<String, Integer>>(1, new Tuple2<String, Integer>("chinese", 97)),
            new Tuple2<Integer, Tuple2<String, Integer>>(2, new Tuple2<String, Integer>("chinese", 83)),
            new Tuple2<Integer, Tuple2<String, Integer>>(3, new Tuple2<String, Integer>("chinese", 64)),
            new Tuple2<Integer, Tuple2<String, Integer>>(4, new Tuple2<String, Integer>("chinese", 73)),
            new Tuple2<Integer, Tuple2<String, Integer>>(5, new Tuple2<String, Integer>("chinese", 82)));

        JavaPairRDD<Integer, String> studentsRdd = sparkContext.parallelizePairs(students);
        JavaPairRDD<Integer, Tuple2<String, Integer>> scoresRdd = sparkContext.parallelizePairs(scores, 2);

        JavaPairRDD<Integer, Tuple2<Iterable<String>, Iterable<Tuple2<String, Integer>>>> studentScoresRdd = studentsRdd
            .cogroup(scoresRdd);
        System.out.println(studentScoresRdd.collect());

        sparkContext.close();
    }
}
