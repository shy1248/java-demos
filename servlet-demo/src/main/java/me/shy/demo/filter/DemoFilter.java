package me.shy.demo.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: 过滤器的使用
 *
 * 过滤器主要要来给服务器接受到的请求资源和响应给浏览器的资源进行管理，以保护servlet。
 *
 * 通过创建一个普通的Java类并实现 javax.servlet.Filter 接口，并实现其三个方法：
 *     init：服务器启动时执行，只执行一次，过滤器加载优先于Servlet；
 *     doFilter：对于匹配到URL的请求每次都执行，需要手动对请求进行放行，否则会造成请求被阻塞；
 *     destroy：服务器关闭时执行，只执行一次，晚于Servlet销毁。
 *
 * Filter由程序员声明，服务器自行调用。
 * 代码编写完毕后还需要在web.xml中配置过滤器。如下：
 *     <filter>
 *         <filter-class>DemoFilter</filter-class>
 *         <filter-name>demoFilter</filter-name>
 *     </filter>
 *     <filter-mapping>
 *         <filter-name>demoFilter</filter-name>
 *         <url-pattern>/*</url-pattern>
 *     </filter-mapping>
 * 其中：
 * <url-pattern>/*</url-pattern>：/*表示拦截所有请求；
 * <url-pattern>*.do</url-pattern>：*.do，拦截URL以.do结尾的请求，一般用于模块拦截；
 * <url-pattern>/demo/start</url-pattern>：拦截具体的URL，专门拦截某个Servlet。
 * url-pattern匹配范围越小，越是接近Servlet时才被执行，换句话说，范围越大，越早进入，但是最晚离开。
 *
 * 执行机制：
 * 浏览器发送URL到服务器，如有匹配的过滤器，先进入过滤器的doFilter，当请求被放行后，会进入下一个符合要求的过滤器的
 * doFilter方法中，如此循环，直到没有过滤器到达Servlet的servce等方法进行具体的业务逻辑。Servlet的业务处理完毕后，
 * 就会返回到最内层的过滤中，然后依次退出Filter，直到请求响应被发出。
 *
 * 使用案例：
 * 1.设置同一的请求响应编码格式；
 * 2.Session管理，判断Session是否失效；
 * 3.权限管理；
 * 4.资源管理，比如为响应的图片加上水印等。
 *
 */

public class DemoFilter implements Filter {

    @Override public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("DemoFilter initilized!");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException {
        // 请求到达Servlet前的处理
        System.out.println("DemoFilter is begin do work.");
        // 设置请求编码格式
        servletRequest.setCharacterEncoding("utf-8");
        // Session过期检查，如果已过期，重定向到登录页面
        HttpSession session = ((HttpServletRequest)servletRequest).getSession();
        if (session.getAttribute("user") == null) {
            ((HttpServletResponse)servletResponse).sendRedirect("start");
        }
        // 注意，对于符合条件的请求，必须予以放行
        filterChain.doFilter(servletRequest, servletResponse);
        // Servlet响应完后的处理
        // 设置
        servletResponse.setCharacterEncoding("utf-8");
        servletResponse.setContentType("text/html;charset=utf-8");
        System.out.println("DemoFilter is end for work.");
    }

    @Override public void destroy() {
        System.out.println("DemoFilter destroied!");
    }
}
