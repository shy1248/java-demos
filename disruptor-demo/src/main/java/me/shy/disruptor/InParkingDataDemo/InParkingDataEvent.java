package me.shy.disruptor.InParkingDataDemo;

/**
 * @Since: 2020/5/9 20:58
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: 定义事件（消息），此处模拟一辆车停入停车场
 *
 **/
public class InParkingDataEvent {
    private String carLinence;

    public String getCarLinence() {
        return carLinence;
    }

    public void setCarLinence(String carLinence) {
        this.carLinence = carLinence;
    }
}
