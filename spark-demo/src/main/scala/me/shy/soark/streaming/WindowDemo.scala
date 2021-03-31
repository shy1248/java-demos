package me.shy.spark.streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: window 算子实例， 每4秒统计前6秒的单词计数，注意次数的时间需要设置为 batch interval （此处为2秒）的倍数
 */
object WindowDemo {
    def main(args: Array[String]): Unit = {
        System.setProperty("hadoop.home.dir", "C:\\Users\\shy\\OneDrive\\sync\\backup\\winutils-master\\hadoop-2.8.3")
        val conf = new SparkConf().setMaster("local[2]").setAppName("WindowDemo")
        val ssc = new StreamingContext(conf, Seconds(2))
        ssc.socketTextStream("demos01", 9999)
            .flatMap(line => line.split("\\s+"))
            .map(word => (word, 1))
            // 第一个参数为 reduceByKey 执行的函数， 第二个为 window 的宽度， 第三个参数为 window 的时间间隔
            .reduceByKeyAndWindow((x: Int, y: Int) => x + y, Seconds(6), Seconds(4))
            .print()
        ssc.start()
        ssc.awaitTermination()
        ssc.stop(true)

    }

}
