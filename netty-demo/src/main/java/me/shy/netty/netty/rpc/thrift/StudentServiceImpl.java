package me.shy.netty.netty.rpc.thrift;

import java.util.Random;
import me.shy.netty.netty.rpc.thrift.gen.DataException;
import me.shy.netty.netty.rpc.thrift.gen.Sex;
import me.shy.netty.netty.rpc.thrift.gen.Student;
import me.shy.netty.netty.rpc.thrift.gen.StudentService.Iface;
import org.apache.thrift.TException;

/**
 * @Since: 2020/3/19 22:31
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 *
 **/
public class StudentServiceImpl implements Iface {
    @Override public Student getStudentByName(String name) throws DataException, TException {
        System.out.println("Called get API with param: " + name);
        Student student = new Student();
        student.setId(1L);
        student.setName(name);
        student.setAge(25);
        student.setGender(Sex.FEMALE);
        return student;
    }

    @Override public void save(Student student) throws DataException, TException {
        System.out.println("Called save API with param: " + student.toString());
        System.out.println(student.toString() + " is svaed!");
    }
}
