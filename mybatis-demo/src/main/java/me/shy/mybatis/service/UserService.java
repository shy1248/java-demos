package me.shy.mybatis.service;

import java.util.List;

import me.shy.mybatis.domain.User;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 */

public interface UserService {
    List<User> showAll();

    List<User> showAllByPage(int pageNumber, int pageSize);

    Long count();
}
