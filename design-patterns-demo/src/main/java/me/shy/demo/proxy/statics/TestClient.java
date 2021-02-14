/**
 * @Date        : 2021-02-14 16:21:26
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : -
 */
package me.shy.demo.proxy.statics;

public class TestClient {
    public static void main(String[] args) {
        // 使用被代理自身
        Moveable m = new Tank();
        m.move(10);

        // 使用日志代理类来代理运行
        MoveableLogedProxy proxy = new MoveableLogedProxy(m);
        proxy.move(100);
    }
}
