package me.shy.servlet;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 */

// 使用注解的方式来配置servlet配置，就不需要在web.xml文件中配置，servlet3.0推荐
@WebServlet("/ajaxServlet") public class AjaxServlet extends HttpServlet {

    @Override protected void service(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        try {
            TimeUnit.SECONDS.sleep(6);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        String method = req.getMethod();
        resp.getWriter().write(
            "This message is send by AjaxServlet!\nName: " + name + ", Password: " + password + ", " + "Method: "
                + method + "\nNow: " + DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.ms")
                .format(ZonedDateTime.now()));
    }
}
