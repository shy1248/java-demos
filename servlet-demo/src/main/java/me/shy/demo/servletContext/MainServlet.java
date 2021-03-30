package me.shy.demo.servletContext;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 */
public class MainServlet extends HttpServlet {

    @Override protected void service(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        HttpSession session = req.getSession();
        String user = (String)session.getAttribute("user");
        if (null == user) {
            System.out.println("Session is exprised, required login again.");
            resp.sendRedirect("start");
            return;
        }
        // 获取PageView
        ServletContext context = this.getServletContext();
        String key = context.getInitParameter("pageViewKey");
        Long pageView = (Long)context.getAttribute(key);
        pageView++;
        context.setAttribute(key, pageView);
        PrintWriter respWriter = resp.getWriter();
        respWriter.write("<html>");
        respWriter.write("<header>");
        respWriter.write("<title>Login - Servlet Demo!</title>");
        respWriter.write("</header>");
        respWriter.write("<body>");
        respWriter.write("<h1>Welcome to login this system, this is a demo application for servlet.</h3>");
        respWriter.write("<div><span>Total PageView is: " + pageView + "</span></div>");
        respWriter.write("<hr>");
        respWriter.write("<div><span>Hi, " + user + ". You login successful, enjoy!</span></div>");
        respWriter.write("</body>");
        respWriter.write("</html>");
    }
}
