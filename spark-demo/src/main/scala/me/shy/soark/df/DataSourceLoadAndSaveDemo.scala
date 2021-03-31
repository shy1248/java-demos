package me.shy.spark.df

import org.apache.spark.sql.{SQLContext, SaveMode}
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 */
object DataSourceLoadAndSaveDemo {
    def main(args: Array[String]): Unit = {
        // for windows local test
        System.setProperty("hadoop.home.dir", "C:\\Users\\shy\\OneDrive\\sync\\backup\\winutils-master\\hadoop-2.8.3")
        val sc = new SparkContext(new SparkConf().setMaster("local[2]").setAppName("DataSourceLoadAndSaveDemo"))
        val sqlContext = new SQLContext(sc)
        // 读取 json 文件
        val df = sqlContext.read.json("D:\\demo.workspace\\people.json")
        //sqlContext.read.format("json").load("D:\\demo.workspace\\people.json")
        //// 不指定文件格式，默认为 parquet 格式（HDFS的默认格式）
        //sqlContext.read.load("D:\\demo.workspace\\users.parquet")
        //sqlContext.read.format("parquet").load(" D :\\demo.workspace\\users.parquet")

        // 保存文件，不指定格式，默认为 parquet
        //df.select("name", "age").write.format("parquet").save("people.parquet")
        // 同时可指定保存策略，共四种：ErrorIfExists，Append，Overwrite，Ignore，默认为 ErrorIfExists，表示如果存在就报错
        df.select("name", "age").write.mode(saveMode = SaveMode.Append).format("parquet").save("people.parquet")

        sc.stop()
    }

}
