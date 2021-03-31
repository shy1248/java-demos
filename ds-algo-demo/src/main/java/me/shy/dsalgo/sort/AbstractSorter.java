/**
 * @Date        : 2021-02-19 13:40:42
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : -
 */
package me.shy.dsalgo.sort;

public abstract class AbstractSorter<E extends Comparable<? super E>> {

    abstract void sort(E[] array);
}
