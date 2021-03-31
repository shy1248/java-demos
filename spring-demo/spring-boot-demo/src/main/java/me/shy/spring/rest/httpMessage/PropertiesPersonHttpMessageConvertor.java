/**
 * @Date        : 2020-10-19 23:04:54
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : -
 */
package me.shy.spring.rest.httpMessage;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Properties;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import me.shy.spring.rest.domain.Person;

public class PropertiesPersonHttpMessageConvertor extends AbstractHttpMessageConverter<Person> {
    public PropertiesPersonHttpMessageConvertor() {
        //  1. 直接调用父类构造方法，创建一个新的被支持的 MIMEType： application/properties+person
        super(MediaType.valueOf("application/properties+person"));
        // 2. 设置该 MIMEType 所支持的 编码
        setDefaultCharset(Charset.forName("UTF-8"));
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        // 3. 设置该 MIMEType 所支持的 class，此处为 Person 和其子类
        return clazz.isAssignableFrom(Person.class);
    }

    @Override
    protected Person readInternal(Class<? extends Person> clazz, HttpInputMessage inputMessage)
            throws IOException, HttpMessageNotReadableException {
        // 4.实现读入请求体解析
        // 即客户端发送的请求体内容为格式为：
        // person.id = 1
        // person.name = shy
        // 需要将这种格式转换为 Person 的实例

        // 4.1 获取请求体的输入流，以读取信息
        InputStream inputStream = inputMessage.getBody();
        // 4.2 构建 Properties 对象用于从输入流接受信息
        Properties properties = new Properties();
        // 4.3 读入请求体
        properties.load(inputStream);
        // 4.5 创建 Person 实例，接收 properties 实例的信息并返回
        Person person = new Person();
        person.setId(Long.valueOf(properties.getProperty("person.id")));
        person.setName(properties.getProperty("person.name"));
        return person;
    }

    @Override
    protected void writeInternal(Person t, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {
        // 5.实现响应体的输出
        // 将 Person 的实例以 properties 格式输出到客户端

        // 5.1 构建 propertis 实例，用于存放 Person 实例的信息
        Properties properties = new Properties();
        properties.setProperty("person.id", String.valueOf(t.getId()));
        properties.setProperty("person.name", t.getName());
        // 5.2 获取响应输出流， 并将 properties 内容写到客户端
        OutputStream outputStream = outputMessage.getBody();
        properties.store(outputStream, "Written from web server!");
    }

    // 最后需要通过自定义配置的方式将这个 MIMEType 加入到 spring boot 的 message convertor 列表中
    // 参见 me.shy.demo.boot.rest.config.CustomMimeTypeOrderConfigurer 中的实现。
}
