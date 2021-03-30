package me.shy.demo

import org.apache.spark.{SparkConf, SparkContext}

import scala.util.Random

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: RDD 扩容，解决 join 操作时由于大量 key 分布均匀造成的数据倾斜问题
 */
object RDDExtendDemo {
    def main(args: Array[String]): Unit = {
        System.setProperty("hadoop.home.dir", "C:\\Users\\shy\\OneDrive\\sync\\backup\\winutils-master\\hadoop-2.8.3")
        val sc = new SparkContext(new SparkConf().setMaster("local[2]").setAppName("RDDExtendDemo"))
        val namesRdd = sc.parallelize(Array(("001", "Tom"), ("002", "Jerry"), ("003", "Mickey")))
        val gradesRdd = sc.parallelize(Array(("001", 90), ("002", 92), ("003", 91)))
        // 将 key 分布较均匀的 RDD 扩容99倍
        val extendedRDD = namesRdd.flatMap(name => {
            var res = scala.collection.mutable.ListBuffer[Tuple2[String, String]]()
            for (i <- 0 to 99) {
                res.append((s"$i#${name._1}", name._2))
            }
            res.iterator
        })
        // 将 key 分布不均衡的 RDD 99以内的随机数打散
        val randomRDD = gradesRdd.map(grade => (s"${Random.nextInt(99)}#${grade._1}", grade._2))
        randomRDD.join(extendedRDD).foreach(x => println(s"${x._1.split("#")(1)}, ${x._2._1}, ${x._2._2}"))
        sc.stop()
    }

}
