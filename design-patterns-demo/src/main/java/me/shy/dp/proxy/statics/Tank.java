/**
 * @Date        : 2021-02-14 16:14:39
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 被代理的实体类
 */
package me.shy.dp.proxy.statics;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Tank implements Moveable {

    @Override
    public void move(int speed) {
        System.out.println(String.format("Tank running with speed %s km/s ...", speed));
        try {
            TimeUnit.MILLISECONDS.sleep(new Random().nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
