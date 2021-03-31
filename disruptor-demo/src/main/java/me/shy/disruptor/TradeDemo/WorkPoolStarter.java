package me.shy.disruptor.TradeDemo;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.IgnoreExceptionHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SequenceBarrier;
import com.lmax.disruptor.WorkerPool;
import com.lmax.disruptor.YieldingWaitStrategy;

/**
 * @Since: 2020/5/10 16:09
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: 直接使用RingBuffer，使用 WorkPool
 *
 **/
public class WorkPoolStarter {
    public static void main(String[] args) {
        // RingBuffer 大小
        int ringBufferSize = 1024 * 2;
        // 线程池容量
        int threadNumber = 4;
        // 订单数量
        int tradeNumber = 100;

        // 这里直接获得RingBuffer. createSingleProducer创建一个单生产者的RingBuffer
        // 第一个参数为EventFactory，产生数据Trade的工厂类
        // 第二个参数是RingBuffer的大小，需为2的N次方
        // 第三个参数是WaitStrategy, 消费者阻塞时如何等待生产者放入Event
        RingBuffer<Trade> ringBuffer = RingBuffer.createSingleProducer(new EventFactory<Trade>() {
            @Override public Trade newInstance() {
                return new Trade(UUID.randomUUID().toString());
            }
        }, ringBufferSize, new YieldingWaitStrategy());

        // SequenceBarrier, 协调消费者与生产者, 消费者链的先后顺序. 阻塞后面的消费者(没有Event可消费时)
        SequenceBarrier sequenceBarrier = ringBuffer.newBarrier();

        // 创建 WorkPool 并启动
        // 第三个参数: 异常处理器, 这里用ExceptionHandler; 第四个参数WorkHandler的实现类, 可为数组(即传入多个消费者)
        WorkerPool<Trade> tradeWorkerPool =
            new WorkerPool<>(ringBuffer, sequenceBarrier, new IgnoreExceptionHandler(), new TradeHandler());
        ExecutorService service = Executors.newFixedThreadPool(threadNumber);
        tradeWorkerPool.start(service);

        // 生产数据
        String[] names = {"Tom", "Jerry", "Mickey", "Tyler", "Joiny"};
        for (int i=0;i<tradeNumber;i++){
            long sequence = ringBuffer.next();
            Trade trade = ringBuffer.get(sequence);
            trade.setName(names[new Random().nextInt(names.length)]);
            trade.setPrice(Math.random() * 9999);
            ringBuffer.publish(sequence);
            System.out.println("New trade: " + trade);
        }

        tradeWorkerPool.halt();
        service.shutdown();
    }
}
