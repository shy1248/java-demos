package me.shy.demo.streaming

import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.{State, StateSpec, StreamingContext}
import org.apache.spark.{SparkConf, streaming}

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: mapWithState 与 updateStateByKey 功能一样，但是性能以及可维护的 key 的数量比 updateBykey 高很多
 */
object MapWithStateDemo {
    def main(args: Array[String]): Unit = {
        // for windows local test
        System.setProperty("hadoop.home.dir", "C:\\Users\\shy\\OneDrive\\sync\\backup\\winutils-master\\hadoop-2.8.3")
        // Streaming 模式本地模式的 local[2] 最好少要设置2个线程，有一个线程需要用来接收数据，其它的线程来处理
        val conf = new SparkConf().setAppName("MapWithStateDemo").setMaster("local[2]")
        // 指定处理批次的时间间隔为1秒
        val ssc = new StreamingContext(conf, streaming.Seconds(1))
        // 设置 checkpoint 目录
        ssc.checkpoint("D:\\demo.workspace\\checkpoint")

        val wordCountDS = ssc.socketTextStream("demos01", 9999, storageLevel = StorageLevel.MEMORY_AND_DISK)
            .flatMap(line => line.split("\\s+"))
            .map(word => (word, 1))

        // mapWithSate 算子开始
        // 定义映射函数，也可以写成 lambda 函数
        val mappingFunc = (word: String, one: Option[Int], state: State[Int]) => {
            // 当前的数量加上上截止上一批次结束的总数，即为当前的总数
            val sum = one.getOrElse(0) + state.getOption().getOrElse(0)
            // 定义返回值
            val output = (word, sum)
            // 更新上一批次结束的总数为当前总数，以便下一批次调用
            state.update(sum)
            // 返回
            output
        }

        // initialRDD
        val initialRDD = ssc.sparkContext.parallelize(Array(("hello", 2), ("hi", 3)))
        // initialState 就是一个初始的状态，表示从这个流开始做计算
        val stateDS = wordCountDS.mapWithState(StateSpec.function(mappingFunc).initialState(initialRDD))

        // 打印每个 RDD 前10条数据
        stateDS.print(10)
        ssc.start()
        ssc.awaitTermination()
        ssc.stop(true)
    }
}
