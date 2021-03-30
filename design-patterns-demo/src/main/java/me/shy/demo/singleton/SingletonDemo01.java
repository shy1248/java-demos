/**
 * @Date        : 2021-02-10 21:30:12
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 单例模式 ---- 饿加载，由 JVM 保证线程安全，缺点是当类加载时就会实例化单例对象，推荐使用
 */
package me.shy.demo.singleton;

public class SingletonDemo01 {

    // 静态初始化实例
    public static final SingletonDemo01 INSTANCE = new SingletonDemo01();

    // 将构造方法私有，以防止外部 new 出对象而破坏单例特性
    private SingletonDemo01() {
    }

    // 暴露出获取单例实例的静态方法
    public static SingletonDemo01 getInstance() {
        return INSTANCE;
    }

    public static void main(String[] args) {
        SingletonDemo01 demo01 = SingletonDemo01.getInstance();
        SingletonDemo01 demo02 = SingletonDemo01.getInstance();
        System.out.println(demo01 == demo02);
    }

}
