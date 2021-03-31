/**
 * @Date        : 2020-10-25 00:25:29
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : -
 */
package me.shy.spring.validation.controller;

import java.util.concurrent.atomic.AtomicInteger;

import javax.validation.Valid;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import me.shy.spring.validation.constrain.EmployeeIdValidator;
import me.shy.spring.validation.domain.Employee;

@RestController
public class EmploteeController {
    private AtomicInteger idGen = new AtomicInteger();

    @GetMapping("/emp/save")
    public Employee save(@Valid @RequestParam String name) {
        int id = idGen.getAndIncrement();
        Employee e = new Employee();
        e.setId(id);
        e.setName(name);
        e.setCardNumber("EB-" + String.valueOf(id));
        return e;
    }

    // 添加验证器，只在本 controller 中生效，每次请求都会执行
    // 想要全局生效，在 @RestControllerAdvice 注解中加上 webDataBinder 参数
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(new EmployeeIdValidator());
    }

    @GetMapping("/emp/save2")
    public Employee save2(@Valid Employee e) {
        return e;
    }
}
