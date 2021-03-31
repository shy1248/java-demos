/**
 * @Since: 2019-05-27 17:05:34
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Licence: GPLv3
 * @Description: -
 * @LastTime: 2019-05-27 18:05:29
 */
package me.shy.mapred.wordcount;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/** 单词计数Mapper类： **/

public class TokenizerMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    // Mapper类的四个泛型分别表示map函数输入键值对的键的类、值的类、输出键值对的键的类、值的类
    private final static IntWritable ONE = new IntWritable(1);
    private Text word = new Text();

    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, IntWritable>.Context context)
            throws IOException, InterruptedException {
        // key:键，代表行号;value：值，代表该行的内容
        StringTokenizer tokenizer = new StringTokenizer(value.toString());
        while (tokenizer.hasMoreTokens()) {
            // nextToken()将每行文本拆分成单个单词
            word.set(tokenizer.nextToken());
            // write()输出中间结果
            context.write(word, ONE);
        }
    }
}
