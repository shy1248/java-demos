/**
 * @Date        : 2020-10-19 22:50:54
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 自定义配置，修改 Spring boot 默认的 message convertor 顺序
 */
package me.shy.spring.rest.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import me.shy.spring.rest.httpMessage.PropertiesPersonHttpMessageConvertor;

@Configuration
public class CustomMimeTypeOrderConfigurer implements WebMvcConfigurer {

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 将 xml 的 convertor 直接放到第一位，当请求不设置 Accept 时，默认就以 XML 格式响应
        // converters.set(0, new MappingJackson2XmlHttpMessageConverter());

        // 添加自定义的 MIMEType 支持
        converters.add(new PropertiesPersonHttpMessageConvertor());
    }

}
