package me.shy.demo.util;

import java.io.IOException;
import java.io.InputStream;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: Mybatis工具类封装，使用ThreadLocal对象保存
 *
 * ThreadLocal是线程容器给线程绑定的一个Object对象，只要线程不发生改变，任何时刻都能获取到里边的对象。
 * 线程不同，一定不能相互获取。
 *
 * 由于客户端发起一次请求，到filter处理，再到servlet（控制器），再到service层，dao层都处于一个线程中，因此可以
 * 写一个全局的filter，在该filter放行前获取mybatis的session，在放行后进行session的事务提交或者回滚，由于tomcat
 * 中用来处理请求的线程数是一定的，因此这种方式可以控制整个程序中的session数，且能复用。这种思想最早由spring提出，
 * 被用到hibernate中。
 *
 */
public class MyBatisUtil {
    private static final Logger LOGGER = Logger.getLogger(MyBatisUtil.class);
    private static final ThreadLocal<SqlSession> THREAD_LOCAL = new ThreadLocal();
    private static SqlSessionFactory factory;

    static {
        try (InputStream inputStream = Resources.getResourceAsStream("mybatis.xml")) {
            factory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            LOGGER.error("Can not read the config file: mybatis.xml.\n" + e.getMessage() + e.getCause());
            System.exit(-1);
        }
    }

    public static SqlSession getSession() {
        SqlSession session = THREAD_LOCAL.get();
        if (null == session) {
            session = factory.openSession();
            THREAD_LOCAL.set(session);
        }
        return session;
    }

    public static void closeSession() {
        THREAD_LOCAL.get().close();
        // 很重要，防止下次获取到的session已经被关闭
        THREAD_LOCAL.set(null);
    }
}
