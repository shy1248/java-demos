package me.shy.demo.InParkingDataDemo;

import com.lmax.disruptor.EventHandler;

/**
 * @Since: 2020/5/9 21:05
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: 发送短信事件处理器（消费者）
 *
 **/
public class ParkingDataSMSHandler implements EventHandler<InParkingDataEvent> {

    @Override public void onEvent(InParkingDataEvent event, long sequence, boolean endOfBatch) throws Exception {
        long threadId = Thread.currentThread().getId();
        String carLinence = event.getCarLinence();
        System.out.println(String.format("[Thread-%s]: Send sms to user for %s.", threadId, carLinence));
    }
}
