package me.shy.demo.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ArticleTree {

    String jdbcDriver = "com.mysql.jdbc.Driver";
    String databaseURL = "jdbc:mysql://localhost/bbs?user=root&password=root";

    public static void main(String[] args) {
        new ArticleTree().show();
    }

    public void show() {

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {

            Class.forName(jdbcDriver);
            conn = DriverManager.getConnection(databaseURL);
            stmt = conn.createStatement();
            String sql = "Select * from article where pid = 0";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                System.out.println(rs.getString("content"));
                tree(conn, rs.getInt("id"), 1);
            }

        } catch (ClassNotFoundException e) {

        } catch (SQLException e) {

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

    private void tree(Connection conn, int id, int level) {

        Statement stmt = null;
        ResultSet rs = null;

        String strPre = "";
        for (int i = 0; i < level; i++) {
            strPre += "++++";
        }

        try {

            stmt = conn.createStatement();
            String sql = "select * from article where pid = " + id;
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                System.out.println(strPre + rs.getString("content"));
                if (rs.getInt("isleaf") != 0) {
                    tree(conn, rs.getInt("id"), level + 1);
                }
            }
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

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
