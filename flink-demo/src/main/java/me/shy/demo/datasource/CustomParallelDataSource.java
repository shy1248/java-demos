package me.shy.demo.datasource;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.RichParallelSourceFunction;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: 自定义flink数据源，支持多并行度。
 * 实现方式有2种：实现 ParallelSourceFunction<T> 或者继承 RichParallelSourceFunction<T>
 * 其中，ParallelSourceFunction<T> 方式与单并行度的方式一摸一样，而继承 RichParallelSourceFunction<T>
 * 的方式可以重写父类的 open 与 close 方法，用于资源的打开与释放。
 */
public class CustomParallelDataSource extends RichParallelSourceFunction<String> {
    private static final long serialVersionUID = -3771550524687177228L;
    private Long count = 0L;
    private boolean isRunning = true;

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        // 此处的并行度可设置为大于1的数
        DataStream<String> dataStream = environment.addSource(new CustomParallelDataSource()).setParallelism(3);
        dataStream.map(new MapFunction<String, String>() {
            private static final long serialVersionUID = -9097641958736881436L;

            @Override
            public String map(String value) throws Exception {
                System.out.println("Recevied data: " + value);
                return value;
            }
        }).print();

        environment.execute();
    }

    // 此方法用于初始化，在任务启动时执行一次，常用于准备资源连接等
    @Override
    public void open(Configuration parameters) throws Exception {
        System.out.println("Data source is Opened!");
    }

    // 产生数据的主要方法，一般是循环产生数据
    @Override
    public void run(SourceContext<String> ctx) throws Exception {
        System.out.println("Starting produce data...");
        while (isRunning) {
            ctx.collect(count.toString());
            count++;
            Thread.sleep(1000);
        }
    }

    // 取消flink作业时会调用此方法
    @Override
    public void cancel() {
        System.out.println("Task is canceled!");
        this.isRunning = false;
    }

    // 此方法用于释放资源
    @Override
    public void close() throws Exception {
        System.out.println("Data source is Closed!");
    }
}
