package me.shy.servlet.listener;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionIdListener;
import javax.servlet.http.HttpSessionListener;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: 监听器
 *
 * Servlet的监听器是Servlet规范中定义的一种特殊的类，用于监听ServletContext，HttpSession和ServletRequest等域
 * 对象的创建与销毁事件，以及监听这些域中属性发生改变的事件。
 *
 * 由于这些作用域的对象的创建和销毁都是由服务器进行，我们无法感知，只能使用这种监听机制实现。
 *
 * 使用：通过实现一序列的Listener接口来实现。并且在web.xml进行如下配置：
 *
 *     <listener>
 *         <listener-class>DemoListener</listener-class>
 *     </listener>
 *
 * 使用场景：
 * 1.网站当前在线人数，可以通过监听Session创建事件代表用户上线，Session销毁代表用户下线。
 * 2.统计网页浏览次数，通过监听
 *
 */
public class DemoListener
    implements ServletRequestListener, HttpSessionListener, ServletContextListener, ServletRequestAttributeListener,
    HttpSessionAttributeListener, HttpSessionActivationListener, HttpSessionIdListener, HttpSessionBindingListener,
    ServletContextAttributeListener {

    @Override public void requestDestroyed(ServletRequestEvent sre) {
        ServletRequest request = sre.getServletRequest();
        System.out.println(request.getServletContext().getContextPath() + "has been destoried");
    }

    @Override public void requestInitialized(ServletRequestEvent sre) {
        ServletRequest request = sre.getServletRequest();
        System.out.println(request.getServletContext().getContextPath() + "has been initilized");
    }

    @Override public void attributeAdded(ServletContextAttributeEvent scae) {
        String name = scae.getName();
        System.out.println("ServletContext: " + name + "has been added, the value is: " + scae.getValue().toString());
    }

    @Override public void attributeRemoved(ServletContextAttributeEvent scae) {
        String name = scae.getName();
        System.out.println("ServletContext: " + name + "has been removed, the value is: " + scae.getValue().toString());
    }

    @Override public void attributeReplaced(ServletContextAttributeEvent scae) {
        String name = scae.getName();
        System.out
            .println("ServletContext: " + name + "has been replaced, the value is: " + scae.getValue().toString());
    }

    @Override public void contextInitialized(ServletContextEvent sce) {
        System.out.println("ServletContext has been initilized");
    }

    @Override public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("ServletContext has been desctoried");
    }

    @Override public void attributeAdded(ServletRequestAttributeEvent srae) {
        String name = srae.getName();
        System.out.println("ServletRequest: " + name + "has been added, the value is: " + srae.getValue().toString());
    }

    @Override public void attributeRemoved(ServletRequestAttributeEvent srae) {
        String name = srae.getName();
        System.out.println("ServletRequest: " + name + "has been removed, the value is: " + srae.getValue().toString());
    }

    @Override public void attributeReplaced(ServletRequestAttributeEvent srae) {
        String name = srae.getName();
        System.out
            .println("ServletRequest: " + name + "has been replaced, the value is: " + srae.getValue().toString());
    }

    @Override public void attributeAdded(HttpSessionBindingEvent se) {
        String name = se.getName();
        System.out.println("HttpSession: " + name + "has been added, the value is: " + se.getValue().toString());
    }

    @Override public void attributeRemoved(HttpSessionBindingEvent se) {
        String name = se.getName();
        System.out.println("HttpSession: " + name + "has been removed, the value is: " + se.getValue().toString());
    }

    @Override public void attributeReplaced(HttpSessionBindingEvent se) {
        String name = se.getName();
        System.out.println("HttpSession: " + name + "has been replaced, the value is: " + se.getValue().toString());
    }

    @Override public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        System.out.println("Session: " + session.getId() + "has been created");
    }

    @Override public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        System.out.println("Session: " + session.getId() + "has been destroied");
    }

    @Override public void sessionIdChanged(HttpSessionEvent httpSessionEvent, String s) {
        HttpSession session = httpSessionEvent.getSession();
        System.out.println("HttpSession: " + session.getId() + " is changed to: " + s);
    }

    @Override public void sessionWillPassivate(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        System.out.println("HttpSession: " + session.getId() + " will be passivate");
    }

    @Override public void sessionDidActivate(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        System.out.println("HttpSession: " + session.getId() + " did activate");
    }

    @Override public void valueBound(HttpSessionBindingEvent event) {
        HttpSession session = event.getSession();
        System.out.println("HttpSession: " + session.getId() + " value bound");
    }

    @Override public void valueUnbound(HttpSessionBindingEvent event) {
        HttpSession session = event.getSession();
        System.out.println("HttpSession: " + session.getId() + " value unbound");
    }
}
