package me.shy.netty.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @Since: 2020/3/29 16:44
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: Netty - ByteBuf
 *
 * 1.Netty的ByteBuf对象采用读写索引分离的策略，即一个ByteBuf对象同时拥有一个读索引（readIndex）与写索引（writeIndex）。
 *   当一个ByteBuf对象初始化时，ByteBuf对象的读和写索引均为0；
 * 2.当读写索引处于同一位置时，我们继续读取，就会抛出ArrayOutOfBoundException；
 * 3.对于ByteBuf对象的任何读或者写操作，都只会影响对应得索引，maxCapacity的最大值默认就是Integer.MAX_VALUE；
 * 4.ByteBuf对象会在写入时会判断长度，不够的话自动扩容，类似于ArrayList；
 * 5.相比较而言，JDK的Buffer对象，底层数组在声明时被定义为final的，无法被修改为其它数组的引用，在使用时需要提前考虑好buffer的长度，
 *   在容量不足时，只能由开发者手动重新创建新的Buffer来解决；同时只有一个位置索引，读写转换时需要手动反转（flip或者rewind）；
 *
 * 创建方式：
 * Unpooled.buffer()
 * Unpooled.copiedBuffer()
 *
 * 常用方法：
 * readIndex()
 * wrtieIndex()
 * capacity()
 * readIndex(int)
 * writeIndex(int)
 * reabableBytes()
 * wrtieableBytes()
 * readByte()
 * readInt()
 * readBytes()
 * writeBytes()
 * writeByte()
 * ...
 * clear()
 * dsicardBytes()
 *
 * Netty Bytebuf提供的3种缓冲区的类型：
 * 1）heap buffer：堆内缓冲区，这是最常用的类型，将数据存储到JVM堆空间种，实际数据存放到ByteArray中，这个数组成为back array。
 *      优点是，数据存放在堆中，数据可以快速创建和与释放，并提供了直接访问内部数组的方法；
 *      缺点是每次进行网络IO时需要将堆内存复制到系统直接内存中。
 * 2）direct buffer：堆外缓冲区，不会占用堆内存，由操作系统在本地内存分配，优缺点与Heap buffer刚好相反，无需内存拷贝。
 *      Netty通过提供内存池来解决堆外缓冲区创建与释放慢的问题。堆外缓冲不能通过直接访问底层字节数组的方式访问数据。对于业务消息的
 *      编解码来说，推荐使用Heap buffer，而对于IO事件的缓冲，则推荐使用Direct buffer。
 * 3）composite buffer：复合缓冲区，类似缓冲区容器，其中可存放堆内或者堆外缓冲区
 *      CompositeBytebuf compBuffer = Unpooled.compositeBuffer();
 *      ByteBuf heapBuffer = Unpooled.buffer(10);
 *      ByteBuf directBuffer = UnpeapBuffer, directBuffer);
 *      // compBuffer.removeComponent(0);
 *      // 使用迭代器来遍历
 *      compBuffer.forEach(System.out::println)
 *
 **/
public class ByteBufTest {
    public static void main(String[] args) {
        ByteBuf buffer = Unpooled.buffer(10);

        for (int i = 0; i < buffer.capacity(); i++) {
            // 相对方法，会修改索引，此处是写操作，修改的是写索引
            buffer.writeByte(i);
            System.out.println(
                "b=[readIndex=" + buffer.readerIndex() + ", writeIndex=" + buffer.writerIndex() + ", " + "capacity="
                    + buffer.capacity() + "]");
        }

        for (int i = 0; i < buffer.capacity(); i++) {
            // 绝对方法，不会修改索引
            byte b = buffer.getByte(i);
            System.out.println(
                "b=" + b + " [readIndex=" + buffer.readerIndex() + ", writeIndex=" + buffer.writerIndex() + ", "
                    + "capacity=" + buffer.capacity() + "]");
        }

        for (int i = 0; i < buffer.capacity(); i++) {
            // 相对方法，会修改索引，此处是读操作，修改的是读索引
            byte b = buffer.readByte();
            System.out.println(
                "b=" + b + " [readIndex=" + buffer.readerIndex() + ", writeIndex=" + buffer.writerIndex() + ", "
                    + "capacity=" + buffer.capacity() + "]");
        }
    }
}
