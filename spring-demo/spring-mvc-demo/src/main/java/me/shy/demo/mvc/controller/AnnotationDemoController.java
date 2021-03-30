package me.shy.demo.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import me.shy.demo.mvc.domain.User;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: HandlerMethod
 *
 * Controller方法：
 * 1.Controller的HandlerMethod的返回值定义为String或其它类型，表示会跳转或重定向，如果是void，表示不需要跳转或重定向；
 * 2.Controller中的方法参数可以任意定义，只要是能注入，spring都会注入。包括HttpRequest，HttpResponse等对象；
 * 3.如果在方法前加上了@ResponseBody注解，表示方法的返回值需要被转换成JSON，以流格式输出到客户端，同时设置响应头为的
 *   Contant-Type为：application/json;charset=utf-8;
 * 4.如果在方法前加上了@ResponseBody注解，但是方法返回值不能被转为json，如纯字符串的基本类型，则以格式原样输出到客户端，同时设置响应头为的
 *   Contant-Type为：text/html;中文会出现乱码。此时可在@RequestMapping注解中添加：produces="text/html;charset=utf-8"，这样响应头中
 *   的Content-Type就会是：text/html;charset=utf-8，解决了中文输出的问题；
 *
 * 传参：
 * 1.基本数据类型默认只需要与方法形参名保持一致即可（注意类型不能转换时会报400）；
 * 2.如果方法参数为一个对象，则请求参数名与对象中的成员属性保持一致即可；
 * 3.可以在方法参数前使用注解@RequestParam，关于@RequestParam的说明：
 *   value：参数名称；
 *   defaultValue：默认值，如果请求没传该参数，就是用这个默认值，只对基本数据类型有效；
 *   required：boolean，默认为false，为true表示该参数是必须的，但是如果设置了defaultValue也是不用传递参数的；
 * 4.对于请求参数中包含多个同名参数，可以使用List来接受，但是必须使用@RequestParam来指定参数名；
 * 5.特殊类型参数：
 *   对于参数以点分隔的参数：如user.name=demo，则会在方法参数的对象中找到user属性，然后给user对象的name属性赋值；
 *   对于参数是索引形式的：如users[0]=demo，则会在方法参数的对象中找到名为users的List属性，并给这个List索引为0的赋值为demo；
 * 6.restful风格（参数为URL的虚拟路径，而非传统的？形式）参数（@RequestMapping("demos/user/{name}/{age}")），需要在参数前
 *   使用@PathVariable注解来获取，默认按照方法形参名来获取，如果参数名与方法形参名不一致，可在@PathVariable中设置参数名；
 *
 * 跳转方式：
 * 1.默认为forward方式跳转；
 * 2.在return字段中使用redirect:前缀表示使用重定向；
 * 3.在return字段中使用forward:前缀表示使用跳转；
 * 4.如果加了前缀，就不会走自定义视图解析器；
 *
 * 视图解析器：
 * 1.springMVC会提供默认的视图解析器
 *
 *
 * 响应传参：
 * 1.使用jsp九大内置对象的setAttrbuted方法来传值；
 * 2.使用Map来传值，map.put("key", "value")，map在request作用域，spring会实例化一个BindingAwareModelMap来进行传值；
 * 3.使用springMVC的Model接口来传值，model.addAttrbute("key","value")，作用域为request；
 * 4.使用springMVC的ModeAndView对象：
 *      ModelAndView mav = new ModelAndView("/index.jsp");
 *      mav.addObject("key", "value");
 *      return mav;
 *   作用域为request；
 *
 * 文件下载：
 * 1.文件下载是根据响应头的Content-Disposition的值来设定的，默认为inline，浏览器的处理方式是能展示就展示，否则就下载；
 * 2.通过设置响应头的Content-Disposition的值为：attachment;filename=文件名，来告诉浏览器以附件形式下载文件，filename指定了
 *   下载是文件的显示名；
 *
 * 文件上传：
 * 1.使用apache的commons-fileupload.jar实现文件上传；
 * 2.MultipartResolver是把客户端的上传的文件流转换成MultipartFile封装类，然后获取文件流；
 * 3.表单数据分类：
 *   <form>标签的entype属性表示表单的数据类型；
 *   默认值为：application/x-www-form-urlencoded，表示少量文字信息的普通表单；
 *   text/plain，大量文字信息使用的类型，如邮件、论文等；
 *   multipart/form-data：表单中包含二进制文件内容；
 * 4.文件上传必须使用post方式，post最大支持2Gb的数据，get则为1K，且post是字节流，get是字符流；
 *
 * 自定义拦截器：
 * 1.springmvc的拦截器与Servlet的Filter比较相似，发送请求时被拦截器拦截，在控制器前后添加额外功能，AOP是对特定方法的扩充，一般针对的是
 *   service层；而拦截器针对的是controller层；
 * 2.springmvc的拦截器只拦截走controller的请求，对于jsp以及配置了的静态资源不会进行拦截；而Filter会拦截匹配url范围的所有请求；
 * 3.要实现HandlerInterceptor接口；
 * 4.多个拦截器组成一个拦截器栈，遵行先进后出；
 */
@Controller
public class AnnotationDemoController {
    @RequestMapping("annotationMVC")
    public String annotationDemo() {
        System.out.println("This message attached by spring mvc, controller is: " + this.getClass().getName());
        return "demo";
    }

    @RequestMapping("regist")
    public String registe(User user) {
        System.out.println(user);
        return "demo";
    }
}
