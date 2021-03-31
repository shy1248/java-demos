package me.shy.netty.netty.rpc.thrift;

import me.shy.netty.netty.rpc.thrift.gen.StudentService;
import me.shy.netty.netty.rpc.thrift.gen.StudentService.Processor;
import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.THsHaServer.Args;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;

/**
 * @Since: 2020/3/19 22:29
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: a demo application for java thrift server.
 *
 * Thrift 编程步骤：
 * 1.编写thrift接口文件；
 * 2.使用thrift编译器生成对应语言的代码：thrift --gen java student.thrift；
 * 3.实现thrift文件中定义的service，如StudentServiceImpl，该类需要实现thrift生成服务端的Iface接口；
 * 4.编写thrift服务器；
 * 5.编写thrift客户端，客户端通过一个Client对象来调用服务端的方法；
 *
 * 注意：客户端与服务端的protocol与transport必须对应
 *
 * Thrift示意架构图如下：
 *        +------------------------------------------------------------------------+
 *        |                                                                        |
 *        |                                                                        |
 *        |     +----------------+                          +----------------+     |
 *        |     | +------------+ |                          | +------------+ |     |
 *        |     | |  You Code  | | \                      / | |  You Code  | |     |
 *        |     | +------------+ |    \                /    | +------------+ |     |
 *        |     | | FooService.| |       \          /       | | FooService.| |     |
 *        |     | |   Client   | |         Generated        | | Processor  | |     |
 *        |     | +------------+ |           Code           | +------------+ |     |
 *        |     | |    Foo.    | |       /          \       | |    Foo.    | |     |
 *        |     | | read/write | |    /                \    | | write/read | |     |
 *        |     | +------------+ | /                      \ | +------------+ |     |
 *        |     | | TProtocol  | |                          | | TProtocol  | |     |
 *        |     | +------------+ |                          | +------------+ |     |
 *        |     | | TTransport | |                          | | TTransport | |     |
 *        |     | +------------+ |                          | +------------+ |     |
 *        |     | |    ....    | |                          | |    ....    | |     |
 *        |     | +------------+ |                          | +------------+ |     |
 *        |     | | Underlying | | -----------------------> | | Underlying | |     |
 *        |     | |    I/O     | | <----------------------- | |    I/O     | |     |
 *        |     | +------------+ |                          | +------------+ |     |
 *        |     +----------------+                          +----------------+     |
 *        |                                                                        |
 *        |                                                                        |
 *        +------------------------------------------------------------------------+
 *
 *
 * thrift服务器组件：
 * 1.TTransport / TServerSocket，指定了Thrift的数据传输方式，有以下几种：
 *   TServerSocket：阻塞式Socket
 *   TFramedTransport：以Frame为单位进行传输，非阻塞式服务中使用。
 *   TFileTransport：以文件形式传输。
 *   TMemoryTransport：将内存用于I/O，Java实现时内部使用了简单的ByteArrayOutputStream。
 *   TZlibTransport：使用Zlib进行压缩，与其它传输方式联合使用，Java无实现
 *
 * 2.Tprotocol，指定了Thrift的数据传输格式，有以下几种实现类：
 *   TBinaryProTocol：二进制格式。
 *   TCompactProcotol：二进制压缩格式，使用较多，数据量最小。
 *   TJSONProtocol：JSON格式，文本格式，数据较大。
 *   TSimpleJSONProtocol：提供JSON的只写协议，缺少元数据信息，不可读取，生成的文件很容易通过脚本语言解析。极少使用。
 *   TDebugProtocol：使用容易读懂的文本格式，用于debug。
 *
 * 3.TServer，指定了Thrift的服务模型，具体有：
 *   TSimpleServer：简单的单线程服务模型，常用于测试。
 *   TThreadPoolServer：多线程服务模型，使用了阻塞式的IO。
 *   TNonblockingServer：多线程服务模型，使用了非阻塞式的IO，需要使用TFramedTransport的数据传输方式。
 *   THsHaServer：是TNonblockingServer的一种拓展，多线程的，半同步半异步的服务模型，其中IO（accpect/read/write）
 *                操作使用异步，而rpc的handler使用同步方式，需要使用TFramedTransport的数据传输方式。
 *
 *
 **/
public class DemoThriftServer {
    public static void main(String[] args) {
        TNonblockingServerSocket socket = null;
        TServer server = null;
        try {
            // 创建transport层
            socket = new TNonblockingServerSocket(9999);
            // 绑定处理业务逻辑的实现类
            StudentService.Processor<StudentServiceImpl> processor = new Processor<>(new StudentServiceImpl());
            // 构建服务器参数
            THsHaServer.Args serverArgs = new Args(socket);
            serverArgs.minWorkerThreads(1);
            serverArgs.maxWorkerThreads(2);
            serverArgs.protocolFactory(new TCompactProtocol.Factory());
            serverArgs.transportFactory(new TFramedTransport.Factory());
            serverArgs.processorFactory(new TProcessorFactory(processor));
            // 创建服务器
            server = new THsHaServer(serverArgs);
            // 启动服务器
            System.out.println("Server startup to listen 9999 ...");
            server.serve();
        } catch (TTransportException e) {
            e.printStackTrace();
        }
    }
}
