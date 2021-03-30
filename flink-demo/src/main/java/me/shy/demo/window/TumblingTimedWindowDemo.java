/**
 * @Date        : 2021-03-28 11:37:19
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : 基于处理时间的滚动窗口
 */
package me.shy.demo.window;

import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;

public class TumblingTimedWindowDemo {
    public static void main(String[] args) {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<String> dataSource = env.socketTextStream("localhost", 9999);
        dataSource.map(line -> {
            String[] feilds = line.split(",");
            return new CarInfo(feilds[0], Integer.parseInt(feilds[1]));
        }).returns(Types.POJO(CarInfo.class)).keyBy(carInfo -> carInfo.getSensorId())
                // TumblingProcessingTimeWindows:
                // 基于处理时间的滚动窗口，所谓的滚动窗口，即窗口的大小（时间长度或者消息数量）与要处理的时间一样，窗口无重叠
                // flink 的窗口触发机制为时间到达（或指定的消息数量到达）且有数据
                // 此处为每隔5秒钟处理5秒钟之内的数据
                .window(TumblingProcessingTimeWindows.of(Time.seconds(5))).sum("carCount").printToErr();
        try {
            env.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


