package me.shy.demo.datasource;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;

/**
 * @Since: 2019-12-21 22:31:24
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: 自定义flink数据源，单线程，无并行度。
 *               <p>
 *               flink 数据源类型： 1.readTextFile - 读取文件 2.fromCollection - 从集合读取数据
 *               3.消息队列，如kafka等 4.自定义数据源
 *               <p>
 *               System.out.println("Starting produce data...");
 *               需要继承自接口：SourceFunction<T> 或者集成自 RichSourceFunction<T> 抽象类，
 *               继承的方式可以重写父类的 open 与 close 方法，用于资源的打开与释放。 需要指定产生的数据类型，且并行度只能为1
 */
public class CustomSignleDataSource implements SourceFunction<String> {
    private static final long serialVersionUID = 1L;
    private Long count = 0L;
    private boolean isRunning = true;

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment environment =
                StreamExecutionEnvironment.getExecutionEnvironment();
        // 此处的并行度只能设置为1
        DataStream<String> dataStream =
                environment.addSource(new CustomSignleDataSource()).setParallelism(1);
        dataStream.map(new MapFunction<String, String>() {
            private static final long serialVersionUID = 1L;

            @Override
            public String map(String value) throws Exception {
                System.out.println("Recevied data: " + value);
                return value;
            }
        }).print();

        environment.execute();
    }

    // 产生数据的主要方法，一般是循环产生数据
    @Override
    public void run(SourceContext<String> ctx) throws Exception {
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
}
