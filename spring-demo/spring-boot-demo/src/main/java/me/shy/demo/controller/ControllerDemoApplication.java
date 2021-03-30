/**
 * @Since: 2019-12-07 14:27:54
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 * @LastTime: 2019-12-24 14:35:46
 *
 * Controller 接受参数汇总
 *
 * 总体来说可分为3类：
 * 1. GET请求通过拼接 URL 进行传参；包括 @PathVariable 和 @RequestParam；
 * 2. POST请求通过请求体进行传递；
 * 3. 通过请求头部进行参数传递；
 *
 */
package me.shy.demo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
// 第一级 Path
@RequestMapping(value = "/hi")
public class ControllerDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ControllerDemoApplication.class, args);
    }

    // 通过 @PathVariable
    @RequestMapping(value = "/hello/{name}")
    public String echoPathVariable(@PathVariable("name") String name) {
        return "Hello, " + name + "!";
    }

    @RequestMapping(value = "/{name}/hello")
    public String echoPathVariableE(@PathVariable("name") String name) {
        return "Hello, " + name + "!";
    }

    // 通过 @RequestParam
    @RequestMapping(value = "/hello")
    public String echoRequestParam(@RequestParam(value = "name", required = true) String name,
            @RequestParam(value = "id", required = false, defaultValue = "0123456") String id) {
        return "Hello, " + name + ", you id is: " + id;
    }

    @RequestMapping(value = "/hello", method = RequestMethod.POST)
    public String echoPost(@RequestParam(value = "name", required = true) String name,
            @RequestParam(value = "id", required = false, defaultValue = "0123456") String id) {
        return "Hello, " + name + ", you id is: " + id + ", by POST.";
    }

    // @PathVariable
    @GetMapping("/{id}")
    public String getPathVariable(@PathVariable String id) {
        return "id=" + id;
    }

    // @RequestParam
    @GetMapping("/getRequestParam")
    public String getRequestParam(@RequestParam(value = "id") String id) {
        return "id=" + id;
    }

    // 无注解传参，这种方式与第二种方式最大的区别是参数可以不传
    // 相当于 @RequestParam(required = False)
    @GetMapping("/getString")
    public String getString(String id) {
        return "id=" + id;
    }

    // 直接通过 HttpServletRequest 对象获取参数
    @GetMapping("/getHttpServletParam")
    public String getParam(HttpServletRequest request) {
        String id = request.getParameter("id");
        return "id=" + id;
    }

    // @RequestBody
    // 一般用于接受 POST 请求体的参数，相应的参数必须要传递
    @PostMapping("/getRequestBody")
    public String getRequestBody(@RequestBody String id) {
        return "id=" + id;
    }

    // @RequestHeader
    // 获取请求头中的参数
    @PostMapping("/getHeaderParam")
    public String getHeaderParam(@RequestHeader String id) {
        return "id=" + id;
    }
}
