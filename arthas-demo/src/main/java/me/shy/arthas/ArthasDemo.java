/**
 * @Date        : 2020-12-06 01:32:22
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : -
 */
package me.shy.arthas;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class ArthasDemo {

    public static void main(String[] args) {
        ArthasDemo demo = new ArthasDemo();
        while (true) {
            System.out.println(demo.uuid());
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public String uuid() {
        return UUID.randomUUID().toString();
    }
}
