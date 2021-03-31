package me.shy.netty.netty.rpc.thrift;

import me.shy.netty.netty.rpc.thrift.gen.Sex;
import me.shy.netty.netty.rpc.thrift.gen.Student;
import me.shy.netty.netty.rpc.thrift.gen.StudentService.Client;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

/**
 * @Since: 2020/3/19 22:54
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: a demo application for java thrift client.
 *
 **/
public class DemoThriftClient {
    public static void main(String[] args) {
        TTransport transport = new TFramedTransport(new TSocket("localhost", 9999, 600));
        TProtocol protocol = new TCompactProtocol(transport);
        Client client = new Client(protocol);

        try {
            transport.open();
            Student jerry = client.getStudentByName("Jerry");
            System.out.println("Call respone: " + jerry + " with get API.");

            Student tom = new Student();
            tom.setId(2L);
            tom.setName("Tom");
            tom.setAge(18);
            tom.setGender(Sex.MALE);
            client.save(tom);

        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException e){
            e.printStackTrace();
        }
    }
}
