/**
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Licence: GPLv3
 * @Description: -
 * @Since: 2019-05-02 19:53:40
 * @LastTime: 2019-05-04 20:48:38
 */
package me.shy.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.collections.IteratorUtils;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

public class GroupTopNDemo {

    public static void main(String[] args) {
        System.setProperty("hadoop.home.dir", "D:/source/demo/hadoop/");

        SparkConf conf = new SparkConf().setAppName("GroupTopNDemo").setMaster("local");
        JavaSparkContext sparkContext = new JavaSparkContext(conf);

        JavaRDD<String> linesRdd = sparkContext
            .textFile(GroupTopNDemo.class.getClassLoader().getResource("grouptopn.txt").toString());
        JavaPairRDD<String, Integer> scoresRdd = linesRdd.mapToPair(new PairFunction<String, String, Integer>() {

            private static final long serialVersionUID = 1L;

            @Override
            public Tuple2<String, Integer> call(String t) throws Exception {
                String[] splited = t.split(" ");
                return new Tuple2<String, Integer>(splited[0], Integer.valueOf(splited[1]));
            }

        });

        JavaPairRDD<String, Iterable<Integer>> groupedScoresRdd = scoresRdd.groupByKey();

        JavaRDD<Tuple2<String, Iterable<Integer>>> sortedGroupedScoresRdd = groupedScoresRdd
            .map(new Function<Tuple2<String, Iterable<Integer>>, Tuple2<String, Iterable<Integer>>>() {

                private static final long serialVersionUID = 1L;

                @Override
                public Tuple2<String, Iterable<Integer>> call(Tuple2<String, Iterable<Integer>> t)
                    throws Exception {
                    String className = t._1;
                    Iterable<Integer> top3Scores = sortAndTop(t._2, 3);

                    return new Tuple2<String, Iterable<Integer>>(className, top3Scores);
                }

            });
        System.out.println(sortedGroupedScoresRdd.collect());
        sparkContext.close();
    }

    private static <T> Iterable<T> sortAndTop(Iterable<T> it, int n) {
        List<T> result = new ArrayList<T>();
        Object[] arr = IteratorUtils.toArray(it.iterator());
        Arrays.sort(arr);
        if (n >= arr.length) {
            return Arrays.asList((T) arr);
        }
        for (int i = arr.length - 1; i > arr.length - 1 - n; i--) {
            result.add((T) arr[i]);
        }
        return result;
    }
}
