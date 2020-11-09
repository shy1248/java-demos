/**
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Licence: GPLv3
 * @Description: -
 * @Since: 2019-05-02 10:16:08
 * @LastTime: 2019-05-02 10:30:08
 */

package me.shy.demo.operator;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.VoidFunction;
import scala.Tuple2;

public class GroupByKeyDemo {

    public static void main(String[] args) {
        System.setProperty("hadoop.home.dir", "D:/source/demo/hadoop/");

        SparkConf conf = new SparkConf().setAppName("GroupByKeyDemo").setMaster("local");
        JavaSparkContext sparkContext = new JavaSparkContext(conf);

        List<Tuple2<String, Integer>> scores = Arrays.asList(new Tuple2<String, Integer>("CLASS_1", 90),
            new Tuple2<String, Integer>("CLASS_2", 80), new Tuple2<String, Integer>("CLASS_2", 75),
            new Tuple2<String, Integer>("CLASS_1", 65), new Tuple2<String, Integer>("CLASS_1", 82),
            new Tuple2<String, Integer>("CLASS_2", 92), new Tuple2<String, Integer>("CLASS_2", 76));

        JavaPairRDD<String, Integer> scoresRdd = sparkContext.parallelizePairs(scores, 1);
        JavaPairRDD<String, Iterable<Integer>> groupedScoresRdd = scoresRdd.groupByKey();

        groupedScoresRdd.foreach(new VoidFunction<Tuple2<String, Iterable<Integer>>>() {

            private static final long serialVersionUID = 1L;

            @Override
            public void call(Tuple2<String, Iterable<Integer>> t) throws Exception {
                System.out.println(t._1);
                Iterator<Integer> it = t._2.iterator();
                while (it.hasNext()) {
                    System.out.println(it.next());
                }
                System.out.println("*********************************************");
            }
        });

        sparkContext.close();
    }
}
