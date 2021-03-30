package me.shy.demo.netty.rpc.protobuf;

import com.google.protobuf.Any;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import javax.xml.crypto.Data;
import me.shy.demo.netty.rpc.protobuf.DataInfo.Student;
import me.shy.demo.netty.rpc.protobuf.DataInfo.Student.PhoneNumber;
import me.shy.demo.netty.rpc.protobuf.DataInfo.Student.PhoneNumber.PhoneType;
import me.shy.demo.netty.rpc.protobuf.DataInfo.Student.Sex;

/**
 * @Since: 2020/3/19 18:51
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: student.proto simple test.
 *
 **/
public class ProtoBufTest {
    public static void main(String[] args) {
        // 构建对象
        PhoneNumber phoneNumber = PhoneNumber.newBuilder()
            .setNumber("13800000000")
            .setPhoneType(PhoneType.MOBILE)
            .build();

        PhoneNumber telphoneNumber = PhoneNumber.newBuilder()
            .setNumber("028-12345678")
            .setPhoneType(PhoneType.HOME)
            .build();

        Any detail = Any.newBuilder()
            .setValue(ByteString.copyFromUtf8("Study very hard!"))
            .build();

        Student stu = Student.newBuilder()
            .setName("Tom").setAge(20)
            .setSex(Sex.FEMALE)
            .setAddress("Chengdu")
            .addPhoneNumber(0, phoneNumber)
            .addPhoneNumber(1, telphoneNumber)
            .putTags("fav", "basketboll")
            .addDetails(0, detail)
            .build();

        // 序列化成字节数组
        byte[] bytes = stu.toByteArray();

        try {
            // 反序列化成对象
            Student stu2 = Student.parseFrom(bytes);
            // toString 输出
            System.out.println(stu2.toString());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }
}
