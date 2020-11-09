package me.shy.demo.InParkingDataDemo;

import com.lmax.disruptor.dsl.Disruptor;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @Since: 2020/5/9 21:30
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 *
 **/
public class InParkingDataEventPublisher implements Runnable {
    // 模拟1000辆车入场
    private static int LOOP = 10;
    private Disruptor<InParkingDataEvent> disruptor;
    private CountDownLatch latch;

    public InParkingDataEventPublisher(CountDownLatch latch, Disruptor<InParkingDataEvent> disruptor) {
        this.disruptor = disruptor;
        this.latch = latch;
    }

    @Override public void run() {
        InParkingDataEventTranslator translator = new InParkingDataEventTranslator();
        for (int i = 0; i < LOOP; i++) {
            disruptor.publishEvent(translator);
            try{
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        latch.countDown();
        System.out.println("Producer writed " + LOOP + " events.");
    }
}
