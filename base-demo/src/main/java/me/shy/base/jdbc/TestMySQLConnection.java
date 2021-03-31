package me.shy.base.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * 此程序为JDBC编程常见的流程
 * 1. new一个DATABASE的Driver：Class.forName(jdbcDriver);
 * 2. 通过DriverManager拿到数据库的连接：conn =DriverManager.getConnection(databaseURL);
 * 3. 定义SQL执行语句类： stmt = conn.createStatement();
 * 4. 调用SQL执行语句类的execute*（）方法
 * 					rs = stmt.executeQuery("select * from article");
 * 					rs = stmt.executeUpdata("insert .....");
 * 					rs = stmt.executeUpdata("updata .....");
 * 					rs = stmt.executeUpdata("delete .....");
 * 5. 定义一个ResultSet类用来接收语句执行所返回的结果
 * 6. 调用结果集类（ResultSet类）的get*（）方法获取结果集中的内容，以供java程序使用
 * 					rs.getString(...)；rs.getInt(...)；.............
 * 7. 用rs.next()方法配合while语句来循环遍历结果集
 * 8. 最后关闭相应的连接，注意catch相应的Exception以及Finally语句的写法
 *
 * Author:yushuibo
 */
public class TestMySQLConnection {

    public static void main(String[] args) {

        String jdbcDriver = "com.mysql.jdbc.Driver";
        String databaseURL = "jdbc:mysql://localhost/bbs?user=root&password=root";

        Connection conn = null;
        ResultSet rs = null;
        Statement stmt = null;

        try {
            Class.forName(jdbcDriver);
            conn = DriverManager.getConnection(databaseURL);
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select * from article");
            while (rs.next()) {
                System.out.println(rs.getString("title"));
                //System.out.println(rs.getString(4));
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            try {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
                if (conn != null) {
                    conn.close();
                    conn = null;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
