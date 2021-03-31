package me.shy.spark.df

import org.apache.spark.sql.types.StringType
import org.apache.spark.sql.{Row, SQLContext, types}
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: 通过构建 schema 创建 DataFrame
 */
object DFUsingSchemaDemo {
    def main(args: Array[String]): Unit = {
        // for windows local test
        System.setProperty("hadoop.home.dir", "C:\\Users\\shy\\OneDrive\\sync\\backup\\winutils-master\\hadoop-2.8.3")
        val sc = new SparkContext(new SparkConf().setMaster("local[2]").setAppName("DataFrameAndRDDTranform"))

        // 构建 schema
        val columns = "name age"
        val schema = types.StructType(columns.split("\\s+")
            .map(column => types.StructField(column, StringType, true)))

        // 从文件初始化为 Row 类型的 RDD
        val personRDD = sc.textFile("D:\\demo.workspace\\people.txt")
            .map(line => line.split(","))
            .map(columns => Row(columns(0), columns(1).trim))

        val sqlContext = new SQLContext(sc)
        val personDF = sqlContext.createDataFrame(personRDD, schema)
        personDF.printSchema()
        personDF.show()
        // 注册表
        personDF.createTempView("person")
        //personDF.createOrReplaceTempView("person")
        // 使用 SQL，返回为 DataFrame 类型
        val res = sqlContext.sql("select name, age from person where age='30'")
        res.show()
        // 将 DataFrame 转换为 RDD，并调用 foreach 算子进行打印
        res.rdd.foreach(r => println(s"${r.getString(0)} ==== ${r.getString(1)}"))

        sc.stop()

    }

}
