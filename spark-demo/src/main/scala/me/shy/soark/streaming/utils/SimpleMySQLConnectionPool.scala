package me.shy.spark.streaming.utils

import java.sql.{Connection, DriverManager}
import java.util


/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 */
object SimpleMySQLConnectionPool {
    private val maxConnectionNum = 8
    private val connectionCount = 0
    private val pool = new util.LinkedList[Connection]()

    {
        Class.forName("com.mysql.jdbc.Driver")
    }

    // release connection to pool
    def release(conn: Connection): Unit = {
        pool.push(conn)
    }

    def getConnection(): Connection = {
        AnyRef.synchronized {
            if (pool.isEmpty) {
                for (i <- 1 to maxConnectionNum) {
                    val conn = DriverManager.getConnection("jdbc:mysql://demos01:3306/sparkstreaming_demo", "shy", "")
                    pool.push(conn)
                    connectionCount + 1
                }
            }
            pool.poll()
        }

    }

}
