package me.shy.demo.operator

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 */
object ActionDemosByScala {
    def main(args: Array[String]): Unit = {
        // for windows local test
        System.setProperty("hadoop.home.dir", "C:\\Users\\shy\\OneDrive\\sync\\backup\\winutils-master\\hadoop-2.8.3")
        val sc = new SparkContext(new SparkConf().setMaster("local[2]").setAppName("ActionDemos"))

        reduce(sc)
        collect(sc)
        take(sc)
        count(sc)
        takeOrdered(sc)
        top(sc)
        saveAsTextFile(sc)
        countByKey(sc)
        foreach(sc)
        takeSample(sc)
        sc.stop()
    }

    def takeSample(sc: SparkContext) = {
        val res = sc.parallelize(Array(21, 2, 13, 4, 15)).takeSample(false, 2)
        for (item <- res) {
            println(item)
        }
    }

    def foreach(sc: SparkContext) = sc.parallelize(Array(1, 2, 3, 4, 5)).foreach(r => println(r))

    def countByKey(sc: SparkContext) = {
        val res = sc.parallelize(Array(("Tom", 90), ("Mickey", 80), ("Jerry", 92), ("Tom", 65), ("Jerry", 93)))
            .countByKey()
        for (item <- res) {
            println(s"${item._1} ==== ${item._2}")
        }
    }

    def saveAsTextFile(sc: SparkContext) = sc.parallelize(Array(1, 2, 3, 4, 5))
        .saveAsTextFile("D:\\demo.workspace\\out")

    def top(sc: SparkContext) = {
        val res = sc.parallelize(Array(21, 2, 13, 4, 15)).top(3)
        for (item <- res) {
            println(item)
        }
    }

    def takeOrdered(sc: SparkContext) = {
        val res = sc.parallelize(Array(21, 2, 13, 4, 15)).takeOrdered(3)
        for (item <- res) {
            println(item)
        }
    }

    def count(sc: SparkContext) = {
        val res = sc.parallelize(Array(1, 2, 3, 4, 5)).count()
        println(res)
    }

    def take(sc: SparkContext) = {
        val res = sc.parallelize(Array(1, 2, 3, 4, 5)).take(3)
        for (item <- res) {
            println(item)
        }
    }

    def collect(sc: SparkContext) = {
        val res = sc.parallelize(Array(1, 2, 3, 4, 5)).collect()
        for (item <- res) {
            println(item)
        }
    }

    def reduce(sc: SparkContext) = {
        val res = sc.parallelize(Array(1, 2, 3, 4, 5)).reduce(_ + _)
        println(res)
    }
}
