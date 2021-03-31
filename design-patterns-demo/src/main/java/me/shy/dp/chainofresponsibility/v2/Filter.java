/**
 * @Date        : 2021-02-12 16:15:12
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : -
 */
package me.shy.dp.chainofresponsibility.v2;

public interface Filter {
    void doFilter(Request request, Response response, FilterChain chain);
}
