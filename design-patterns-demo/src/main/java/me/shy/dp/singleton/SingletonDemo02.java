/**
 * @Date        : 2021-02-10 21:38:06
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 单例模式 ---- 饿加载，和 SingletonDemo01 实现方法一样
 */
package me.shy.dp.singleton;

public class SingletonDemo02 {
    public static SingletonDemo02 INSTANCE;

    static {
        INSTANCE = new SingletonDemo02();
    }

    // 将构造方法私有，以防止外部 new 出对象而破坏单例特性
    private SingletonDemo02() {
    }

    // 暴露出获取单例实例的静态方法
    public static SingletonDemo02 getInstance() {
        return INSTANCE;
    }

    public static void main(String[] args) {
        SingletonDemo02 demo01 = SingletonDemo02.getInstance();
        SingletonDemo02 demo02 = SingletonDemo02.getInstance();
        System.out.println(demo01 == demo02);
    }
}
