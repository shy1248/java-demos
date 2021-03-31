package me.shy.spark

import org.apache.spark.{SparkConf, SparkContext}

/**
  * @Since: 2019-12-21 22:31:24
  * @Author: shy
  * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
  * @Version: v1.0
  * @Description: A word count demo by spark using scala
  */

object rdCountByScala {
  def main(args: Array[String]): Unit = {
    // for windows local test
    System.setProperty(
      "hadoop.home.dir",
      "d:/applications/winutils-master/hadoop-2.8.3"
    )

    // initilize sparkContext
    val conf =
      new SparkConf().setMaster("local[1]").setAppName("WordCountByScala")
    val sc = new SparkContext(conf)

    // initilize RDD from file
    sc.textFile("D:/dev/temp/wordcount.txt")
      // flatmap line to words with splited "\s+"
      .flatMap(_.split("\\s+"))
      // map every word to a tuple: (word, 1)
      .map((_, 1))
      // sum for every word
      .reduceByKey(_ + _)
      // action, print to console
      .foreach(wordCount => println(s"${wordCount._1}: ${wordCount._2}"))

    // stop sparkContext
    sc.stop()
  }
}
