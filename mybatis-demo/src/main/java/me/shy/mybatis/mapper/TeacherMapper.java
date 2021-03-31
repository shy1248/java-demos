package me.shy.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import me.shy.mybatis.domain.Teacher;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 */
public interface TeacherMapper {

    // 使用注解方式实现resultMap的collection模式
    @Results(value = {@Result(id = true, column = "id", property = "id"), @Result(column = "name", property = "name"),
        @Result(column = "id", property = "students", many = @Many(select = "StudentMapper.selectByTid"))})
    @Select("select * from teacher") List<Teacher> selectAllWithStudent();
}
