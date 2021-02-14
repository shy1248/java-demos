/**
 * @Date        : 2021-02-12 16:16:17
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : -
 */
package me.shy.demo.chainofresponsibility;

import java.util.Stack;

public class FilterChain implements Filter {
    // Using Stack, (Fist In, Last Out) to saving Filters.
    private Stack<Filter> lasts = new Stack<>();

    @Override
    public boolean doFilter(Request request, Response response, Filter last) {
        if (! this.lasts.empty()) {
            Filter nextFilter = this.lasts.pop();
            return nextFilter.doFilter(request, response, this);
        }
        System.out.println("There is no filter in chain.");
        return false;
    }

    public FilterChain addFilter(Filter filter) {
        this.lasts.push(filter);
        return this;
    }


}
