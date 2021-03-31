/**
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Licence: GPLv3
 * @Description: -
 * @Since: 2019-05-02 09:17:42
 * @LastTime: 2019-05-02 09:47:02
 */

package me.shy.spark.operator;

import java.util.Arrays;
import java.util.List;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;

public class FilterDemo {

    public static void main(String[] args) {
        System.setProperty("hadoop.home.dir", "D:\\source\\demo\\hadoop");
        SparkConf conf = new SparkConf().setAppName("FilterDemo").setMaster("local");
        JavaSparkContext sparkContext = new JavaSparkContext(conf);

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        JavaRDD<Integer> numbersRdd = sparkContext.parallelize(numbers, 2);
        JavaRDD<Integer> evenNumbersRdd = numbersRdd.filter(new Function<Integer, Boolean>() {

            private static final long serialVersionUID = 1L;

            @Override
            public Boolean call(Integer v1) throws Exception {
                // if (v1 % 2 == 0){
                // return true;
                // } else {
                // return false;
                // }
                return v1 % 2 == 0;
            }
        });

        System.out.println(evenNumbersRdd.collect());

        sparkContext.close();
    }
}
