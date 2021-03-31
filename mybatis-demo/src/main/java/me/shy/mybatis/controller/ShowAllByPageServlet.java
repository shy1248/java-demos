package me.shy.mybatis.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.shy.mybatis.domain.User;
import me.shy.mybatis.dto.PageInfo;
import me.shy.mybatis.service.UserService;
import me.shy.mybatis.service.impl.UserServiceImpl;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 */
@WebServlet("/page") public class ShowAllByPageServlet extends HttpServlet {
    UserService userService = new UserServiceImpl();

    @Override protected void service(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        // 获取请求参数
        String pageNumberString = req.getParameter("pageNumber");
        String pageSizeString = req.getParameter("pageSize");
        // 设置分页初始值
        int pageSize = 5;
        int pageNumber = 1;
        // 如果请求中带有参数，使用参数初始化
        if (null != pageNumberString && !pageNumberString.trim().equals("")) {
            pageNumber = Integer.parseInt(pageNumberString);
        }
        if (null != pageSizeString && !pageSizeString.trim().equals("")) {
            pageSize = Integer.parseInt(pageSizeString);
        }

        // 查询当前页面的应该展示的内容
        List<User> users = userService.showAllByPage(pageNumber, pageSize);
        // 计算总页数
        Long totalRecords = userService.count();
        Long totalPage = (totalRecords % pageSize == 0 ? totalRecords / pageSize : totalRecords / pageSize + 1);
        // 封装对象，传递给JSP
        PageInfo pageInfo = new PageInfo(totalPage, pageNumber, pageSize, users);
        // 设置传递
        req.setAttribute("pageInfo", pageInfo);
        // 转发至JSP
        req.getRequestDispatcher("/showAllByPage.jsp").forward(req, resp);
    }
}
