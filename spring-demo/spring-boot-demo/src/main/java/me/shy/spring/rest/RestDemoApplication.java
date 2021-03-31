/**
 * @Date        : 2020-10-19 22:27:23
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : Rest MIMEType
 *
 * MIMEType: 即媒体类型，客户端在发送请求时，通过请求头中指定 Accpect 向服务器声明自己能够接受的类型；
 * 通过在请求头中指定 Content-Type 来指定发送请求体的媒体类型；Spring boot 中处理这些 MIMEType的
 * 类是 XXXXMessageConvertor类，Spring boot 中默认会加入一些处理客户端与服务端的 message convertor，
 * 这些 convertor 都有2个方法，canRead 与 canWrite，即在读入请求体和写出响应体时分别用到的方法。
 *
 * 如果客户端请求时指定了 Accept，且 Spring boot 支持，就直接调用响应的 convertor 类来处理，否则就是从
 * 默认支持的 convertor List 中轮训匹配，哪个 convertor 类匹配上，就使用哪个。
 *
 * 如：
 * GET  http://localhost:8080/person/001?name=shy HTTP/1.1
 * 没有指定 Accept 时，以 application/json 格式返回响应
 *
 * HTTP/1.1 200
 * Content-Type: application/json;charset=UTF-8
 * Transfer-Encoding: chunked
 * Date: Mon, 19 Oct 2020 14:45:08 GMT
 * Connection: close
 *
 * {
 *   "id": 1,
 *   "name": "shy"
 * }
 *
 * 而当指定了 Accept: application/xml时，spring 的响应就以xml格式返回：
 *
 * HTTP/1.1 200
 * Content-Type: application/xml;charset=UTF-8
 * Transfer-Encoding: chunked
 * Date: Mon, 19 Oct 2020 14:46:04 GMT
 * Connection: close
 *
 * <Person>
 *   <id>1</id>
 *   <name>shy</name>
 * </Person>
 *
 * 这是因为，在 Spring boot 的默认 message convertor 列表中，application/json 的 convertor
 * 位于 application/xml 的 convertor 前面，首先匹配上 application/json 的 convertor
 *
 * 可以通过自定义配置的方式来改变这一默认顺序, 参见 me.shy.demo.boot.rest.config.CustomMimeTypeOrderConfigurer
 * 的实现，将 xml 的 convertor 设置到默认 convertor 列表的第一位，当不设置 Accept 时，会以 XML 格式响应请求。
 *
 * 还可以自定义 MIMEType，本例子中将结合 Person 这个 pojo 实现一个 application/properties+person 的 MIMEType。
 * 详见 me.shy.demo.boot.rest.httpMessage.PropertiesHttpMessageConvertor 的实现。
 *
 * 如：
 * GET  http://localhost:8080/person/001?name=shy HTTP/1.1
 # 指定 Accept: application/properties+person 时，响应如下：

 * HTTP/1.1 200
 * Content-Type: application/properties+person;charset=UTF-8
 * Transfer-Encoding: chunked
 * Date: Mon, 19 Oct 2020 15:49:37 GMT
 * Connection: close
 *
 * #Written from web server!
 * #Mon Oct 19 23:49:37 CST 2020
 * person.name=shy
 * person.id=1
 *
 */
package me.shy.spring.rest;

import javax.validation.constraints.NotNull;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import me.shy.spring.rest.domain.Person;

@SpringBootApplication
@RestController
public class RestDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(RestDemoApplication.class, args);
    }

    @GetMapping("/person/{id}")
    public Person person(@PathVariable @NotNull String id, @RequestParam(required = false) String name) {
        Person p = new Person();
        p.setId(Long.valueOf(id));
        p.setName(name);
        return p;
    }

    // 请求为 json 格式，响应为 properties 格式

    /**
    请求：
    POST http://localhost:8080/person/json/to/properties HTTP/1.1
    Accept: application/properties+person
    Content-Type: application/json

    {
    "id": 1001,
    "name": "Tom"
    }

    响应：
    HTTP/1.1 200
    Content-Type: application/properties+person;charset=UTF-8
    Transfer-Encoding: chunked
    Date: Mon, 19 Oct 2020 16:02:53 GMT
    Connection: close

    #Written from web server!
    #Tue Oct 20 00:02:53 CST 2020
    person.name=Tom
    person.id=1001
    */
    @PostMapping(value = "/person/json/to/properties",
            // consumes: 用于指定可以接受的客户端的请求格式
            consumes = "application/json",
            // produces: 用于指定响应体的格式
            produces = "application/properties+person")
    public Person jsonToPropertiesWithPerson(@RequestBody Person p) {
        return p;
    }

    // 请求为 properties 格式，响应为 json 格式
    /**
    请求：
    POST http://localhost:8080/person/properties/to/json HTTP/1.1
    Accept: application/json
    Content-Type: application/properties+person

    person.id = 1002
    person.name = Jerry

    响应：
    HTTP/1.1 200
    Content-Type: application/json;charset=UTF-8
    Transfer-Encoding: chunked
    Date: Mon, 19 Oct 2020 16:04:39 GMT
    Connection: close

    {
    "id": 1002,
    "name": "Jerry"
    }
    */
    @PostMapping(value = "/person/properties/to/json",
            // consumes: 用于指定可以接受的客户端的请求格式
            consumes = "application/properties+person",
            // produces: 用于指定响应体的格式
            produces = "application/json")
    public Person propertiesToJsonWithPerson(@RequestBody Person p) {
        return p;
    }
}
