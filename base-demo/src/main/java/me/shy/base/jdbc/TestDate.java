package me.shy.base.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TestDate {

    public static void main(String[] args) {

        String jdbcDriver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost/bbs?user=root&password=root";
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        Timestamp ts = null;
        Date d = null;
        SimpleDateFormat sdf = null;
        Calendar c = null;

        try {
            Class.forName(jdbcDriver);
            conn = (Connection)DriverManager.getConnection(url);
            stmt = (Statement)conn.createStatement();
            String sql = "select pdate from article";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                d = rs.getDate("pdate");
                sdf = new SimpleDateFormat("yyyy年MM月dd日");
                System.out.println(sdf.format(d));
                ts = rs.getTimestamp("pdate");
                sdf = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss:SSSZ");
                System.out.println(sdf.format(ts));

                c = Calendar.getInstance();
                c.setTime(d);
                System.out.println(Calendar.YEAR);
                System.out.println(Calendar.MONTH);
                System.out.println(Calendar.DATE);
                System.out.println(Calendar.HOUR);
                System.out.println(Calendar.MINUTE);
                System.out.println(Calendar.SECOND);

            }

            //拿到系统当前时间的几种方法
            Date da = new Date();
            System.out.println(da);

            System.out.println(System.currentTimeMillis());//拿到系统当前时间从1970-1-1零时零分零秒到现在的毫秒数

            System.out.println(Calendar.getInstance());
            System.out.println(Calendar.getInstance().YEAR);

            TimeZone tz = TimeZone.getTimeZone("Japan");
            Calendar ca = Calendar.getInstance(tz);
            System.out.println(Calendar.HOUR_OF_DAY);

            //拿出所有的Time zone ID
            for (String str : tz.getAvailableIDs()) {
                System.out.println(str);
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
