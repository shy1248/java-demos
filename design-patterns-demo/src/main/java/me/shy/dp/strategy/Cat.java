/**
 * @Date        : 2021-02-11 11:26:16
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 测试 pojo
 */
package me.shy.dp.strategy;

import lombok.Data;

@Data
public class Cat {
	private int weight;
    private int height;

    public Cat(int weight, int height) {
        this.weight = weight;
        this.height = height;
    }
}
