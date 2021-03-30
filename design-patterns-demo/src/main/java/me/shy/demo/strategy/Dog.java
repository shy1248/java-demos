/**
 * @Date        : 2021-02-11 11:30:28
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 测试 pojo
 */
package me.shy.demo.strategy;

import lombok.Data;

@Data
public class Dog {
    private int food;

    public Dog(int food) {
        this.food = food;
    }
}
