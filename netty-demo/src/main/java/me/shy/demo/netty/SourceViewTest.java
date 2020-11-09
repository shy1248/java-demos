package me.shy.demo.netty;

import io.netty.util.NettyRuntime;
import io.netty.util.internal.SystemPropertyUtil;

/**
 * @Since: 2020/3/24 18:55
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 *
 **/
public class SourceViewTest {
    public static void main(String[] args) {
        System.out.println("Default thread number is :" + getDefaultThreadNumber());
    }

    //  default threads number for NioEventLoopGroup
    private static int getDefaultThreadNumber(){
        return Math.max(1, SystemPropertyUtil.getInt(
            "io.netty.eventLoopThreads", NettyRuntime.availableProcessors() * 2));
    }
}
