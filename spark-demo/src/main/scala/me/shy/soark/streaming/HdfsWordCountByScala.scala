package me.shy.spark.streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 */
object HdfsWordCountByScala {
    def main(args: Array[String]): Unit = {
        // for windows local test
        System.setProperty("hadoop.home.dir", "C:\\Users\\shy\\OneDrive\\sync\\backup\\winutils-master\\hadoop-2.8.3")
        val conf = new SparkConf().setMaster("local[2]").setAppName("HdfsWordCountByScala")
        val ssc = new StreamingContext(conf, Seconds(2))
        val wordCountDS = ssc.textFileStream("hdfs://demos01:9000/sparkstreaming/demo")
            .flatMap(line => line.split("\\s+"))
            .map(word => (word, 1))
            .reduceByKey(_ + _)

        wordCountDS.print(20)
        ssc.start()
        ssc.awaitTermination()
        ssc.stop(true)
    }

}
