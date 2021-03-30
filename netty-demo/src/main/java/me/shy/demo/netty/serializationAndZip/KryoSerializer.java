package me.shy.demo.netty.serializationAndZip;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import java.io.ByteArrayOutputStream;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 */
public class KryoSerializer {

    /**
     * Kryo 序列化
     * @param source
     * @param target
     */
    public static void serialize(Object source, ByteBuf target) {
        Kryo kryo = ThreadLocalKryoFactory.getInstance();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Output output = new Output(baos);
        kryo.writeClassAndObject(output, source);
        byte[] bytes = output.toBytes();
        output.close();
        target.writeBytes(bytes);
    }

    /**
     * Kryo 反序列化
     * @param source
     * @return
     */
    public static Object deserialize(ByteBuf source) {
        if (null == source) {
            return null;
        }
        Input input = new Input(new ByteBufInputStream(source));
        return ThreadLocalKryoFactory.getInstance().readClassAndObject(input);
    }
}
