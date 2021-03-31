/**
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Licence: GPLv3
 * @Description: -
 * @Since: 2019-05-02 10:54:43
 * @LastTime: 2019-05-02 11:05:04
 */

package me.shy.spark.operator;

import java.util.Arrays;
import java.util.List;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.VoidFunction;
import scala.Tuple2;

public class ReduceByKeyDemo {

    public static void main(String[] args) {
        System.setProperty("hadoop.home.dir", "D:/source/demo/hadoop/");

        SparkConf conf = new SparkConf().setAppName("ReduceByKeyDemo").setMaster("local");
        JavaSparkContext sparkContext = new JavaSparkContext(conf);

        List<Tuple2<String, Integer>> scores = Arrays.asList(new Tuple2<String, Integer>("CLASS_1", 90),
            new Tuple2<String, Integer>("CLASS_2", 80), new Tuple2<String, Integer>("CLASS_2", 75),
            new Tuple2<String, Integer>("CLASS_1", 65), new Tuple2<String, Integer>("CLASS_1", 82),
            new Tuple2<String, Integer>("CLASS_2", 92), new Tuple2<String, Integer>("CLASS_2", 76));

        JavaPairRDD<String, Integer> scoresRdd = sparkContext.parallelizePairs(scores, 2);
        JavaPairRDD<String, Integer> reducedScoresRdd = scoresRdd
            .reduceByKey(new Function2<Integer, Integer, Integer>() {

                private static final long serialVersionUID = 1L;

                @Override
                public Integer call(Integer v1, Integer v2) throws Exception {
                    return v1 + v2;
                }
            });

        reducedScoresRdd.foreach(new VoidFunction<Tuple2<String, Integer>>() {

            private static final long serialVersionUID = 1L;

            @Override
            public void call(Tuple2<String, Integer> t) throws Exception {
                System.out.println("Class " + t._1 + " total scores is " + t._2);
            }

        });

        sparkContext.close();
    }
}
