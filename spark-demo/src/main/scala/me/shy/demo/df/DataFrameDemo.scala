package me.shy.demo.df

import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 */
object DataFrameDemo {
    def main(args: Array[String]): Unit = {
        // for windows local test
        System.setProperty("hadoop.home.dir", "C:\\Users\\shy\\OneDrive\\sync\\backup\\winutils-master\\hadoop-2.8.3")
        val conf = new SparkConf().setMaster("local[2]").setAppName("DataFrameDemo")
        val sc = new SparkContext(conf)
        val sqlContext = new SQLContext(sc)

        // 读取 json 文件，将 json 文件转为 DataFrame
        val df = sqlContext.read.json("D:\\demo.workspace\\people.json")
        // 显示 DataFrame 内容
        df.show()
        // 打印 DataFrame 的结构
        df.printSchema()
        // 查询 name 列
        df.select("name").show()

        sc.stop()
    }
}
