package me.shy.demo.netty.serializationAndZip;

import com.esotericsoftware.kryo.Kryo;
import org.objenesis.strategy.StdInstantiatorStrategy;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 */
public class ThreadLocalKryoFactory {
    private static final ThreadLocal<Kryo> holder = new ThreadLocal<Kryo>() {
        @Override protected Kryo initialValue() {
            Kryo kryo = new Kryo();
            kryo.setInstantiatorStrategy(
                new Kryo.DefaultInstantiatorStrategy(new StdInstantiatorStrategy()));
            return kryo;
        }
    };

    public static Kryo getInstance() {
        return holder.get();
    }
}
