/**
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Licence: GPLv3
 * @Description: -
 * @Since: 2019-05-02 18:45:46
 * @LastTime: 2019-05-08 19:47:29
 */
package me.shy.spark;

import java.util.List;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

public class TopNDemo {

    public static void main(String[] args) {
        System.setProperty("hadoop.home.dir", "D:/source/demo/hadoop/");

        SparkConf conf = new SparkConf().setAppName("TopNDemo").setMaster("local");
        JavaSparkContext sparkContext = new JavaSparkContext(conf);
        JavaRDD<String> linesRdd = sparkContext
            .textFile(GroupTopNDemo.class.getClassLoader().getResource("topn.txt").toString());
        JavaPairRDD<Integer, String> pairsRdd = linesRdd.mapToPair(new PairFunction<String, Integer, String>() {

            private static final long serialVersionUID = 1L;

            @Override
            public Tuple2<Integer, String> call(String t) throws Exception {
                return new Tuple2<Integer, String>(Integer.valueOf(t), t);
            }

        });

        JavaPairRDD<Integer, String> sortedPairsRdd = pairsRdd.sortByKey(false);

        JavaRDD<Integer> numbersRdd = sortedPairsRdd.map(new Function<Tuple2<Integer, String>, Integer>() {

            private static final long serialVersionUID = 1L;

            @Override
            public Integer call(Tuple2<Integer, String> v1) throws Exception {
                return v1._1;
            }

        });

        List<Integer> top3 = numbersRdd.take(3);

        System.out.print(top3);

        sparkContext.close();
    }
}
