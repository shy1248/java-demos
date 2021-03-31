package me.shy.spark

import org.apache.spark.{SparkConf, SparkContext}

import scala.util.Random

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: 给 Key 加随机数解决由于聚合类 shffle 产生数据倾斜的方式
 */
object AggregateWordCountByScala {
    def main(args: Array[String]): Unit = {
        // for windows local test
        System.setProperty("hadoop.home.dir", "C:\\Users\\shy\\OneDrive\\sync\\backup\\winutils-master\\hadoop-2.8.3")
        val sc = new SparkContext(new SparkConf().setMaster("local[2]").setAppName("AggregateWordCountByScala"))
        sc.parallelize(Array("You Jump", "I Jump", "You Jump", "I Jump", "You Jump", "I Jump", "Jump Jump", "Jump " +
            "Jump"))
            .flatMap(line => line.split("\\s+"))
            .map(word => (s"${Random.nextInt()}#${word}", 1))
            .reduceByKey(_ + _)
            .map(wordWithCount => (wordWithCount._1.split("#")(1), wordWithCount._2))
            .reduceByKey(_ + _)
            .foreach(wordWithCount => println(wordWithCount))

        sc.stop()
    }

}
