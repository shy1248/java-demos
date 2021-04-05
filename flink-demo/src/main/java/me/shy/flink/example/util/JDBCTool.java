/**
 * @Date        : 2021-04-03 09:53:34
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : -
 */
package me.shy.flink.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCTool {

    public static Connection connect() {
        Properties properties = new Properties();
        String driver = null;
        String url = null;
        String username = null;
        String password = null;
        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("example-jdbc.properties"));
            driver = properties.getProperty("jdbc.driver");
            url = properties.getProperty("jdbc.url");
            username = properties.getProperty("jdbc.username");
            password = properties.getProperty("jdbc.password");
            Class.forName(driver);
            return DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void close(Connection connection) {
        try {
            if (null != connection && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean execute(Connection connection, String sql, Object[] data) {
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);
            if (null != data) {
                for (int i = 0; i < data.length; i++) {
                    pstmt.setObject(i + 1, data[i]);
                }
            }
            return pstmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (null != pstmt) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static int[] executeBatch(Connection connection, String sql, Object[][] data) {
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);
            if (null != data) {
                for (int i = 0; i < data.length; i++) {
                    for (int j = 0; j < data[i].length; j++) {
                        pstmt.setObject(i + 1, data[i][j]);
                    }
                    pstmt.addBatch();
                }
            }
            return pstmt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (null != pstmt) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Object queryOne(Connection connection, String sql, Object[] data) {
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);
            if (null != data) {
                for (int i = 0; i < data.length; i++) {
                    pstmt.setObject(i + 1, data[i]);
                }
            }
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                return resultSet.getObject(1);
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (null != pstmt) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void query(Connection connection, String sql, Object[] data, QueryCallback callback) {
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);
            if (null != data) {
                for (int i = 0; i < data.length; i++) {
                    pstmt.setObject(i + 1, data[i]);
                }
            }
            ResultSet resultSet = pstmt.executeQuery();
            callback.callback(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (null != pstmt) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public interface QueryCallback {
        default void callback(ResultSet resultSet) throws SQLException {
            int columnCount = resultSet.getMetaData().getColumnCount();
            Object[] temp = new Object[columnCount];
            for (int i = 0; i < columnCount; i++) {
                temp[i] = resultSet.getMetaData().getColumnLabel(i);
            }
            System.err.println(String.join("\t", temp.toString()));
            while (resultSet.next()) {
                for (int j = 0; j < columnCount; j++) {
                    temp[j] = resultSet.getObject(j);
                }
                System.out.println(String.join("\t", temp.toString()));
            }
            if (null != resultSet) {
                resultSet.close();
            }
        }
    }
}
