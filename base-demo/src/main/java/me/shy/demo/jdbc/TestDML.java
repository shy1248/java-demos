package me.shy.demo.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestDML {

    public static void main(String[] args) {

        String jdbcDriver = "com.mysql.jdbc.Driver";
        String databaseURL = "jdbc:mysql://localhost/bbs?user=root&password=root";
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            Class.forName(jdbcDriver);
            conn = DriverManager.getConnection(databaseURL);
            stmt = conn.createStatement();
            String sql = "insert into article values (null,4,1,'ÔÙ¶¥¡£¡£¡£','ÔÙ¶¥¡£¡£¡£',now(),0)";
            //System.out.println(sql);
            stmt.executeUpdate(sql);
            stmt.executeUpdate("commit");
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
