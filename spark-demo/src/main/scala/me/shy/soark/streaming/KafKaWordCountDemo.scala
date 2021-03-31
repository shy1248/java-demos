package me.shy.spark.streaming

import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.SparkConf
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: kafka 消费数据实例，每个10秒钟统计前60秒出现次数最多的前3个单词
 */
object KafKaWordCountDemo {
    def main(args: Array[String]): Unit = {
        // Driver HA 配置，Driver 中维护了很多元数据信息，如 Kafka 的 Topic， offset 等信息
        // 生产环境中这样配置，当 Driver 挂掉后重启可以从 CheckPoint 中恢复这些信息
        val checkPointDirectory = "D:\\demo.workspace\\checkpoint"

        def getContext(): StreamingContext = {
            val conf = new SparkConf().setMaster("local[2]").setAppName("KafKaWordCountDemo")
            val ssc = new StreamingContext(conf, Seconds(2))
            ssc.checkpoint(checkPointDirectory)
            ssc
        }

        // windows 系统上需要设置
        System.setProperty("hadoop.home.dir", "C:\\Users\\shy\\OneDrive\\sync\\backup\\winutils-master\\hadoop-2.8.3")

        // 创建 StreamingContext，存在时直接获取，否则就创建
        val ssc = StreamingContext.getOrCreate(checkPointDirectory, getContext)

        // 定义 Kafka 参数以及 Topics
        val kafkaParams = Map[String, Object](
            "bootstrap.servers" -> "demos01:9092",
            "key.deserializer" -> classOf[StringDeserializer],
            "value.deserializer" -> classOf[StringDeserializer],
            "group.id" -> "sparkstreaming",
            "auto.offset.reset" -> "latest",
            "enable.auto.commit" -> (false: java.lang.Boolean)
        )
        val topics = Set("sparkstreamingdemo")

        // 从 Kafka 创建 DStrem，返回值的两个泛型 [String, String] 类型分别为：
        // 1.Kafka 消息 Key 的类型
        // 2.Kafka 消息 Value 的类型
        val kafkaDS = KafkaUtils.createDirectStream[String, String](ssc, LocationStrategies.PreferConsistent,
            ConsumerStrategies.Subscribe[String, String](topics, kafkaParams))

        // 进行单词统计计算
        kafkaDS.map(record => record.value())
            .flatMap(line => line.split("\\s+"))
            .map(word => (word, 1))
            // 每个10秒统计前60秒的单词
            .reduceByKeyAndWindow((v1: Int, v2: Int) => v1 + v2, Seconds(60), Seconds(10))
            .foreachRDD(rdd => {
                // 转换为 RDD
                val rowRDD = rdd.map(wordWithCount => Row(wordWithCount._1, wordWithCount._2))
                // 构造临时表的 schema
                val schema = StructType(
                    Array(
                        StructField("word", StringType, true),
                        StructField("count", IntegerType, true)
                    )
                )
                // 获取 SQLContext
                //val sqlContext = new SQLContext(rowRDD.context)
                val ss = SparkSession.builder().getOrCreate()
                // 将 RDD 转换为 DataFramne
                //val wordWithCountDF = sqlContext.createDataFrame(rowRDD, schema)
                val wordWithCountDF = ss.createDataFrame(rowRDD, schema)
                // 注册临时表
                wordWithCountDF.createOrReplaceTempView("wordcount")
                // 查询 SQL，根据 count 倒叙排列并取前三
                //val res = sqlContext.sql("select word, count from wordcount order by count desc limit 3")
                val res = ss.sql("select word, count from wordcount order by count desc limit 3")
                // 显示结果
                res.show()
            })


        ssc.start()
        ssc.awaitTermination()
        ssc.stop(true)
    }

}
