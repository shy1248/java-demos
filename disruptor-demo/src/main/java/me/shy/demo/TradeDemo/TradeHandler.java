package me.shy.demo.TradeDemo;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.WorkerPool;

/**
 * @Since: 2020/5/10 15:47
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: 消费者, 这里实现一个接口就行, 写两个是为了同时测试 EventProcessor 和 WorkPool
 *
 **/
public class TradeHandler implements EventHandler<Trade>, WorkHandler<Trade> {
    @Override public void onEvent(Trade event, long sequence, boolean endOfBatch) throws Exception {
        this.onEvent(event);
    }

    @Override public void onEvent(Trade event) throws Exception {
        // 具体的消费逻辑
        System.out.println("Handing trade: " + event);
    }
}
