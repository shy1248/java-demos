/**
 * @Date        : 2020-10-25 11:47:55
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 定义 CardNumber 验证器的注解
 */
package me.shy.spring.validation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import me.shy.spring.validation.constrain.CardNumberValidator;


@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { CardNumberValidator.class })
public @interface CheckCardNumber {
    // 以下三个参数为必须
    String message() default "CardNumber format must be match \"EB-\\d+\"!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
