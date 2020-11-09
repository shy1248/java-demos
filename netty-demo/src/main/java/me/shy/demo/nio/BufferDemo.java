package me.shy.demo.nio;

import java.nio.IntBuffer;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 */
public class BufferDemo {

    public static void main(String[] args) {

        // 1.基础用法
        // 初始化一个 Int 类型的 Buffer
        // Java 的基础类型种，除了 Boolean 类型没有 Buffer，其它类型都要对应的实现
        // 用的最多的还是 ByteBuffer
        // 每一个 Buffer 都有三个属性：
        //      pos： position，当前索引的位置，从 0 开始
        //      lim： limit，当前 Buffer 中实际存储元素的个数，初始化后，当没有使用 flip 复位时，该值和 cap 相等
        //      cap:  capacity，当前 Buffer 初始化时候的总长度，allocate 方法指定
        // 每个Buffer都有两类读写操作：带索引（绝对操作）以及不带索引（相对操作），相对操作会使Buffer的position的值递增，而绝对操作不会。
        // 但是使用绝对操作时，如果指定的索引大于limit的值，则会抛异常。

        // Buffer 分为2种，JVM对缓冲，默认通过allocate()方法创建的，堆外缓冲，通过allocateDirect()方法创建。堆外缓冲通过其父类Buffer
        // 的long类型的address属性来访问堆外内存，放在父类中的目的是为了加快JNI访问速度。
        // DirectBuffer由于是直接使用堆外内存，因此数据实现了零拷贝，效率更高，同时减少了JVM内存回收压力。

        // Buffer的Scattering（分散）与Gathering（收集）
        // Buffer的Scattering表示的是可从一个channel中的数据读入一个Buffer数组中；
        // Buffer的Gathering与Scattering相反，表示可将一个Buffer数组的内容写入同一个channel

        IntBuffer intBuffer = IntBuffer.allocate(30);
        System.out.println(intBuffer);
        // put，向 Buffer 中添加元素，没有指定 index，则每次 put 后 Buffer 的 pos 都会递增
        intBuffer.put(15); // pos 0 -> 1
        intBuffer.put(21); // pos 1 -> 2
        intBuffer.put(34); // pos 2 -> 3
        System.out.println(intBuffer);
        // 使用 get(index) 获取元素时， Buffer 的 pos 不变
        System.out.println("The value at position 1 is: " + intBuffer.get(1));
        // 使用 put(index, newVlaue) 修改 Buffer 对应 pos 上的值时，pos 不变
        System.out.println("The value at position 2 is: " + intBuffer.get(2));
        intBuffer.put(2, 25);
        System.out.println("The value at position 2 changed now: " + intBuffer.get(2));

        // 当前 Buffer 的 lim 还是为 30，因为没有使用 flip 复位，因此不会抛异常
        // 但是使用 flip 复位后，lim 还是为 3，该值会丢失
        System.out.println("The value at position 10 is: " + intBuffer.get(10));
        intBuffer.put(10, 55);
        System.out.println("The value at position 10 changed now: " + intBuffer.get(10));
        // flip，复位 Buffer 的 pos 到 0
        // fori 循环遍历 buffer 时，与 i 的值无关，只会从 Buffer 的 pos 开始遍历
        // 因此，遍历时要注意使用该方法复位 pos
        intBuffer.flip();
        System.out.println(intBuffer);
        // flip 复位后，lim 的值变为 Buffer 实际元素的个数，再次获取第10位时，抛异常：IndexOutOfBoundsException
        // 因此，get(index) 和 put(index, newVlaue) 方法中的 index 必须小于或等于 Buffer 的 pos
        System.out.println("The value at position 10 is: " + intBuffer.get(10));
        intBuffer.put(10, 55);
        System.out.println("The value at position 10 changed now: " + intBuffer.get(10));

        // 使用 for 遍历之前要使用 flip 复位，否则会抛异常
        for (int i = 0; i < intBuffer.limit(); i++) {
            System.out.println("Get position " + i + " of buffer: " + intBuffer.get(i));
        }

        // 2.wrap 方法的使用
        // wrap 方法包裹一个素组，将数组转成 Buffer 对象，该 Buffer 对象的 cap 为数组的长度，
        // lim 为实际截取元素的个数，pos 为 0
        // 使用这种方式时不会指定 Buffer 的长度，因为会被 wrap 覆盖
        int[] arr = new int[]{10, 13, 44};
        IntBuffer buffer01 = IntBuffer.wrap(arr);
        System.out.println("Wrapped buffer (None range specified): " + buffer01);
        IntBuffer buffer02 = IntBuffer.wrap(arr, 0, 2);
        // buffer02.flip();
        System.out.println("Wrapped buffer(Start=0, Length=2): " + buffer02);
        // 修改 Buffer 的内容，数组的内容会变
        buffer01.put(1, 23);
        for (int i = 0; i < buffer01.limit(); i++) {
            System.out.println("Get position " + i + " of buffer: " + buffer01.get(i));
            System.out.println("Get position " + i + " of arr: " + arr[i]);
        }

        // 3.其它操作
        IntBuffer buffer03 = IntBuffer.allocate(10);

        System.out.println(buffer03);
        int[] arr1 = new int[]{10, 23, 24, 50};
        // 可以直接 put 一个数组到 Bufer 中
        buffer03.put(arr1);
        // buffer03.put(arr1, 1, 2);
        System.out.println(buffer03);
        // Buffer 复制
        IntBuffer buffer04 = buffer03.duplicate();
        System.out.println(buffer04);

        // 手动指定 pos
        buffer04.position(1);
        System.out.println(buffer04);

        // 获取 Buffer 的可用元素
        System.out.println(buffer04.remaining());

        // 将 Buffer 内容读入数据
        int[] arr2 = new int[buffer04.remaining()];
        buffer04.get(arr2);

        for (int i = 0; i < arr2.length; i++) {
            System.out.println(arr2[i]);
        }

    }

}
