package me.shy.demo.source

import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.functions.source.{RichSourceFunction, SourceFunction}
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 */
object CustomSingleDataSourceByScala {
    def main(args: Array[String]): Unit = {
        val environment = StreamExecutionEnvironment.getExecutionEnvironment
        val text = environment.addSource(new CustomSingleDataSourceByScala()).setParallelism(1)
        text.map(word => println(s"Recevied data: $word")).print()
        environment.execute("CustomSingleDataSource")
    }

}

class CustomSingleDataSourceByScala extends RichSourceFunction[String] {
    var isRunning = true
    var count = 0L

    override def run(ctx: SourceFunction.SourceContext[String]): Unit = {
        while (isRunning) {
            count += 1
            ctx.collect(String.valueOf(count))
            Thread.sleep(1000)
        }

    }

    override def cancel(): Unit = {
        isRunning = false
    }
}