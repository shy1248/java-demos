package me.shy.demo.df

import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: parquet 文件操作
 */
object ParquetFileOperateDemo {
    def main(args: Array[String]): Unit = {
        // for windows local test
        System.setProperty("hadoop.home.dir", "C:\\Users\\shy\\OneDrive\\sync\\backup\\winutils-master\\hadoop-2.8.3")
        val sc = new SparkContext(new SparkConf().setMaster("local[2]").setAppName("ParquetFileOperateDemo"))
        val sqlContext = new SQLContext(sc)

        val usersDF = sqlContext.read.load("D:\\demo.workspace\\users.parquet")
        usersDF.printSchema()
        usersDF.show()
        usersDF.createTempView("users")
        val res = sqlContext.sql("select favorite_color from users where name='Ben'")
        res.show()

        sc.stop()
    }

}
