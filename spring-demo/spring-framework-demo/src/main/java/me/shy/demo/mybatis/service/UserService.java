package me.shy.demo.mybatis.service;

import java.util.List;
import me.shy.demo.mybatis.pojo.User;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 */
public interface UserService {

    List<User> getAll();

    int addUser(User user);
}
