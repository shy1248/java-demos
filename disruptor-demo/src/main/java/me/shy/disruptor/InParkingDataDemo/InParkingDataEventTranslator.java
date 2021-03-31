package me.shy.disruptor.InParkingDataDemo;

import com.lmax.disruptor.EventTranslator;

/**
 * @Since: 2020/5/9 21:22
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 *
 **/
public class InParkingDataEventTranslator implements EventTranslator<InParkingDataEvent> {
    @Override public void translateTo(InParkingDataEvent event, long sequence) {
        this.generateTradeTransaction(event);
    }

    private InParkingDataEvent generateTradeTransaction(InParkingDataEvent event) {
        long threadId = Thread.currentThread().getId();
        int num = (int)(Math.random() * 8000) + 1000;
        String carLinence = "京Z" + num;
        event.setCarLinence(carLinence);
        System.out.println(String.format("[Thread-%s]: Producing a event %s.", threadId, carLinence));
        return event;
    }
}
