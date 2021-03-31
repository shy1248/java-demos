package me.shy.spring.ioc;

import lombok.Data;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 */

@Data
public class People {
    private int id;
    private String name;

    public People(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
