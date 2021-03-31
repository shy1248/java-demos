/**
 * @Date        : 2020-10-25 11:52:15
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 注解 CheckCardNumber 验证器的实现，格式为 'EB-xxxx'，xxxx为正整数
 */
package me.shy.spring.validation.constrain;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import me.shy.spring.validation.annotation.CheckCardNumber;

public class CardNumberValidator implements ConstraintValidator<CheckCardNumber, String> {
    private static final String VALID_PATTERN = "EB-\\d+";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (null == value || "".equals(value.trim())) {
            return false;
        }
        return Pattern.matches(VALID_PATTERN, value);
    }

}
