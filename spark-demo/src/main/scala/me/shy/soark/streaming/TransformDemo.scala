package me.shy.spark.streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, State, StateSpec, StreamingContext}

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: transform 算子作用在 DStream 上，用于将 DStream 转换为 RDD，并与其它的 RDD 做关联等操作
 *               本例中用单词计数，排除 [,.?!] 等标点实现类似黑名单过滤的效果
 */
object TransformDemo {
    def main(args: Array[String]): Unit = {
        System.setProperty("hadoop.home.dir", "C:\\Users\\shy\\OneDrive\\sync\\backup\\winutils-master\\hadoop-2.8.3")
        // Streaming 模式本地模式的 local[2] 最好少要设置2个线程，有一个线程需要用来接收数据，其它的线程来处理
        val conf = new SparkConf().setMaster("local[2]").setAppName("TransformDemo")
        val ssc = new StreamingContext(conf, Seconds(2))
        ssc.checkpoint("D:\\demo.workspace\\checkpoint")

        val wordCountDS = ssc.socketTextStream("demos01", 9999)
            .flatMap(line => line.split("\\s+"))
            .map(word => (word, 1))

        // 定义黑名单单词列表 RDD
        val blackListRDD = ssc.sparkContext.parallelize(Array(",", ".", "?", "!")).map(bw => (bw, "flag"))

        wordCountDS.transform(rdd => {
            // left join 完成后，返回的格式为 (word, (1, flag|empty))，为 empty 的表示 word 不在黑名单中
            rdd.leftOuterJoin(blackListRDD)
                // 过滤掉有 flag 的单词（有 flag 表示存在于黑名单中）
                .filter(t => t._2._2.isEmpty)
                // 重新封装返回的结果，以便进行接下来的计算
                .map(t => (t._1, 1))
        })

            // 调用 mapWithState 进行累计计数
            .mapWithState(StateSpec.function((word: String, one: Option[Int], state: State[Int]) => {
                val sum = one.getOrElse(0) + state.getOption().getOrElse(0)
                val output = (word, sum)
                state.update(sum)
                output
            }))
            // 调用 print action，默认打印每个 RDD 的前10条数据
            .print(10)

        ssc.start()
        ssc.awaitTermination()
        ssc.stop(true)
    }

}
