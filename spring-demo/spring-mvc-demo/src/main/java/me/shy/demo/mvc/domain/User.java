package me.shy.demo.mvc.domain;

import java.util.List;

import lombok.Data;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 */

@Data
public class User {
    private int id;
    private String username;
    private String password;
    private List<String> favers;
    private String icon;
}
