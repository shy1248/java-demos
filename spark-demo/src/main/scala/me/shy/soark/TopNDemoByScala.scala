package me.shy.spark

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 */
object TopNDemoByScala {
    def main(args: Array[String]): Unit = {
        System.setProperty("hadoop.home.dir", "C:\\Users\\shy\\OneDrive\\sync\\backup\\winutils-master\\hadoop-2.8.3")
        val sc = new SparkContext(new SparkConf().setMaster("local[2]").setAppName("TopN"))
        val topN = sc.textFile("D:\\demo.workspace\\wordcount.txt", 2)
            .flatMap(line => line.split("\\s+"))
            .map(word => (word, 1))
            .reduceByKey(_ + _)
            .map(wordAndCount => (wordAndCount._2, wordAndCount._1))
            .sortByKey(false)
            .take(3)

        for (t <- topN) println(t)

        sc.stop()
    }

}
