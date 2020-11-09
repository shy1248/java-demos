package me.shy.demo.InParkingDataDemo;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.EventHandlerGroup;
import com.lmax.disruptor.dsl.ProducerType;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Since: 2020/5/9 21:38
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: A Disruptor demo.
 *
 * 以车辆入场为例，入场后需要存入数据库，需要发送kafka消息，两步执行完后，给用户发送短信。
 *
 *
 **/
public class InParkingDataStarter {
    public static void main(String[] args) throws InterruptedException {
        // 记录开始时间
        long begin = System.currentTimeMillis();
        // 构建 RingBuffer 大小，必须为2的N次方，否则影响性能
        int bufferSize = 1024 * 1024;
        // 构建线程池，生产者，DB入库，Kafka入库，发送短信，总共4个线程，因此构建固定大小为4的线程池
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        // 构建 disruptor，单生产者，指定等待策略（RingBuffer中无事件时消费者的等待策略为YieldingWaitStrategy）
        Disruptor<InParkingDataEvent> disruptor = new Disruptor<InParkingDataEvent>(new EventFactory<InParkingDataEvent>() {
            @Override public InParkingDataEvent newInstance() {
                return new InParkingDataEvent();
            }
        }, bufferSize, executorService, ProducerType.SINGLE, new YieldingWaitStrategy());

        // 创建消费者组，先入DB与Kafka，没有异常后发送短信给用户
        EventHandlerGroup<InParkingDataEvent> handlerGroup = disruptor.handleEventsWith(new ParkingDataInDBHandler(),
            new ParkingDataToKafkaHandler());
        handlerGroup.then(new ParkingDataSMSHandler());

        // 启动disruptor
        disruptor.start();
        CountDownLatch latch = new CountDownLatch(1);
        executorService.submit(new InParkingDataEventPublisher(latch, disruptor));
        latch.await();
        // 关闭disruptor
        disruptor.shutdown();
        // 关闭线程池
        executorService.shutdown();
        System.out.println("Total cost: " + (System.currentTimeMillis() - begin) + " ms.");
    }
}
