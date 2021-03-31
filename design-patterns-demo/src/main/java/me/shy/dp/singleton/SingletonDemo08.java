/**
 * @Date        : 2021-02-10 23:07:58
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 单例模式 ---- 懒加载版6，使用枚举类方式实现，线程安全由 JVM 保证，同时能保证反序列化时对单例的破坏
 *                             因为枚举类没有构造方法，因此就不能通过反射的机制创建实例
 */
package me.shy.dp.singleton;

public enum SingletonDemo08 {
    INSTANCE;

    public static void main(String[] args) {

        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                System.out.println(String.format("Instance[%s] hascode ===> %s.", Thread.currentThread().getId(),
                        SingletonDemo08.INSTANCE.hashCode()));
            }).start();
        }
    }
}
