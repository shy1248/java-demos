package me.shy.demo.vertx;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 */
public class AsyncDemos {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();

        Router router = Router.router(vertx);

        // 默认情况下，router 的 handler 方法是同步调用的，同过打印线程id，发现处理该请求的线程为：vert.x-eventloop-thread-X
        // 即事件环的主线程，如果要做耗时操作，会阻塞主事件环
        router.route("/sync").handler(context -> {
            context.response().end("Hello, Sync!");
            System.out.println("Sync: " + Thread.currentThread().getName());
        });

        // router 的 blockingHandler 方法是异步步调用的，同过打印线程id，发现处理该请求的线程为：vert.x-worker-thread-X
        // 一般的耗时操作（查询数据库或者请求远程服务等）使用异步，能够提高并发量
        router.route("/async").blockingHandler(context -> {
            context.response().end("Hello, Async!");
            System.out.println("Async: " + Thread.currentThread().getName());
        }, false);

        HttpServer httpServer = vertx.createHttpServer();
        httpServer.requestHandler(router::accept);
        httpServer.listen(8080);
    }

}
