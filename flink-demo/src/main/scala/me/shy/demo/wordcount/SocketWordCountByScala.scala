/**
  * @Since: 2019-12-21 22:31:24
  * @Author: shy
  * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
  * @Version: v1.0
  * @Description: -
  */

package me.shy.flink.wordcount

import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.scala.DataStream

object SocketWordCountByScala {
  def main(args: Array[String]): Unit = {
    var host = "localhost"
    var port = 9000

    try {
      val parameters = ParameterTool.fromArgs(args)
      host = parameters.get("host")
      port = Integer.parseInt(parameters.get("port"))
      println(s"Listen on $host:$port ...")
    } catch {
      case e: Exception =>
        host = "localhost"
        println("Not all host or port gaven, using default: localhost:9000!")
    }
    val environment: StreamExecutionEnvironment =
      StreamExecutionEnvironment.getExecutionEnvironment
    val text: DataStream[String] = environment.socketTextStream(host, port)
    text
      .flatMap(_.split("\\s+"))
      .map((_, 1))
      .setParallelism(1)
      .keyBy(0)
      //   .timeWindow(Time.seconds(2), Time.seconds(1))
      .sum(1)
      .print()
    environment.execute("SocketWordCountByScala")
  }
}
