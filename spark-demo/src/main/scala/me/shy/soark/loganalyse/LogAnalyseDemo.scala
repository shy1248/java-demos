package me.shy.spark.loganalyse

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: Tomcat log analyse
 *               1.The arg, min and max response content size
 *               2.A count of response's code returned
 *               3.All Ip that have accessed this server more than N times
 *               4.The top endpoints request by request times
 */
object LogAnalyseDemo {
    def main(args: Array[String]): Unit = {
        // for windows local test
        System.setProperty("hadoop.home.dir", "C:\\Users\\shy\\OneDrive\\sync\\backup\\winutils-master\\hadoop-2.8.3")

        val sc = new SparkContext(new SparkConf()
            .setMaster("local[2]")
            .setAppName("LogAnalyseDemo")
            // 调用 Kryo 序列化库，默认为 Java 序列化 API，性能较差
            .registerKryoClasses(Array(classOf[ApacheAccessLog]))
            // 当需要序列化的对象较大时需要设置该参数，默认为2M
            .set("spark.kryoserilizer.buffer.mb", "2m")

        )
        val logsRdd = sc.textFile("D:\\demo.workspace\\localhost_access_log.2019-07-03.txt")
            .map(line => ApacheAccessLog.parseLog(line))
            .cache()

        // The arg, min and max response content size
        println("================== 1 ==================")
        val contentSizeRdd = logsRdd.map(log => log.contentSize).cache()
        val maxContentSize = contentSizeRdd.max()
        val minContentSize = contentSizeRdd.min()
        val avgContentSize = contentSizeRdd.sum() / contentSizeRdd.count()
        contentSizeRdd.unpersist()
        println(s"The max content size is: $maxContentSize")
        println(s"The min content size is: $minContentSize")
        println(s"The avg content size is: $avgContentSize")

        // A count of response's code returned
        println("================== 2 ==================")
        logsRdd.map(log => (log.responseCode, 1))
            .reduceByKey(_ + _)
            .foreach(respCount => println(s"Response code ${respCount._1}'s count is ${respCount._2}."))

        // All Ip that have accessed this server more than N times
        println("================== 3 ==================")
        val top3Ipcount = logsRdd.map(log => (log.remoteIp, 1))
            .reduceByKey(_ + _)
            .filter(ipCount => ipCount._2 > 5)
            .take(3)
        for (countIp <- top3Ipcount) println(s"Top 3 ip address ${countIp._2} has ${countIp._1} times.")

        // The top endpoints request by request N times
        println("================== 4 ==================")
        val top5EndPoint = logsRdd.map(log => (log.endPoint, 1))
            .reduceByKey(_ + _)
            .map(endPointCount => (endPointCount._2, endPointCount._1))
            .sortByKey()
            .take(5)
        for (countEndPoint <- top5EndPoint) println(s"Top 3 endPoint accessed ${countEndPoint._2} has ${countEndPoint._1} times.")

        logsRdd.unpersist()
        sc.stop()
    }
}
