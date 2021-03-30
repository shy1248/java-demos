/**
 * @Date        : 2021-03-28 17:35:42
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : -
 */
package me.shy.demo.window;

import org.apache.flink.api.common.RuntimeExecutionMode;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.ProcessingTimeSessionWindows;
import org.apache.flink.streaming.api.windowing.time.Time;

public class SessionWindowDemo {
    public static void main(String[] args) {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setRuntimeMode(RuntimeExecutionMode.AUTOMATIC);
        DataStreamSource<String> dataSource = env.socketTextStream("localhost", 9999);
        dataSource.map(line -> {
            String[] fields = line.split(",");
            return new CarInfo(fields[0], Integer.parseInt(fields[1]));
        }).returns(Types.POJO(CarInfo.class)).keyBy(carInfo -> carInfo.getSensorId())
                .window(ProcessingTimeSessionWindows.withGap(Time.seconds(30))).sum("carCount").printToErr();
        try {
            env.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
