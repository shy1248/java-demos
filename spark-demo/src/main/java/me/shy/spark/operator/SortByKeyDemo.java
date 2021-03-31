/**
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Licence: GPLv3
 * @Description: -
 * @Since: 2019-05-02 11:12:57
 * @LastTime: 2019-05-02 11:24:48
 */

package me.shy.spark.operator;

import java.util.Arrays;
import java.util.List;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.VoidFunction;
import scala.Tuple2;

public class SortByKeyDemo {

    public static void main(String[] args) {
        System.setProperty("hadoop.home.dir", "D:/source/demo/hadoop/");

        SparkConf conf = new SparkConf().setAppName("SortByKeyDemo").setMaster("local");
        JavaSparkContext sparkContext = new JavaSparkContext(conf);

        List<Tuple2<String, Integer>> scores = Arrays.asList(new Tuple2<String, Integer>("Tom", 90),
            new Tuple2<String, Integer>("Jack", 80), new Tuple2<String, Integer>("Lucy", 75),
            new Tuple2<String, Integer>("Lily", 65), new Tuple2<String, Integer>("Scott", 82),
            new Tuple2<String, Integer>("Willis", 92), new Tuple2<String, Integer>("Anord", 76));

        JavaPairRDD<String, Integer> scoresRdd = sparkContext.parallelizePairs(scores, 1);
        JavaPairRDD<String, Integer> sortScoresRdd = scoresRdd.sortByKey(false);

        sortScoresRdd.foreach(new VoidFunction<Tuple2<String, Integer>>() {

            private static final long serialVersionUID = 1L;

            @Override
            public void call(Tuple2<String, Integer> t) throws Exception {
                System.out.println(t._1 + " : " + t._2);
            }

        });

        sparkContext.close();
    }
}
