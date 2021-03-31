/**
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Licence: GPLv3
 * @Description: -
 * @Since: 2019-05-02 16:14:24
 * @LastTime: 2019-06-01 21:03:11
 */

package me.shy.spark;

import java.util.Arrays;
import java.util.List;
import org.apache.spark.Accumulator;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.VoidFunction;

public class AccumulatorVariableDemo {

    public static void main(String[] args) {
        System.setProperty("hadoop.home.dir", "D:/source/demo/hadoop/");

        SparkConf conf = new SparkConf().setAppName("AccumulatorVariableDemo").setMaster("local");
        JavaSparkContext sparkContext = new JavaSparkContext(conf);

        // define accumulator variable
        Accumulator<Integer> sum = sparkContext.accumulator(0);

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        JavaRDD<Integer> numbersRdd = sparkContext.parallelize(numbers, 5);
        numbersRdd.foreach(new VoidFunction<Integer>() {

            private static final long serialVersionUID = 1L;

            @Override
            public void call(Integer t) throws Exception {
                // in task thread, only do operation for accumulator variable, can not access value
                sum.add(t);
            }

        });

        // in driver program, can access accumulator variable's value
        System.out.println("Accumulator sum is " + sum.value());

        sparkContext.close();
    }
}
