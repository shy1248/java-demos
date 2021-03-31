package me.shy.spark.streaming

import me.shy.demo.streaming.utils.SimpleMySQLConnectionPool
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 */
object ForeachRDDDemo {
    def main(args: Array[String]): Unit = {
        System.setProperty("hadoop.home.dir", "C:\\Users\\shy\\OneDrive\\sync\\backup\\winutils-master\\hadoop-2.8.3")
        val conf = new SparkConf().setMaster("local[2]").setAppName("ForeachRDDDemo")
        val ssc = new StreamingContext(conf, Seconds(2))

        // 统计单词
        val wordCountDS = ssc.socketTextStream("demos01", 9999)
            .flatMap(line => line.split("\\s+"))
            .map(word => (word, 1))
            .reduceByKey(_ + _)

        // 将结果保存至数据库
        wordCountDS.foreachRDD(rdd => {
            // 此处使用 foreachPartition 算子，避免每条数据都需要打开一个连接
            rdd.foreachPartition(wordWithCounts => {
                // 使用静态的 mysql 连接池，提高性能，同时注意 连接的初始化不能放到 foreachPartition 的外边
                // 因为 Connection 这类对象是不能被序列化的
                val conn = SimpleMySQLConnectionPool.getConnection()
                for (wordWithCount <- wordWithCounts) {
                    val statement = conn.prepareStatement("insert into wordcount values(now(),?,?)")
                    statement.setString(1, wordWithCount._1)
                    statement.setInt(2, wordWithCount._2)
                    statement.executeUpdate()
                }
                SimpleMySQLConnectionPool.release(conn)
            })
        })

        ssc.start()
        ssc.awaitTermination()
        ssc.stop(true)
    }

}
