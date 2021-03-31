/**
 * @Date        : 2021-02-12 16:22:47
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : -
 */
package me.shy.dp.chainofresponsibility;

public class TimestampFilter implements Filter {

    @Override
    public boolean doFilter(Request request, Response response, Filter last) {
        System.out.println("Entering Timestamp Filter ...");
        request.setMessage(String.format("[Timestamp] %s", request.getMessage()));
        System.out.println(String.format("Current request is: %s", request.getMessage()));
        last.doFilter(request, response, last);
        response.setBody(String.format("[Timestamp] %s", response.getBody()));
        System.out.println(String.format("Current response is: %s", response.getBody()));
        System.out.println("Leaving Timestamp Filter ...");
        return true;
    }

}
