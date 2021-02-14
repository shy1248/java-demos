/**
 * @Date        : 2021-02-11 11:40:49
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 配置文件加载策略接口
 */
package me.shy.demo.utils;

import java.io.InputStream;

public interface ConfigLoadStrategy<T> {

    // void load(String path) throws FileNotFoundException;
    T load(InputStream in, Class<T> clasz);
}
