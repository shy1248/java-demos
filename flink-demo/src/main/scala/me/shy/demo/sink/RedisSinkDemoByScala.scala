package me.shy.demo.sink

import io.lettuce.core.RedisURI
import io.lettuce.core.cluster.RedisClusterClient
import io.lettuce.core.cluster.api.StatefulRedisClusterConnection
import io.lettuce.core.cluster.api.sync.RedisClusterCommands
import org.apache.flink.api.scala._
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.functions.sink.{RichSinkFunction, SinkFunction}
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 */
object RedisSinkDemoByScala {
    def main(args: Array[String]): Unit = {
        val environment = StreamExecutionEnvironment.getExecutionEnvironment
        environment.setParallelism(1)
        val text = environment.socketTextStream("demos01", 9000, '\n')
        text.flatMap(_.split("\\s+")).addSink(new RedisSink)
        environment.execute("RedisSinkDemoByScala")
    }
}

class RedisSink extends RichSinkFunction[String] {
    var client: RedisClusterClient = _
    var connecttion: StatefulRedisClusterConnection[String, String] = _
    var syncCommand: RedisClusterCommands[String, String] = _

    override def open(parameters: Configuration): Unit = {
        val redisURI = RedisURI.Builder.redis("demos01", 6388).build()
        this.client = RedisClusterClient.create(redisURI)
        this.connecttion = client.connect()
        this.syncCommand = connecttion.sync()
        println("Redis client is connected!")
    }

    override def close(): Unit = {
        this.connecttion.close()
        this.client.shutdown()
        println("Redis client is shutdown!")
    }

    override def invoke(value: String, context: SinkFunction.Context): Unit = {
        this.syncCommand.lpush("l_words_scala", value.toString)
        println(s"Write value: $value to redis key: l_words_scala...")
    }
}
