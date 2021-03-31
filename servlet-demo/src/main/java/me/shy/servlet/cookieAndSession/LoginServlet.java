package me.shy.servlet.cookieAndSession;

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
 * @Description:
 *
 * forward：请求转发，属于同一次请求，共享一个request对象，因此可用request对象的setArribtue和getArrtibute进行数据流转。
 *          可能造成表单重复提交的问题。浏览器地址栏URL不会发生改变。
 * redirect：请求重定向，属于不同的两次请求，request对象不同。浏览器地址栏URL发生改变。
 *
 * 由于http请求是无状态的，因此使用redircet重定向后，后一次请求不能直接获取前一次请求的处理的结果。此时可以使用cookie
 * 或者session解决。cookie和session是为了解决不同请求数据共享的问题。
 *
 * 1.cookie
 *
 *  cookie保存的是一组键值对。
 *  cookie数据声明在服务器端，但存储在浏览器端。
 *  临时的cookie信息存储在浏览器的运行内存中，浏览器关闭后cookie数据就会丢失。可以通过cookie对象的setMaxAge()方法设置
 *  cookie的有效期，此时cookie信息会被浏览器存储在内存中，在有效期内符合请求路径要求的请求都会带上该cookie信息。
 *
 *  同时，默认情况下，每次请求都会带上cookie，除非通过cookie对象的setPath(uri)方法设置请求路径，此时只会在设置的路径的请求
 *  中携带cookie信息。
 *
 *  cookie的创建和存储：
 *  // 创建cookie
 *  Cookie c = new Cookie(key, value)
 *  // 设置cookie的有效期
 *  c.setMaxAge(3*24*60*60)
 *  // 设置cookie的有效路径
 *  c.setPath("/servlet-demo/main")
 *  // 响应cookie
 *  resp.setCookie(c)
 *
 *  cookie的读取：
 *
 *  Cookie[] cs = req.getCookies()
 *  if(null != cs){
 *      System.out.println("No cookies!");
 *  } else {
 *       for(Cookie c: cs){
 *           String name = c.getName();
 *           String value = c.getValue();
 *           System.out.println("Cookie: name=" + name + ", value=" + value + ".");
 *       }
 *  }
 *
 *
 * 2.session
 *   用户第一次访问服务器，服务器为该用户创建一个session对象，并将该session对象的JSESSIONID使用
 *   cookie方式存储到浏览器，从而保证同一用户的其它请求能够获取到同一session对象，也保证了不同请求
 *   能获取到共享的数据。
 *
 *   session由服务器创建，并存储在服务端，但是依赖cookie技术。作用范围为一次会话（只要JSESSIONID和session对象不失效均有效）。
 *   tomcat的默认存储时间为30分钟，可在tomcat配置文件web.xml或者项目的web.xml中配置如下修改：
 *      <session-config>
 *          <session-timeout>30</session-timeout>
 *      </session-config>
 *
 *
 *   session使用：
 *   session一般由Web容器如tomcat创建，使用时只需要由下面的代码：
 *   HttpSession session = req.getSession();
 *   该代码的意思是：
 *      如果cookie中携带JSESSIONID，则直接根据该id从容器中获取session对象；
 *      如果cookie中没有携带JSESSIONID，则直接为当前用户创建一个新的session对象，并将JSESSIONID存储到cookie中。
 *      JSESSIONID作为临时cookie存储在浏览器端，浏览器关闭即失效。
 *      如果session对象失效了，同样会直接为当前用户创建一个新的session对象，并将JSESSIONID存储到cookie中。
 *
 *    通过session对象的setMaxInactiveInterval(seconds)方法可以设置session对象的存储有效期。
 *    注意，在该有效期内，如果session对象没有被使用，则销毁session对象，如果在有效期内使用了，则有效期时间被重制为该值。
 *
 *    使用session对象的invalidate()方法可以使session对象强制失效。
 *
 *    通过session对象的setAttribute(key, value)可以给session对象存储数据，下一次请求获取到相同的session对象后通过
 *    getAttribute(key)即可获得这个数据，因此可实现多次请求间的数据共享。但是要保证session不失效，并且存储要先于取出。
 *
 *    使用时机：一般在Web登陆成功后会将该用户的个人信息（如User对象）保存在session对象中，供该用户的其它请求使用。
 *
 *    session失效处理办法：
 *    1.可以将用户请求中的JSESSIONID与后台获取到的session对象的JSESSIONID（通过session对象的getId()方法获取）进行比对。
 *    如果一样表示没有失效，不一样就是失效了；
 *    2.或者直接获取session对象中存储的值，如果为null就表示失效了，否则就是没有失效；
 *    3.在关键业务逻辑处做处理即可；
 *    4.如果失效了，一般是直接重定向到登录页面让用户重新登录。
 *
 *
 *
 */
public class LoginServlet extends HttpServlet {

    private static final String VALID_USER = "demo";
    private static final String VALID_PASS = "123456";

    @Override protected void service(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");

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
            // 添加cookie信息
            // 此处需要注意对敏感信息加密
            Cookie c = new Cookie("cname", VALID_USER);
            // 设置cookie有效期为3天，为了测试方便，此处设为30秒
            // c.setMaxAge(3 * 24 * 60 * 60);
            c.setMaxAge(30);
            // 设置cookie的有效路径
            c.setPath("start");
            // 在响应行中增加cookie信息
            resp.addCookie(c);
            // 创建session
            HttpSession session = req.getSession();
            // 设置session的有效期为1分钟，便于测试
            session.setMaxInactiveInterval(60);
            // 在session中存储用户信息
            session.setAttribute("user", VALID_USER);
            resp.sendRedirect("main");
        } else {
            System.out.println("Login failed, forward!");
            req.setAttribute("isSuccessed", false);
            req.setAttribute("errorInfo", respMsg);
            req.getRequestDispatcher("index").forward(req, resp);
        }
    }
}
