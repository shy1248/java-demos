package me.shy.spark

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: 使用 broadcast 方式实现 join 操作，解决 join 操作产生数据倾斜问题
 */
object MapJoinDemoByScala {
    def main(args: Array[String]): Unit = {
        // for windows local test
        System.setProperty("hadoop.home.dir", "C:\\Users\\shy\\OneDrive\\sync\\backup\\winutils-master\\hadoop-2.8.3")
        val sc = new SparkContext(new SparkConf().setMaster("local[2]").setAppName("MapJoinDemo"))

        val namesRdd = sc.parallelize(Array((1, "Tom"), (2, "Jerry"), (3, "Mickey")))
        val gradesRdd = sc.parallelize(Array((1, 90), (2, 92), (3, 91)))
        // 将较小的 RDD 广播出去
        val littleRDD = sc.broadcast(namesRdd.collect())

        //// 使用 map 算子方式
        //val res = gradesRdd.map(grade => {
        //    def find(): Tuple3[Int, Int, String] = {
        //        for (name <- littleRDD.value) {
        //            if (grade._1 == name._1) {
        //                return (grade._1, grade._2, name._2)
        //            }
        //        }
        //        return (grade._1, grade._2, null)
        //    }
        //
        //    find()
        //})

        // 使用 mapPartition 算子，防止使用 map 造成每条记录都需要取读取广播变量
        val res = gradesRdd.mapPartitions(grades => {
            var cacheLittleRDD = scala.collection.mutable.Map[Int, String]()
            var partitionBffer = scala.collection.mutable.ListBuffer[Tuple3[Int, Int, String]]()

            for (name <- littleRDD.value) {
                cacheLittleRDD += (name._1 -> name._2)
            }

            for (grade <- grades) {
                partitionBffer.append((grade._1, grade._2, cacheLittleRDD(grade._1)))
            }

            partitionBffer.iterator
        })

        res.foreach(x => println(x))
        sc.stop()
    }

}
