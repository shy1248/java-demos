/**
 * @Date        : 2021-02-12 16:11:17
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : Chain of Responsibility, moked by HttpServlet Filter, like this:
 *
 *
 *                        LoggingFilter   TimestampFilter      Handler
 *                             |                |                |
 *     request  =============> | =============> | =============> | =============>
 *                             |                |                |              |
 *     reponse  <============= | <============= | <============= | <=============
 *                             |                |                |
 *
 */
package me.shy.demo.chainofresponsibility;

public class MokeHttpServer {

    public static void main(String[] args) {
        Request request = new Request();
        request.setMessage("Hello, server.");
        Response response = new Response();
        response.setBody("I'm response!");

        FilterChain chain = new FilterChain();
        chain.addFilter(new TimestampFilter()).addFilter(new LoggingFilter());
        chain.doFilter(request, response, chain);
    }
}
