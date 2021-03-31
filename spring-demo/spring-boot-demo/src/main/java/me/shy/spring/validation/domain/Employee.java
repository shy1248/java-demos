/**
 * @Date        : 2020-10-25 00:22:01
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : -
 */
package me.shy.spring.validation.domain;


import javax.validation.constraints.NotBlank;

import lombok.Data;
import me.shy.spring.validation.annotation.CheckCardNumber;

@Data
public class Employee {
    private int id;
    @NotBlank
    private String name;
    @CheckCardNumber
    private String cardNumber;
}
