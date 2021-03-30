package me.shy.demo.forwardAndRedirect;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description:
 *
 * forward：请求转发，属于同一次请求，共享一个request对象，因此可用request对象的setArribtue和getArrtibute进行数据流转。
 *          可能造成表单重复提交的问题。浏览器地址栏URL不会发生改变。
 * redirect：请求重定向，属于不同的两次请求，request对象不同。浏览器地址栏URL发生改变。
 *
 */
public class LoginServlet extends HttpServlet {

    private static final String VALID_USER = "demo";
    private static final String VALID_PASS = "123456";

    @Override protected void service(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        System.out.println("Login with username=" + username + ", password=" + password + ".");
        boolean isSuccessed = false;
        String respMsg = "";
        if (null == username || username.trim().equals("")) {
            respMsg = "Username must not be empty!";
        } else if (null == password || password.trim().equals("")) {
            respMsg = "Password must not be empty!";
        } else if (!username.equals(VALID_USER) || !password.equals(VALID_PASS)) {
            respMsg = "Username or password is invalid!";
        } else {
            respMsg = "Login success!";
            isSuccessed = true;
        }

        if (isSuccessed) {
            System.out.println("Login successful, redirect!");
            resp.sendRedirect("main");
        } else {
            System.out.println("Login failed, forward!");
            req.setAttribute("isSuccessed", false);
            req.setAttribute("errorInfo", respMsg);
            req.getRequestDispatcher("index").forward(req, resp);
        }
    }
}
