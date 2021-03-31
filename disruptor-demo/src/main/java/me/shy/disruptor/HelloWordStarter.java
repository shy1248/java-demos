package me.shy.disruptor;

import java.nio.ByteBuffer;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

/**
 * @Since: 2020/5/10 11:05
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: Hello Word for Disruptor
 *
 **/
public class HelloWordStarter {
    public static void main(String[] args) throws Exception {

        HelloWordStarter starter = new HelloWordStarter();

        // 1.配置并获取 disruptor
        // ExecutorService service = Executors.newCachedThreadPool();
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        // 2. 创建事件工厂
        LongEventFactory eventFactory = starter.new LongEventFactory();
        // 3. 设置 RingBuffer 大小, 需为2的N次方(能将求模运算转为位运算提高效率 ), 否则影响性能
        int bufferSize = 1024 * 2;

        // 4. 创建 disruptor, 泛型参数:传递的事件的类型
        // 第一个参数: 产生 Event 的工厂类, Event 封装生成-消费的数据
        // 第二个参数: RingBuffer 的缓冲区大小
        // 第三个参数: 线程池
        // 第四个参数: SINGLE 单个生产者, MULTI 多个生产者
        // 第五个参数: WaitStrategy 当消费者阻塞在 SequenceBarrier 上, 消费者如何等待的策略，有以下四种：
        //   BlockingWaitStrategy 使用锁和条件变量, 效率较低, 但CPU的消耗最小, 在不同部署环境下性能表现比较一致
        //   SleepingWaitStrategy 多次循环尝试不成功后, 让出CPU, 等待下次调度; 多次调度后仍不成功, 睡眠纳秒级别的时间再尝试. 平衡了延迟和CPU资源占用, 但延迟不均匀.
        //   YieldingWaitStrategy 多次循环尝试不成功后, 让出CPU, 等待下次调度. 平衡了延迟和CPU资源占用, 延迟也比较均匀.
        //   BusySpinWaitStrategy 自旋等待，类似自旋锁. 低延迟但同时对CPU资源的占用也多.

        Disruptor<LongEvent> disruptor =
            new Disruptor<LongEvent>(eventFactory, bufferSize, threadFactory, ProducerType.SINGLE,
                new YieldingWaitStrategy());

        // 5. 注册事件消费处理器，也即消费者。可传入多个消费者，还可以指定消费者的依赖关系
        disruptor.handleEventsWith(starter.new LongEventHandler());

        // 6. 启动 disruptor
        disruptor.start();

        // 7. 生产数据
        // 获取 disruptor 的 RingBuffer
        RingBuffer<LongEvent> buffer = disruptor.getRingBuffer();
        // 创建生产者，使用 RingBuffer 方式，原始 Api
        // LongEventProducer eventProducer = starter.new LongEventProducer(buffer);
        // 创建生产者，使用 Translator 方式， 新 Api
        LongEventProducerWithTranslator eventProducer = starter.new LongEventProducerWithTranslator(buffer);

        // 模拟数据，使用字节缓冲区保存 long 数字
        ByteBuffer bytes = ByteBuffer.allocate(8);
        for (int i = 0; i < 100; i++) {
            bytes.putLong(0, i);
            eventProducer.produce(bytes);
        }

        // 8. 关闭资源
        // 关闭 disruptor，阻塞直至所有事件都得到处理
        disruptor.shutdown();
        // 需关闭 disruptor 使用的线程池, 上一步 disruptor 关闭时不会连带关闭线程池
        // service.shutdown();
    }

    // Event 封装要传递的事件（数据）
    public class LongEvent {
        private Long value;

        public Long getValue() {
            return value;
        }

        public void setValue(Long value) {
            this.value = value;
        }
    }

    // 产生事件的工厂
    public class LongEventFactory implements EventFactory<LongEvent> {

        @Override public LongEvent newInstance() {
            return new LongEvent();
        }
    }

    // 消费者
    public class LongEventHandler implements EventHandler<LongEvent> {
        @Override public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {
            System.out.println("Feed long event: " + event.getValue());
        }
    }

    // 生产者，通过 RingBuffer 方式
    public class LongEventProducer {
        // 生产者持有 RingBuffer 的引用
        private RingBuffer<LongEvent> buffer;

        public LongEventProducer(RingBuffer<LongEvent> buffer) {
            this.buffer = buffer;
        }

        public void produce(ByteBuffer bytes) {
            // 获取下一个 Event 槽的下标
            long sequence = this.buffer.next();
            try {
                // 给 Event 填充数据
                LongEvent event = this.buffer.get(sequence);
                event.setValue(bytes.getLong(0));
            } finally {
                // 发布 Event，激活观察者去消费，将 sequence 传递给消费者
                // publish 应该放在 finally 中以确保一定会被调用
                // 如果某个事件槽被获取，但是却未被提交，将阻塞后续的 publish 动作
                this.buffer.publish(sequence);
            }
        }
    }

    // 生产者，通过新 API 实现
    public class LongEventProducerWithTranslator {
        // 使用 EventTranslator 封装获取 Event 的过程
        private final EventTranslatorOneArg<LongEvent, ByteBuffer> translatorOneArg =
            new EventTranslatorOneArg<LongEvent, ByteBuffer>() {
                @Override public void translateTo(LongEvent event, long sequence, ByteBuffer bytes) {
                    event.setValue(bytes.getLong(0));
                }
            };
        // 生产者持有 RingBuffer 的引用
        private RingBuffer<LongEvent> buffer;

        public LongEventProducerWithTranslator(RingBuffer<LongEvent> buffer) {
            this.buffer = buffer;
        }

        public void produce(ByteBuffer bytes) {
            // 发布 Event
            this.buffer.publishEvent(translatorOneArg, bytes);
        }
    }

}
