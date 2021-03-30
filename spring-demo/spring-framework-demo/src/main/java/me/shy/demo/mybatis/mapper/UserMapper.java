package me.shy.demo.mybatis.mapper;

import java.util.List;
import me.shy.demo.mybatis.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 */
public interface UserMapper {

    @Select("select * from user")
    List<User> selectAll();

    @Insert("insert into user values(default, #{name}, #{age})")
    int insert(User user);
}
