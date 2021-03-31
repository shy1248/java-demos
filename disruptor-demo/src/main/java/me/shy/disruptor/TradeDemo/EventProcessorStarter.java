package me.shy.disruptor.TradeDemo;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lmax.disruptor.BatchEventProcessor;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SequenceBarrier;
import com.lmax.disruptor.YieldingWaitStrategy;

/**
 * @Since: 2020/5/10 15:37
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: 直接使用RingBuffer，使用 EventProcessor
 *
 **/
public class EventProcessorStarter {
    public static void main(String[] args) throws InterruptedException {
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

        //创建事件处理器 (消费者): 处理ringBuffer, 用TradeHandler的方法处理(实现EventHandler), 用sequenceBarrier协调生成-消费
        //如果存在多个消费者(老api, 可用workpool解决) 那重复执行，创建事件处理器-注册进度-提交消费者的过程, 把其中TradeHandler换成其它消费者类
        BatchEventProcessor<Trade> tradeBatchEventProcessor =
            new BatchEventProcessor<>(ringBuffer, sequenceBarrier, new TradeHandler());

        // 把消费者的消费进度情况注册给RingBuffer结构(生产者)
        // 如果只有一个消费者的情况可以省略
        ringBuffer.addGatingSequences(tradeBatchEventProcessor.getSequence());

        // 创建线程池
        ExecutorService service = Executors.newFixedThreadPool(threadNumber);
        // 把消费者提交到线程池, EventProcessor实现了callable接口
        service.submit(tradeBatchEventProcessor);

        // 阻塞生产线程
        CountDownLatch latch = new CountDownLatch(tradeNumber);
        // 生产者，这里的线程不是必要
        service.submit(new Callable<Void>() {
            @Override public Void call() throws Exception {
                long sequence;
                String[] names = {"Tom", "Jerry", "Mickey", "Tyler", "Joiny"};
                for (int i = 0; i < tradeNumber; i++) {
                    sequence = ringBuffer.next();
                    Trade trade = ringBuffer.get(sequence);
                    trade.setName(names[new Random().nextInt(names.length)]);
                    trade.setPrice(Math.random() * 9999);
                    ringBuffer.publish(sequence);
                    System.out.println("New trade: " + trade);
                    latch.countDown();
                }
                return null;
            }
        });

        // 等待生产者结束
        latch.await();
        // 通知事件处理器  可以结束了（并不是马上结束!）
        tradeBatchEventProcessor.halt();
        // 关闭线程池
        service.shutdown();
    }
}
