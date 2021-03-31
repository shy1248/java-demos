/**
 * @Date        : 2020-10-18 21:53:44
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 自定义拦截器
 */
package me.shy.base.annotation.example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class AccessInterceptor implements HandlerInterceptor {

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        System.out.println("Entering inteceptor: AccessInterceptor ...");

        // 反射方法获取关注的注解
        HandlerMethod method = (HandlerMethod) handler;
        LoginRequired methodAnnotation = method.getMethodAnnotation(LoginRequired.class);
        // 如果没有获取到，表示没有使用 @LoginRequired 注解，直接放行
        if (null == methodAnnotation) {
            return true;
        // 有获取到，直接拦截，并提示用户需要登陆才能访问
        } else {
            response.setContentType("application/json; charset=utf-8");
            response.getWriter().print("Want to access the resource? You must login at first ...");
            return false;
        }
    }

}
