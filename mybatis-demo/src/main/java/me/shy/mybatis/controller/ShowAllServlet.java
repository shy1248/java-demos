package me.shy.mybatis.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.shy.mybatis.domain.User;
import me.shy.mybatis.service.UserService;
import me.shy.mybatis.service.impl.UserServiceImpl;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 */
@WebServlet("/show") public class ShowAllServlet extends HttpServlet {
    UserService userService = new UserServiceImpl();

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        List<User> users = userService.showAll();
        req.setAttribute("users", users);
        req.getRequestDispatcher("/show.jsp").forward(req, resp);
    }
}
