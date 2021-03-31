package me.shy.phoenix;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @Since: 2020/5/7 9:53
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 *
 **/
public class PhoenixDemo {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Class.forName("org.apache.phoenix.jdbc.PhoenixDriver");

        Properties props = new Properties();
        props.setProperty("phoenix.query.timeoutMs", "1200000");
        props.setProperty("hbase.rpc.timeout", "1200000");
        props.setProperty("hbase.client.scanner.timeout.period", "1200000");

        Connection connection = DriverManager.getConnection("jdbc:phoenix:10.25.193.250:2181", props);
        // Connection connection = DriverManager.getConnection("jdbc:phoenix:10.25.193.250:2181");
        System.out.println("获取连接成功！");

        String sql = "select \"copyright_id\", \"copyright_name\", \"cp_id\" from \"copyright\" limit 5";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            System.out.println(resultSet.getString(1));
            System.out.println(resultSet.getString(2));
            System.out.println(resultSet.getString(3));
        }

        try {
            if (connection != null && !connection.isClosed())
                connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("关闭连接失败!");
        }

    }
}
