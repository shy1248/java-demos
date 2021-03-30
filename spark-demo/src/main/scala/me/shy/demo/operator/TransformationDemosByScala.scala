package me.shy.demo.operator

import org.apache.spark.{Partitioner, SparkConf, SparkContext, TaskContext}

import scala.collection.mutable.ListBuffer
import scala.util.Random

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 */
object TransformationDemosByScala {
    def main(args: Array[String]): Unit = {
        // for windows local test
        System.setProperty("hadoop.home.dir", "C:\\Users\\shy\\OneDrive\\sync\\backup\\winutils-master\\hadoop-2.8.3")
        val sc = new SparkContext(new SparkConf().setMaster("local[2]").setAppName("TransformationDemos"))

        map(sc)
        filter(sc)
        flatMap(sc)
        groupByKey(sc)
        reduceByKey(sc)
        sortByKey(sc)
        join(sc)
        cogroup(sc)
        unionAndIntersection(sc)
        distinct(sc)
        cartesian(sc)
        mapPartitions(sc)
        repartition(sc)
        coalesce(sc)
        sample(sc)
        aggregateByKey(sc)
        mapPartitionsWithIndex(sc)
        repartitionAndSortWithinPartitions(sc)

        // stop sparkSparkContext
        sc.stop()
    }

