package me.shy.demo.InParkingDataDemo;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

/**
 * @Since: 2020/5/9 21:00
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: 插入Database事件处理器（消费者）
 *
 **/
public class ParkingDataInDBHandler implements EventHandler<InParkingDataEvent>, WorkHandler<InParkingDataEvent> {
    @Override public void onEvent(InParkingDataEvent event, long sequence, boolean endOfBatch) throws Exception {
        long threadId = Thread.currentThread().getId();
        String carLinence = event.getCarLinence();
        System.out.println(String.format("[Thread-%s]: Saved %s into database.", threadId, carLinence));
    }

    @Override public void onEvent(InParkingDataEvent event) throws Exception {
        this.onEvent(event);
    }
}
