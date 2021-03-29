/**
 * @Date        : 2021-03-28 16:32:00
 * @Author      : shy
 * @Email       : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version     : v1.0
 * @Description : -
 */
package me.shy.demo.window;

import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class SlidingCountWindowDemo {
    public static void main(String[] args) {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<String> dataSource = env.socketTextStream("localhost", 9999);
        dataSource.map(line -> {
            String[] feilds = line.split(",");
            return new CarInfo(feilds[0], Integer.parseInt(feilds[1]));
        }).returns(Types.POJO(CarInfo.class)).keyBy(carInfo -> carInfo.getSensorId()).countWindow(5, 3).sum("carCount")
                .printToErr();
        try {
            env.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
