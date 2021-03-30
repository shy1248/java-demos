package me.shy.demo.partition

import me.shy.demo.datasource.CustomSingleDataSourceByScala
import org.apache.flink.api.common.functions.Partitioner
import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 */
object CustomPartitionByScala {
    def main(args: Array[String]): Unit = {
        val environment = StreamExecutionEnvironment.getExecutionEnvironment
        val text = environment.addSource(new CustomSingleDataSourceByScala).setParallelism(1)
        text.map(Tuple1(_))
                .partitionCustom(new CustomPartitionByScala, 0)
                .map(t => {
                    val value = t._1;
                    val tid = Thread.currentThread().getId;
                    println(s"Current thread is: $tid, and value is: $value");
                    value
                })
                .print()

        environment.execute("CustomPartitionByScala")

    }
}

class CustomPartitionByScala extends Partitioner[String] {
    override def partition(key: String, numPartitions: Int): Int = (key.toLong % 2).asInstanceOf[Int]
}
