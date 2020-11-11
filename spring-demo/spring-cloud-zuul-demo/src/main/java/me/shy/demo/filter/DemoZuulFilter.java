/**
 * @Date        : 2020-11-11 22:38:50
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : Zuul 过滤器 Demo
 */
package me.shy.demo.filter;

import javax.servlet.http.HttpServletRequest;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import org.springframework.stereotype.Component;

@Component
public class DemoZuulFilter extends ZuulFilter {

    @Override
    public int filterOrder() {
        // 过滤器的顺序
        return 0;
    }

    @Override
    public String filterType() {
        // 定义过滤器的类型，zuul 中定义了四种不同生命周期的过滤器类型：
        // 1. pre：路由之前
        // 2.routing：路由之时
        // 3.post：路由之后
        // 4.error：发送错误调用
        return "pre";
    }

    // 具体的过滤逻辑
    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        System.err.println(String.format("%s >>> %s", request.getMethod(), request.getRequestURI().toString()));
        String accessToken = request.getParameter("token");
        if (null == accessToken) {
            System.err.println("Access token is empty!!!");
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            try {
                ctx.getResponse().getWriter().write("Access token is empty!!!");
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            System.err.println("OK!!!");
        }
        return null;
    }

    @Override
    public boolean shouldFilter() {
        // 什么时候需要过滤，此处返回 true 即表示任何时候都过滤
        return true;
    }

}
