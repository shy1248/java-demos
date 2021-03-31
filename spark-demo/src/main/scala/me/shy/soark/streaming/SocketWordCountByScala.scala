package me.shy.spark.streaming

import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.{SparkConf, streaming}

/**
  * @Since: 2019-12-21 22:31:24
  * @Author: shy
  * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
  * @Version: v1.0
  * @Description: -
  */
object SocketWordCountByScala {
  def main(args: Array[String]): Unit = {
    // for windows local test
    System.setProperty(
      "hadoop.home.dir",
      "C:\\Users\\shy\\OneDrive\\sync\\backup\\winutils-master\\hadoop-2.8.3"
    )
    // Streaming 模式本地模式的 local[2] 最好少要设置2个线程，有一个线程需要用来接收数据，其它的线程来处理
    val conf =
      new SparkConf().setAppName("SocketWordCountByScala").setMaster("local[2]")
    // 指定处理批次的时间间隔为1秒
    val ssc = new StreamingContext(conf, streaming.Seconds(1))
    val wordCountDataStream = ssc
      .socketTextStream(
        "demos01",
        9999,
        storageLevel = StorageLevel.MEMORY_AND_DISK
      )
      .flatMap(line => line.split("\\s+"))
      .map(word => (word, 1))
      .reduceByKey(_ + _)
    // 打印每个 RDD 前10条数据
    wordCountDataStream.print(10)
    ssc.start()
    ssc.awaitTermination()
    ssc.stop(true)
  }

}
