package me.shy.mybatis;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import me.shy.mybatis.domain.User;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 *
 * 只需要在项目下的META-INFO目录下添加context.xml，并在里边进行配置:
 * <Context>
 *     <Resource
 *             driverClassName="org.sqlite.JDBC"             -- 驱动类名
 *             url="jdbc:sqlite:D:/demo.workspace/demo.db"  -- 连接url
 *             username=""                                   -- 用户名
 *             password=""                                   -- 密码
 *             maxActive="5"                                 -- 最大活动的连接
 *             maxIdle="2"                                   -- 最大空闲的连接
 *             maxWait="10000"                               -- 当连接空闲多久时释放
 *             auth="Container"                              -- 默认就为Container
 *             type="javax.sql.DataSource"                   -- 数据源类型
 *             name="demo"                                   -- 连接池名称
 *     />
 * </Context>
 */
@WebServlet("/jndi") public class JNDITestServlet extends HttpServlet {
    @Override protected void service(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource)ctx.lookup("java:comp/env/demo");
            connection = ds.getConnection();
            ps = connection.prepareStatement("select * from user");
            rs = ps.executeQuery();
            while (rs.next()) {
                resp.getWriter().println(new User(rs.getInt(1), rs.getString(2), rs.getInt(3)));
            }
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
