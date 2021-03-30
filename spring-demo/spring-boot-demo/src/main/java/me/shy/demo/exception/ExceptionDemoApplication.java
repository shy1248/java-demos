/**
 * @Date        : 2020-10-28 00:23:28
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : -
 */
package me.shy.demo.exception;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
public class ExceptionDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(ExceptionDemoApplication.class, args);
    }

    @GetMapping("/getName")
    public R<String> test(@RequestParam String name){
        if (StringUtils.isEmpty(name)) {
            throw new DefinedException(ResponseStatusEnum.ERROR_INCOMPLETE_REQUEST);
        } else if ("shy".equals(name)) {
            // 这里没有查询操作，当请求参数是shy的时候，模拟成查询结果为空
            throw new DefinedException(ResponseStatusEnum.ERROR_EMPTY_RESULT);
        }
        // 这里模拟一下除自定义异常外的其他两种异常
        int i = 0;
        i = 5 / i;
        return new R<String>().fillData(name);
    }
}
