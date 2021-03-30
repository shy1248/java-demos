package me.shy.demo.mapper;

import java.util.List;
import me.shy.demo.domain.User;
import org.apache.ibatis.annotations.Param;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 */
public interface UserMapper {
    List<User> selectAll();

    Long count();

    User selectById(int id);

    List<User> getGrownUps();

    List<User> page(@Param("pageStart") int start, @Param("pageNumber") int current);

    int insert(User user);
}
