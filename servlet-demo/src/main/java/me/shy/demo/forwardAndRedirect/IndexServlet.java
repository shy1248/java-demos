package me.shy.demo.forwardAndRedirect;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
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
public class IndexServlet extends HttpServlet {

    @Override protected void service(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        System.out.println("Handing Login request!");
        boolean isForward = req.getAttribute("isSuccessed") == null ? false : true;
        String errorInfo = "";
        if (isForward) {
            errorInfo = (String)req.getAttribute("errorInfo");
        }
        PrintWriter respWriter = resp.getWriter();
        respWriter.write("<html>");
        respWriter.write("<header>");
        respWriter.write("<title>Login - Servlet Demo!</title>");
        respWriter.write("</header>");
        respWriter.write("<body>");
        respWriter.write("<h3>Welcome to login this system, this is a demo application for servlet.</h3>");
        respWriter.write("<hr>");
        respWriter.write("<form action='login' method='post'>");
        respWriter.write(
            "<div>Username: <input type='text' name='username' value='' /><span><font " + "color='#FF0000'>" + errorInfo
                + "</font></span></div>");
        respWriter.write("<div>Password: <input type='password' name='password' value='' /></div>");
        respWriter.write("<div><input type='submit' value='Login' /></div>");
        respWriter.write("</form>");
        respWriter.write("</body>");
        respWriter.write("</html>");
    }
}
