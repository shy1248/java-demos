package me.shy.spark.dataSet

import org.apache.spark.sql.SparkSession

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 */
object DataSetTransformDemo {
    def main(args: Array[String]): Unit = {
        System.setProperty("hadoop.home.dir", "C:\\Users\\shy\\OneDrive\\sync\\backup\\winutils-master\\hadoop-2.8.3")
        val spark = SparkSession.builder()
            .appName("DataSetDemo")
            .master("local[1]")
            .getOrCreate()

        val classDF = spark.read.json("D:\\demo.workspace\\class.json")
        val studentDF = spark.read.json("D:\\demo.workspace\\student.json")

        // 导入隐式转化和 SQL 函数
        import org.apache.spark.sql.functions._
        import spark.implicits._

        studentDF.filter("new <> 'no'")
            //.join(classDF, classDF("id") === studentDF("classID"))
            .join(classDF, $"id" === $"classID")
            .groupBy(classDF("classname"), studentDF("gender"))
            //.agg("age" -> "avg")
            .agg(avg("age"))
            .show()

        spark.stop()
    }

}
