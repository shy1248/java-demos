/**
 * @Date        : 2021-02-12 16:27:19
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : -
 */
package me.shy.dp.chainofresponsibility.v2;

public class LoggingFilter implements Filter {

    @Override
    public void doFilter(Request request, Response response, FilterChain chain) {
        System.out.println("Entering Logging Filter ...");
        request.setMessage(String.format("[Logging] %s", request.getMessage()));
        System.out.println(String.format("Current request is: %s", request.getMessage()));
        chain.doFilter(request, response);
        response.setBody(String.format("[Logging] %s", response.getBody()));
        System.out.println(String.format("Current response is: %s", response.getBody()));
        System.out.println("Leaving Logging Filter ...");
    }

}
