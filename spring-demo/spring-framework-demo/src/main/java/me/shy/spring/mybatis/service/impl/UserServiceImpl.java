package me.shy.spring.mybatis.service.impl;

import java.util.List;
import me.shy.spring.mybatis.mapper.UserMapper;
import me.shy.spring.mybatis.pojo.User;
import me.shy.spring.mybatis.service.UserService;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 */
public class UserServiceImpl implements UserService {
    private UserMapper userMapper;

    public UserMapper getUserMapper() {
        return userMapper;
    }

    // 生成seter方法，以便spring框架通过属性注入
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public List<User> getAll() {
        return userMapper.selectAll();
    }

    @Override
    public int addUser(User user) {
        return userMapper.insert(user);
    }
}
