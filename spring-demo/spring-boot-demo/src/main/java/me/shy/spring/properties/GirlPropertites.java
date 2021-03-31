/**
 * @Since: 2019-12-07 13:40:13
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 * @LastTime: 2019-12-07 13:44:05
 *
 * 配置文件读取
 * Spring 配置文件支持 yaml 和 properties 2种格式，官方比较推荐使用 yaml 格式。
 * 如果 properties 和 yaml 文件同时存在，且文件中具有相同的配置项，那么会优先读取 yaml 文件中的配置，然后
 * 读取 properties 中的配置，这样就导致 properties 文件中的配置会覆盖 yaml 文件中的配置。
 *
 * 另外，还可以通过 @PropertySource 注解读取自定义的配置文件，默认情况下自定义的配置文件只支持 properties 格式
 * 的配置文件，可以通过实现 PropertySourceFactory 接口或者继承 DefalutPropertySourceFactory 类来自定义解析格式，
 * 如 YamlSourceFactory。
 *
 * @PropertySource 有 3 个参数：
 * 1.value用于指定配置文件；
 * 2.encoding用于指定配置文件编码；
 * 3.factory用于指定文件解析工厂，默认为 DefaultPropertySourceFactory ，只能解析 properties 文件；
 *
 * 如：@PropertySource(value = {"classpath:test.yml"},encoding = "gbk")
 *
 */
package me.shy.spring.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
// 将配置组转换为 Pojo
@ConfigurationProperties(prefix = "girl")
public class GirlPropertites {

    private Integer age;
    private String cupSize;

}

