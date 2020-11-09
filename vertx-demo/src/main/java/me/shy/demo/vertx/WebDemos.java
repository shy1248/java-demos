package me.shy.demo.vertx;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: 路由处理
 */
public class WebDemos {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        // 路由处理
        Router router = Router.router(vertx);

        router.route("/").handler(context -> {
            context.response().end("Hello, VertX!");
        });

        router.route("/sayHello").handler(context -> {
            HttpServerRequest request = context.request();
            String name = request.getParam("name");
            String agent = request.getHeader("User-Agent");
            HttpServerResponse response = context.response();
            name = (name == null ? "Nobody" : name);
            response.end("Hello, " + name + ", you agent is: " + agent);
        });

        // GET
        router.get("/api/get").handler(context -> {
            context.response().end("Hello, GET!");
        });

        // POST
        router.post("/api/post").handler(context -> {
            context.response().end("Hello, POST!");
        });

        // Pattern
        router.get("/api/order*").handler(context -> {
            context.response().end("Hello, Pattern!");
        });

        HttpServer httpServer = vertx.createHttpServer();
        httpServer.requestHandler(router::accept);
        httpServer.listen(8080);
    }

}
