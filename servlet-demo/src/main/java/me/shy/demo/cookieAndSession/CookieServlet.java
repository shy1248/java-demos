package me.shy.demo.cookieAndSession;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: 通过cookie实现三天免登录
 *
 * 流程：
 * 1.获取cookies信息；
 * 2.如果不存在任何cookies信息，表示为第一次登录或者浏览器cookies被删除，需要重新登录，重定向到登陆页面；
 * 3.如何cookies存在，则尝试获取指定的cookie，如果没获取到，则需要重新登录，重定向到登陆页面；
 * 4.如果获取到指定cookie信息，则进行校验，校验没通过，则需要重新登录，重定向到登陆页面；
 * 5.如果校验通过，则直接重定向到主页面；
 * 6.对于2/3/4的情况，重定向到登录页面，在登陆成功时设置cookie。
 *
 */
public class CookieServlet extends HttpServlet {

    private static final String VALID_USER = "demo";

    @Override protected void service(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");

        // 获取cookie信息
        Cookie[] cookies = req.getCookies();
        // 如果cookie信息为空，表示第一次登录或者浏览器数据被清空
        if (null == cookies) {
            req.getRequestDispatcher("index").forward(req, resp);
            return;
        }
        // 尝试获取cname的cookie
        String cname = "";
        for (Cookie c : cookies) {
            if ("cname".equals(c.getName())) {
                cname = c.getValue();
            }
        }

        // 没有获取到cname的cookie，需要重新登录
        if ("".equals(cname)) {
            req.getRequestDispatcher("index").forward(req, resp);
            return;
        }
        // 获取到cname了，不能直接重定向到主页面
        // 还需要去数据库校验用户是否存在，否则有可能用户登陆后再注销，然后又在三天内登录就直接成功了
        // 其实该用户是不存在的
        if (!VALID_USER.equals(cname)) {
            req.getRequestDispatcher("index").forward(req, resp);
            return;
        }
        // 校验通过，直接重定向到主页面
        // 创建session
        HttpSession session = req.getSession();
        // 设置session的有效期为1分钟，便于测试
        session.setMaxInactiveInterval(60);
        // 在session中存储用户信息
        session.setAttribute("user", VALID_USER);
        resp.sendRedirect("main");
    }
}
