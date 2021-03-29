package me.shy.demo.wordCount;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

/**
 * @Since: 2019-12-21 21:34:33
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Description: -
 */

import lombok.Data;

public class SocketWordCount {

    public static void main(String[] args) throws Exception {
        String hostname = "localhost";
        int port = 9000;

        try {
            ParameterTool parameters = ParameterTool.fromArgs(args);
            hostname = parameters.get("host");
            if (null == hostname) {
                System.out.println("None \"host\" spectified, set to localhost.");
            }
            try {
                port = parameters.getInt("port");
            } catch (Exception e) {
                System.out.println("None \"port\" spectified, set to 9000 as default.");
            }

        } catch (Exception e) {
            System.err.println("An error occourd during parse args.");
            e.printStackTrace();
        }

        String delimiter = "\n";
        Long maxRetry = 1L;

        // get flink enviroment
        StreamExecutionEnvironment enviroment =
                StreamExecutionEnvironment.getExecutionEnvironment();
        // listen data source
        DataStreamSource<String> lines =
                enviroment.socketTextStream(hostname, port, delimiter, maxRetry);
        // do operator
        DataStream<WordCount> wordCounts = lines.flatMap(new FlatMapFunction<String, String>() {
            private static final long serialVersionUID = 1L;

            @Override
            public void flatMap(String value, Collector<String> out) throws Exception {
                for (String word : value.split("\\s+")) {
                    out.collect(word);
                }
            }
        }).map(new MapFunction<String, WordCount>() {
            private static final long serialVersionUID = 1L;

            @Override
            public WordCount map(String value) throws Exception {
                return new WordCount(value, 1L);
            }
        }).keyBy("word").timeWindow(Time.seconds(5), Time.seconds(1)).sum("count");
        // data sink, output to console
        wordCounts.print();
        // start execute
        enviroment.execute();
    }

    @Data
    public static class WordCount {

        private String word;
        private Long count;

        public WordCount() {
        }

        public WordCount(String word, Long count) {
            this.word = word;
            this.count = count;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("WordCount[");
            sb.append(this.word);
            sb.append(",");
            sb.append(this.count);
            sb.append("]");
            return sb.toString();
        }
    }
}
