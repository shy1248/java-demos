/**
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Licence: GPLv3
 * @Description: -
 * @Since: 2019-05-02 17:55:54
 * @LastTime: 2019-05-04 20:49:56
 */
package me.shy.spark.secondary_sorted;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;
import scala.Tuple2;

public class SecondarySorted {

    public static void main(String[] args) {
        System.setProperty("hadoop.home.dir", "D:/source/demo/hadoop/");

        SparkConf conf = new SparkConf().setAppName("SecondarySorted").setMaster("local");
        JavaSparkContext sparkContext = new JavaSparkContext(conf);

        JavaRDD<String> linesRdd = sparkContext
            .textFile(SecondarySorted.class.getClassLoader().getResource("secondarysorted.txt").toString());
        JavaRDD<SecondarySortedKey> numbersRdd = linesRdd.map(new Function<String, SecondarySortedKey>() {

            private static final long serialVersionUID = 1L;

            @Override
            public SecondarySortedKey call(String v1) throws Exception {
                String[] splited = v1.split(" ");
                return new SecondarySortedKey(Integer.valueOf(splited[0]), Integer.valueOf(splited[1]));
            }
        });

        JavaPairRDD<SecondarySortedKey, String> tmpRdd = numbersRdd
            .mapToPair(new PairFunction<SecondarySortedKey, SecondarySortedKey, String>() {

                private static final long serialVersionUID = 1L;

                @Override
                public Tuple2<SecondarySortedKey, String> call(SecondarySortedKey t) throws Exception {
                    return new Tuple2<SecondarySortedKey, String>(t, "");
                }

            });

        JavaPairRDD<SecondarySortedKey, String> sortedNumbersRdd = tmpRdd.sortByKey(false);

        sortedNumbersRdd.foreach(new VoidFunction<Tuple2<SecondarySortedKey, String>>() {

            private static final long serialVersionUID = 1L;

            @Override
            public void call(Tuple2<SecondarySortedKey, String> t) throws Exception {
                System.out.println(t._1.getFirst() + " " + t._1.getSecond());
            }

        });

        sparkContext.close();
    }
}