    def repartition(sc: SparkContext) = {
        sc.parallelize(Array(1, 2, 3, 4, 5, 6, 7, 8, 9))
            .repartition(3)
            .foreach(r => println(s"Thread: ${Thread.currentThread().getId}, Partition: ${
                TaskContext.getPartitionId()
            }, ====> ${r}"))
    }

    def repartitionAndSortWithinPartitions(sc: SparkContext) = {
        sc.parallelize(Array(1, 2, 3, 4, 5, 6, 7, 8, 10))
            .map((_, Random.nextInt(10)))
            .repartitionAndSortWithinPartitions(new Partitioner() {
                override def numPartitions: Int = 2

                override def getPartition(key: Any): Int = key.hashCode() & 1
            }).foreach(r => println(s"Partition: ${TaskContext.getPartitionId()}, ====> ${r}"))
    }

    def mapPartitionsWithIndex(sc: SparkContext) = {
        sc.parallelize(Array(1, 2, 3, 4, 5, 6, 7, 8, 10), 2)
            .mapPartitionsWithIndex((index, nums) => {
                var res = ListBuffer[String]()
                for (num <- nums) {
                    res.append(s"num $num at index: $index")
                }
                res.iterator
            }).foreach(r => println(s"Partition: ${TaskContext.getPartitionId()}, ====> ${r}"))
    }

    def aggregateByKey(sc: SparkContext) = {
        /**
         * 其实reduceBykey就是aggregateByKey的简化版。 就是aggregateByKey多提供了一个函数
         * 类似于Mapreduce的combine操作（就在map端执行reduce的操作）
         *
         * 第一个参数代表的是每个key的初始值初始值：
         * 第二个是一个函数，类似于map-side的本地聚合
         * 第三个也是个函数，类似于reduce的全局聚合
         */
        sc.parallelize(Array(("Tom", 90), ("tom", 80), ("Jerry", 92), ("Tom", 65), ("Jerry", 93)))
            .aggregateByKey(0, 2)(_ + _, _ + _)
            .foreach(r => println(s"Partition: ${TaskContext.getPartitionId()}, ====> ${r}"))
    }

    def sample(sc: SparkContext) = {
        // 对RDD中的集合内元素进行采样，第一个参数withReplacement是true表示有放回取样，false表示无放回。第二个参数表示比例
        sc.parallelize(Array(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))
            .sample(true, 0.1)
            .foreach(r => println(s"Partition: ${TaskContext.getPartitionId()}, ====> ${r}"))
    }

    def coalesce(sc: SparkContext) = {
        /**
         * repaitition其实只是coalesce的shuffle为true的简易的实现版本
         *
         * N 代表的是原来的分区数
         * M numPartitions  新的分区数
         * shuffle  是否进行shuffle
         *
         * 1）N < M  需要将shuffle设置为true。
         * 2）N > M 相差不多，N=1000 M=100  建议 shuffle=false 。
         * 父RDD和子RDD是窄依赖
         * 3）N >> M  比如 n=100 m=1  建议shuffle设置为true，这样性能更好。
         * 设置为false，父RDD和子RDD是窄依赖，他们同在一个stage中。造成任务并行度不够，从而速度缓慢。
         *
         */
        sc.parallelize(Array(1, 2, 3, 4, 5, 6, 7, 8, 9))
            .coalesce(3, true)
            .foreach(r => println(s"Partition: ${TaskContext.getPartitionId()}, ====> ${r}"))
    }

    def mapPartitions(sc: SparkContext) = {
        sc.parallelize(Array(1, 2, 3, 4, 5, 6, 7, 8, 10), 2)
            .mapPartitions(nums => {
                var res = ListBuffer[Int]()
                for (num <- nums) {
                    res.append(num * 2)
                }
                res.iterator
            }).foreach(r => println(r))
    }

    def cartesian(sc: SparkContext) = {
        val numbers = sc.parallelize(Array(1, 2, 3, 4))
        val letters = sc.parallelize(Array('A', 'B', 'C'))
        numbers.cartesian(letters).foreach(r => println(r))
    }

    def distinct(sc: SparkContext) = sc.parallelize(Array(1, 2, 3, 3, 4, 2, 5, 1)).distinct().foreach(r => println(r))

    def unionAndIntersection(sc: SparkContext) = {
        val numbers = sc.parallelize(Array(1, 2, 3, 4, 5))
        val otherNumbers = sc.parallelize(Array(4, 5, 6, 7, 8, 9))
        numbers.union(otherNumbers).foreach(r => println(r))
        numbers.intersection(otherNumbers).foreach(r => println(r))
    }

    def cogroup(sc: SparkContext) = {
        val sexRdd = sc.parallelize(Array((1, "Tom"), (2, "Jerry"), (3, "Mickey"), (1, "Bobo")))
        val gradesRdd = sc.parallelize(Array((1, 90), (2, 92), (3, 91), (1, 80), (2, 82), (4, 81)))
        sexRdd.cogroup(gradesRdd).foreach(r => println(r))
    }

    def join(sc: SparkContext) = {
        val namesRdd = sc.parallelize(Array((1, "Tome"), (2, "Jerry"), (3, "Mickey")))
        val gradesRdd = sc.parallelize(Array((1, 90), (2, 92), (3, 91)))
        namesRdd.join(gradesRdd).foreach(r => println(r))
    }

    def sortByKey(sc: SparkContext) = {
        val grades = Array((90, "Tom"), (92, "Jerry"), (91, "Mickey"))
        sc.parallelize(grades).sortByKey().foreach(r => println(r))
    }

    def reduceByKey(sc: SparkContext) = {
        val sex = Array(("M", "Tom"), ("F", "Jerry"), ("M", "Mickey"))
        sc.parallelize(sex).reduceByKey(_ + "," + _).foreach(r => println(s"${r._1} ====> ${r._2}"))
    }

    def groupByKey(sc: SparkContext) = {
        val sex = Array(("M", "Tom"), ("F", "Jerry"), ("M", "Mickey"))
        sc.parallelize(sex).groupByKey().foreach(r => println(s"${r._1} ====> ${r._2}"))
    }

    def flatMap(sc: SparkContext) = {
        // words to chars
        val names = Array("Jerry", "Tom", "Mickey")
        sc.parallelize(names).flatMap(name => name.toCharArray).foreach(r => println(r))
    }

    def filter(sc: SparkContext) = {
        // leave only an even number
        val numbers = Array(1, 2, 3, 4, 5, 6, 7, 8, 9)
        sc.parallelize(numbers).filter(num => (num & 1) == 0).foreach(r => println(r))
    }

    def map(sc: SparkContext) = {
        //// numbers with power 2
        val numbers = Array(1, 2, 3, 4, 5, 6, 7, 8, 9)
        sc.parallelize(numbers).map(math.pow(_, 2)).foreach(r => println(r))
    }


}
