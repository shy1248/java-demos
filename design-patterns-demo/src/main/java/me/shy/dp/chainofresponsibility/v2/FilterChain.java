/**
 * @Date        : 2021-02-12 16:16:17
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : -
 */
package me.shy.dp.chainofresponsibility.v2;

import java.util.ArrayList;
import java.util.List;

public class FilterChain {
    private List<Filter> filters = new ArrayList<>();
    private int index = -1;

    public void doFilter(Request request, Response response) {
        index++;
        if (index >= filters.size()) {
            System.out.println("There is no filter in chain.");
            return;
        }
        filters.get(index).doFilter(request, response, this);
    }

    public FilterChain addFilter(Filter filter) {
        this.filters.add(filter);
        return this;
    }


}
