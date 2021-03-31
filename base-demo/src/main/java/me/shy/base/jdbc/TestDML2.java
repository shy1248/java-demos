package me.shy.base.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestDML2 {

    public static void main(String[] args) {

        String jdbcDriver = "com.mysql.jdbc.Driver";
        String databaseURL = "jdbc:mysql://localhost/bbs?user=root&password=root";
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        if (args.length != 7) {
            System.out.println("Parameter Error!");
            System.exit(-1);
        }

        String str1 = args[0];
        String str2 = args[1];
        String str3 = args[2];
        String str4 = args[3];
        String str5 = args[4];
        String str6 = args[5];
        String str7 = args[6];

        try {
            Class.forName(jdbcDriver);
            conn = DriverManager.getConnection(databaseURL);
            stmt = conn.createStatement();
            String sql = "insert into article values (" + str1 + str2 + str3 + str4 + str5 + str6 + str7 + ")";
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
