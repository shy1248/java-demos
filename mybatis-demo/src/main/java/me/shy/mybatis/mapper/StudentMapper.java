package me.shy.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import me.shy.mybatis.domain.Student;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: 注解的使用
 *
 * 使用注解方式需要使用，或者使用package
 * 只要不是动态SQL，都可以使用注解方式实现，注解方式和xml方式可以共存。
 * 注解使用方式是：建立接口类，定义方法，然后在方法上使用@Select，@Insert，@Update，@Delete等
 * 注解，这些注解的参数均为原来mapper.xml中的sql。
 * @Results注解可以实现resultMap的方式，只是写起来比较不方便，需要注意数组中的非基本类型需要再套注解。
 * 需要在mybatis.xml中的mappers增加package或者mapper标签，其中mapper标签的属性要用class指定：
 *
 * <mapper class="me.shy.demo.mapper.*"></mapper>
 */
public interface StudentMapper {

    // 删除
    @Delete("delete from student where id=#{id}") int delete(Student student);

    // 查询
    @Select("select * from student") List<Student> selectAll();

    // 插入
    @Insert("insert into student values(default, #{name}, #{age})") int insert(Student student);

    // 更新
    @Update("update student set name=#{name}, age=#{age} where id=#{id}") int update(Student student);

    // 查询
    @Select("select * from student where tid=#{0}") List<Student> setByTid(int tid);

    // 多表联合查询
    @Select("select s.id id, s.name name, s.age age, s.tid tid, t.id `teacher.id`, t.name `teacher.name` from student"
        + " s left join teacher t on s.tid=t.id") List<Student> selectAllWithTeacher();
}
