/**
 * @Date : 2021-03-29 14:08:42
 * @Author : shy
 * @Email : yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version : v1.0
 * @Description : 使用KeyState中的ValueState获取数据中的最大值(实际中直接使用maxBy即可)
 *
 */
package me.shy.demo.state;

import org.apache.flink.api.common.RuntimeExecutionMode;
import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class KeyedStateDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setRuntimeMode(RuntimeExecutionMode.AUTOMATIC);

        DataStreamSource<Tuple2<String, Long>> dataSource = env.fromElements(Tuple2.of("北京", 1L), Tuple2.of("上海", 2L),
            Tuple2.of("北京", 6L), Tuple2.of("上海", 8L), Tuple2.of("北京", 3L), Tuple2.of("上海", 4L));

        // api 自带方式
        //min只会求出最小的那个字段,其他的字段不管
        //minBy会求出最小的那个字段和对应的其他的字段
        //max只会求出最大的那个字段,其他的字段不管
        //maxBy会求出最大的那个字段和对应的其他的字段
        // dataSource.keyBy(t -> t.f1).maxBy(1).printToErr();

        // 使用 map 结合 ValueState 实现
        // IN: Tuple2<String,Long> ==> OUT: Tuple3<String,Long,Long>> ，其中 Tuple3 的第三个 field 为最大值
        dataSource.keyBy(t -> t.f0).map(new RichMapFunction<Tuple2<String, Long>, Tuple3<String, Long, Long>>() {
            private static final long serialVersionUID = 1L;
            // 声明一个 ValueState
            private ValueState<Long> maxValueState;

            // 获取 ValueState，因为只需要在开始时获取一次即可，所有在 open 方法中执行
            @Override
            public void open(Configuration parameters) throws Exception {
                // 定一个 ValueStateDescriptor
                ValueStateDescriptor<Long> stateDescriptor =
                    new ValueStateDescriptor<Long>("MaxValueState", Long.class);
                // 初始化 ValueState
                maxValueState = getRuntimeContext().getState(stateDescriptor);
            }

            @Override
            public Tuple3<String, Long, Long> map(Tuple2<String, Long> value) throws Exception {
                Long currentValue = value.f1;
                // 获取 ValueState 中的值
                Long historyMaxValue = maxValueState.value();
                if (null == historyMaxValue || historyMaxValue < currentValue) {
                    historyMaxValue = currentValue;
                    // 更新 ValueState 中的值
                    maxValueState.update(historyMaxValue);
                }
                return Tuple3.of(value.f0, currentValue, historyMaxValue);
            }

        }).printToErr();

        env.execute();
    }
}
