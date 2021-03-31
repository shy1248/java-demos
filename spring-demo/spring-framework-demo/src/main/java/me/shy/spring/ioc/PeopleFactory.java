package me.shy.spring.ioc;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 */
public class PeopleFactory {
    public People newInstance() {
        System.out.println("New instance people with instance factory!");
        return new People(1, "Jiony");
    }
}
