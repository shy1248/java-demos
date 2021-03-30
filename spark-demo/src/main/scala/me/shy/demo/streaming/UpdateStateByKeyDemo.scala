package me.shy.demo.streaming

import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.{SparkConf, streaming}

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: updateStateByKey 算子，可以做全局累计计算，默认情况下，sparkstreaing 只会保留当前批次的计算结果
 *               使用该算子，需要设置一个 checkpoint 目录，以便出现错误时可以从 checkpoint 中恢复
 */
object UpdateStateByKeyDemo {
    def main(args: Array[String]): Unit = {
        // for windows local test
        System.setProperty("hadoop.home.dir", "C:\\Users\\shy\\OneDrive\\sync\\backup\\winutils-master\\hadoop-2.8.3")
        // Streaming 模式本地模式的 local[2] 最好少要设置2个线程，有一个线程需要用来接收数据，其它的线程来处理
        val conf = new SparkConf().setAppName("UpdateStateByKeyDemo").setMaster("local[2]")
        // 指定处理批次的时间间隔为1秒
        val ssc = new StreamingContext(conf, streaming.Seconds(1))
        // 设置 checkpoint 目录
        ssc.checkpoint("D:\\demo.workspace\\checkpoint")

        val wordCountDS = ssc.socketTextStream("demos01", 9999, storageLevel = StorageLevel.MEMORY_AND_DISK)
            .flatMap(line => line.split("\\s+"))
            .map(word => (word, 1))
            // 此处调用 updateStateByKey 算子
            // 第一个参数为当前批次的 值 集合，第二个参数为，当前批次之前的 累计 结果，注意数据类型
            // Option 类型代表了变量要么有值（Some），要么无值（None），使用 getOrElse(default) 方法获取值，如果无值则返回 default
            .updateStateByKey((values: Seq[Int], lastResult: Option[Int]) => {
                val currentCounts = values.sum
                Some(currentCounts + lastResult.getOrElse(0))
            })
        // 打印每个 RDD 前10条数据
        wordCountDS.print(10)
        ssc.start()
        ssc.awaitTermination()
        ssc.stop(true)
    }
}
