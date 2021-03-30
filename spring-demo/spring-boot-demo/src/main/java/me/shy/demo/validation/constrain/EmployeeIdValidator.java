/**
 * @Date        : 2020-10-25 00:25:29
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 自定义验证器，雇员 id 不能大于 10000
 */

package me.shy.demo.validation.constrain;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import me.shy.demo.validation.domain.Employee;
import me.shy.demo.validation.exception.GlobalException;

public class EmployeeIdValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        // 对于 Employee 及其子类有效
        return clazz.isAssignableFrom(Employee.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Employee e = (Employee) target;
        int eid = e.getId();
        if (e.getId() > 10000) {
            throw new GlobalException(100, "Value too large for employee id (< 10000), current is: " + eid);
        }
        if ("".equals(e.getName())) {
            throw new GlobalException(101, "The name of employee must not be empty!");
        }
    }

}
