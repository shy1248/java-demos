package me.shy.demo.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import me.shy.demo.service.UserService;
import me.shy.demo.util.MyBatisUtil;
import me.shy.demo.domain.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 */
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = Logger.getLogger(UserService.class);

    @Override public List<User> showAll() {
        // SqlSessionFactory factory =
        //     new SqlSessionFactoryBuilder().build(this.getClass().getResourceAsStream("/mybatis.xml"));
        // SqlSession session = factory.openSession();

        SqlSession session = MyBatisUtil.getSession();
        List<User> users = session.selectList("UserMapper.selectAll");
        LOGGER.info("Total users is: " + users.size());
        // session.close();
        return users;
    }

    @Override public List<User> showAllByPage(int pageNumber, int pageSize) {
        // SqlSessionFactory factory =
        //     new SqlSessionFactoryBuilder().build(this.getClass().getResourceAsStream("/mybatis.xml"));
        // SqlSession session = factory.openSession();

        SqlSession session = MyBatisUtil.getSession();
        Map<String, Integer> params = new HashMap<>();
        params.put("pageSize", pageSize);
        params.put("pageStart", pageSize * (pageNumber - 1));
        List<User> users = session.selectList("UserMapper.page", params);
        LOGGER.info("PageNumber: " + pageNumber + ", PageSize: " + pageSize + ", user number is: " + users.size());
        // session.close();
        return users;
    }

    @Override public Long count() {
        // SqlSessionFactory factory =
        //     new SqlSessionFactoryBuilder().build(this.getClass().getResourceAsStream("/mybatis.xml"));
        // SqlSession session = factory.openSession();

        SqlSession session = MyBatisUtil.getSession();
        Map<String, Integer> params = new HashMap<>();
        Long count = session.selectOne("UserMapper.count");
        LOGGER.info("Total user number is: " + count);
        // session.close();
        return count;
    }
}
