package me.shy.spring.ioc;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 */
public class StaticPeopleFactory {
    public static People getInstance() {
        System.out.println("Get instance People using static factory.");
        return new People(2, "Mark");
    }
}
