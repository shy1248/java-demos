/**
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Licence: GPLv3
 * @Description: -
 * @Since: 2019-05-02 13:06:19
 * @LastTime: 2019-05-04 20:39:46
 */

package me.shy.demo;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.VoidFunction;
import scala.Tuple2;

public class ActionDemos {

    public static void main(String[] args) {
        System.setProperty("hadoop.home.dir", "D:/source/demo/hadoop/");

        SparkConf conf = new SparkConf().setAppName("ActionsDemo").setMaster("local");
        JavaSparkContext sparkContext = new JavaSparkContext(conf);

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        JavaRDD<Integer> numbersRdd = sparkContext.parallelize(numbers, 2);

        // collect
        List<Integer> numberList = numbersRdd.collect();
        System.out.println("Collect: " + numberList);

        // count
        long count = numbersRdd.count();
        System.out.println("Count: " + count);

        // reduce
        Integer reduce = numbersRdd.reduce(new Function2<Integer, Integer, Integer>() {

            private static final long serialVersionUID = 1L;

            @Override
            public Integer call(Integer v1, Integer v2) throws Exception {
                return v1 + v2;
            }
        });
        System.out.println("Reduce: " + reduce);

        // take
        List<Integer> takeNumbers = numbersRdd.take(3);
        System.out.println("Take 3: " + takeNumbers);

        // saveAsFile
        System.out.println("Save to local Filesystem.");
        numbersRdd.saveAsTextFile("./spark-out");

        // count by key
        List<Tuple2<String, String>> students = Arrays.asList(
            new Tuple2<String, String>("CALSS_1", "Tom"),
            new Tuple2<String, String>("CALSS_1", "Jerry"),
            new Tuple2<String, String>("CALSS_2", "Anord"),
            new Tuple2<String, String>("CALSS_2", "Lily"),
            new Tuple2<String, String>("CALSS_2", "Foxy"),
            new Tuple2<String, String>("CALSS_2", "Harry"),
            new Tuple2<String, String>("CALSS_1", "Rose")
        );
        JavaPairRDD<String, String> studentsRdd = sparkContext.parallelizePairs(students, 2);
        Map<String, Long> studentCountByClassMap = studentsRdd.countByKey();
        System.out.println("CountByKey: " + studentCountByClassMap);

        // for each
        studentsRdd.foreach(new VoidFunction<Tuple2<String, String>>() {

            private static final long serialVersionUID = 1L;

            @Override
            public void call(Tuple2<String, String> t) throws Exception {
                System.out.println("Foreach: (" + t._1 + ", " + t._2 + ")");
            }
        });

        sparkContext.close();
    }
}
