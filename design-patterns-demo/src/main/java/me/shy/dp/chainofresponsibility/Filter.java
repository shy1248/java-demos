/**
 * @Date        : 2021-02-12 16:15:12
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : -
 */
package me.shy.dp.chainofresponsibility;

public interface Filter {
    boolean doFilter(Request request, Response response, Filter last);
}
