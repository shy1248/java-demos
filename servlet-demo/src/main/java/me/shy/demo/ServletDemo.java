package me.shy.demo;

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
 * @Description:
 *
 * 狭义的Servlet是指Java语言定义的一个接口，广义的Servlet是指任何实现了这个 接口的Java类。一般指的是后者。
 *
 * Servlet 程序开发流程：
 * 新建JavaEE项目，编写Java类，需要继承自HttpServlet，并复写service、doPost、doGet等方法；
 * 修改web.xml，添加如下配置：
 *      <servlet>
 *      <-- 定义Servlet全类名 !-->
 *      <servlet-class>ServletDemo</servlet-class>
 *      <-- 定义Servlet名字 !-->
 *      <servlet-name>demo</servlet-name>
 *      <--此配置为1，表示在服务器启动时即加载到内存 !-->
 *      <load-on-startup>1</load-on-startup>
 *      </servlet>
 *      <servlet-mapping>
 *          <-- 通过Servlet名字指定为哪个Servlet进行URL映射 !-->
 *          <servlet-name>demo</servlet-name>
 *          <-- 定义Servlet的URL路由映射 !-->
 *          <url-pattern>/demo</url-pattern>
 *      </servlet-mapping>
 *
 * web.xml文件在服务器加载时加载到内存。Servlet生命周期为：
 * 1.当没有在web.xml文件中配置load-on-startup时，Servlet的生命周期为第一次访问到服务器关闭；
 * 2.当在web.xml文件中配置load-on-startup的值为非负数时，Servlet的生命周期为服务器启动到服
 *   务器关闭，且按照load-on-startup的数值从小到大的优先级进行加载；为0时优先级最高。
 *
 * service方法可以同时处理get和post请求；
 * doGet方法只能处理get方式请求，同理doPost只能处理post方式的请求；
 * 同时只要有service方法，都会优先使用service方法处理；
 * 但是如果service方法中有调用父类的service方法，父类的service方法会根据请求类型去调用当前类的doGet或者doPost方法，
 * 因此，如果只提供了service方法，同时在service方法中调用了父类的service方法，会报405。
 *
 * HttpServletRequest对象由容器服务器（如tomcat）创建，作为实参传递给service、doGet、doPost等方法。
 * 该对象封装了HTTP协议的请求头、请求行以及请求数据等信息。
 *
 * Servlet一般开发流程：
 * 1.设置请求编码格式；
 * 2.设置响应编码格式：
 * 3.获取请求信息；
 * 4.处理请求信息；
 * 5.响应请求。
 *      直接响应，resp.getWriter().write("response")
 *      请求转发，forward
 *      请求重定向，redirect
 *
 */
public class ServletDemo extends HttpServlet {

    /**
     * 销毁方法，Servlet被销毁时执行一次
     */
    @Override public void destroy() {
        System.out.println("ServletDemo is destroing...");
    }

    /**
     * 初始化方法，只会在Servlet加载到内存时执行一次
     *
     * @throws ServletException
     */
    @Override public void init() throws ServletException {
        System.out.println("ServletDemo is initlizing ...");
    }

    @Override protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        writer.write("This is demo message responsed from ServeletDemo Application, handle by doPost method" + ".");
        System.out.println(
            "Response: This is demo message responsed from ServeletDemo Application, handle by doPost method" + ".");

        // 一些常用方法
        // String method = req.getMethod();
        // String url = req.getRequestURL().toString();
        // String uri = req.getRequestURI();
        // String scheme = req.getScheme();
        // String value = req.getHeader("key");
        // Enumeration<String> headerNames = req.getHeaderNames();
        // while (headerNames.hasMoreElements()) {
        //     String headerName = headerNames.nextElement();
        //     String headerValue = req.getHeader(headerName);
        // }
        // Enumeration<String> parameterNames = req.getParameterNames();
        // ServletInputStream inputStream = req.getInputStream();

    }

    @Override protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        resp.getWriter()
            .write("This is demo message responsed from ServeletDemo Application, handle by doGet method" + ".");
        System.out
            .println("Response: This is demo message responsed from ServeletDemo Application, handle by doGet method.");
    }

    /**
     * 对业务请求提供服务的方法
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override protected void service(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        resp.getWriter()
            .write("This is demo message responsed from ServeletDemo Application, handle by service method" + ".");
        System.out.println(
            "Response: This is demo message responsed from ServeletDemo Application, handle by service method" + ".");
        // super.service(req, resp);
    }
}
