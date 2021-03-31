/**
 * @Date        : 2021-02-11 11:42:45
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : Yaml config file loader
 */
package me.shy.dp.utils;

import java.io.InputStream;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

public class YamlConfigLoader<T> implements ConfigLoadStrategy<T> {

    @Override
    public T load(InputStream in, Class<T> clasz) {
        Yaml yaml = new Yaml(new Constructor(clasz));
        return yaml.load(in);
    }
}
