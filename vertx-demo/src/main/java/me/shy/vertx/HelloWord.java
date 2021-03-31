package me.shy.vertx;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 */
public class HelloWord {

    public static void main(String[] args) {
        // 获取 VertX 核心对象
        Vertx vertx = Vertx.vertx();
        // 获取 HTTPServer
        HttpServer server = vertx.createHttpServer();
        // 处理请求
        server.requestHandler(request -> {
            request.response().end("Hello, VertX!");
        });
        // 设置 server 监听端口
        server.listen(8080);
    }

}
