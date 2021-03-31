package me.shy.mybatis;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import me.shy.mybatis.domain.User;
import me.shy.mybatis.util.MyBatisUtil;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: mybatis
 * <p>
 * mybatis的使用步骤：
 * 1.导入mybatis的jar包；
 * 2.编写mybatis的配置文件，如mybatis.xml文件；
 * 3.编写mybatis的mapper映射文件，如UserMapper.xml；
 * 4.单独使用mybatis时，需要创建session，如下代码所示。
 */
public class MyBatisDemoTestMain {

    public static void main(String[] args) throws IOException {
        // // 读取mybatis的配置文件，里边包含数据库的连接信息
        // // InputStream in = TestMain.class.getResourceAsStream("/mybatis.xml");
        // InputStream in = Resources.getResourceAsStream("mybatis.xml");
        // System.out.println(in);
        // // 使用配置文件构建session工厂
        // SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        // // 使用session工厂创建session
        // SqlSession session = factory.openSession();

        SqlSession session = MyBatisUtil.getSession();
        // 开启自动提交事务的session
        // SqlSession session = factory.openSession(true);
        // 使用session查询。参数为Mapper.xml中配置的（namespace.id），返回值可以强转为resultType定义的类型。

        // mybatis的缓存机制：
        // session默认是有带statment对象缓存的，当2次调用同一个查询方法时，实际上只会查询一次，如果是2次调用不同的
        // 查询方法，哪怕SQL一样，也不会调用缓存，因为statement对象不同。缓存的有效范围是同一个SQLSession对象。
        // 该缓存为一级缓存。SqlSessionFactory为二级缓存，同一个SqlSessionFactory获取的SqlSession都可以使用。
        // 当数据频繁读取，很少修改时使用。开启方式为：在mapper.xml文件中增加<cache readOnly="true"></cache>，
        // 其中readOnly="true"如果不写，就要让mapper.xml的实体类实现序列化接口。
        // 当SqlSession对象commit或者close时会把一级缓存的内容刷入二级缓存中。

        // 返回List存储的多条结果
        List<User> users = session.selectList("UserMapper.selectAll");
        for (User user : users) {
            System.out.println(user);
        }

        // 返回Map存储的多条结果，mapKey参数表示要用那一个列来作为Map的key，这个列是什么类型，返回Map的第一个泛型就是什么类型
        // 返回Map的第二个泛型就还是mapper.xml中定义的resultType的类型
        Map<String, User> names = session.selectMap("UserMapper.selectAll", "name");
        System.out.println(names.get("Jerry"));

        // 返回单条结果集
        Long count = session.selectOne("UserMapper.count");
        System.out.println("Total record is: " + count);
        User user = session.selectOne("UserMapper.selectById", 2);
        System.out.println("The user which id=2 is: " + user);

        // 返回List存储的多条结果
        List<User> grownUps = session.selectList("UserMapper.getGrownUps");
        for (User grownUp : grownUps) {
            System.out.println("GrownUp: " + grownUp);
        }

        // 分页
        int pageSize = 2;
        int pageNumber = 1;
        Map<String, Integer> params = new HashMap<>();
        params.put("pageSize", pageSize);
        params.put("pageStart", pageSize * (pageNumber - 1));
        // 返回List存储的多条结果
        List<User> top2 = session.selectList("UserMapper.page", params);
        for (User u : top2) {
            System.out.println("Top2: " + u);
        }

        // 插入操作
        User tony = new User();
        tony.setName("Tony");
        tony.setAge(17);
        session.insert("UserMapper.insert", tony);
        System.out.println("Tony has been added!");

        // 提交事务
        // mybatis中默认对JDBC的事务都是不自动提交的
        // 除了用session.commit()来提交事务，也可以使用factory.openSession(true)来开启自动提交
        // 一般建议手动提交事务，出错时使用session.rollback()
        session.commit();
        // 关闭session
        session.close();
    }
}
