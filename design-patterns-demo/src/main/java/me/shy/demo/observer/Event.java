/**
 * @Date        : 2021-02-12 22:00:13
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : a base class for Event
 */
package me.shy.demo.observer;

public class Event<T> {
    protected T source;

    public T getSource() {
        return source;
    }

    public void setSource(T source) {
        this.source = source;
    }
}
