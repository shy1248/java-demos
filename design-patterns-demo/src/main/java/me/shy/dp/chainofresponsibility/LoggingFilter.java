/**
 * @Date        : 2021-02-12 16:27:19
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : -
 */
package me.shy.dp.chainofresponsibility;

public class LoggingFilter implements Filter {

    @Override
    public boolean doFilter(Request request, Response response, Filter last) {
        System.out.println("Entering Logging Filter ...");
        request.setMessage(String.format("[Logging] %s", request.getMessage()));
        System.out.println(String.format("Current request is: %s", request.getMessage()));
        last.doFilter(request, response, last);
        response.setBody(String.format("[Logging] %s", response.getBody()));
        System.out.println(String.format("Current response is: %s", response.getBody()));
        System.out.println("Leaving Logging Filter ...");
        return false;
    }

}
