/**
 * @Date        : 2020-12-06 01:01:05
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 高 CPU 使用例子
 */
package me.shy.demo;

import java.util.Random;

public class HighCPUDemo {
    public static void main(String[] args) {
        Random random = new Random();
        while (true) {
            random.nextFloat();
        }
    }
}
