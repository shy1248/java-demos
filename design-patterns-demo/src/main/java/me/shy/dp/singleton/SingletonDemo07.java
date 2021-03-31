/**
 * @Date        : 2021-02-10 23:01:37
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 单例模式 ---- 懒加载版5，使用内部静态类的实现方式，无需加锁，线程安全由 JVM 保证
 *
 */
package me.shy.dp.singleton;

public class SingletonDemo07 {

    // 在静态内部类中初始化实例对象，可避免实例在类加载时就实例化
    // 因为静态内部类都是在实例时才会被类加载器加载，也即实例只有在真正使用时才会被创建
    public static class InstanceHoldor {
        public static final SingletonDemo07 INSTANCE = new SingletonDemo07();
    }

    private SingletonDemo07() {
    }

    public static SingletonDemo07 getInstance() {
        return SingletonDemo07.InstanceHoldor.INSTANCE;
    }

    public static void main(String[] args) {

        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                System.out.println(String.format("Instance[%s] hascode ===> %s.", Thread.currentThread().getId(),
                        SingletonDemo07.getInstance().hashCode()));
            }).start();
        }
    }
}
