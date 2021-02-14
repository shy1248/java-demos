/**
 * @Date        : 2021-02-14 16:17:12
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 代理类，可给 Moveable 类型的对象进行代理
 */
package me.shy.demo.proxy.statics;

public class MoveableLogedProxy implements Moveable {
    private Moveable m;

    @Override
    public void move(int speed) {
        System.out.println(String.format("[%s]: Starting moved ...", System.currentTimeMillis()));
        m.move(speed);
        System.out.println(String.format("[%s]: Stop moved.", System.currentTimeMillis()));
    }

    public MoveableLogedProxy(Moveable m) {
        this.m = m;
    }
}
