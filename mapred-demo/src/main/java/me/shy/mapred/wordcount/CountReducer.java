/**
 * @Since: 2019-05-27 17:36:46
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Licence: GPLv3
 * @Description: -
 * @LastTime: 2019-05-27 22:25:37
 */
package me.shy.mapred.wordcount;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * 单词计数Reducer类：
 * Reducer接收Mapper输出的中间结果并执行reduce函数，reduce接收到的参数形如：<key,List<value>>，
 * 这是因为map函数将key值相同的所有value都发送给reduce函数。在reduce函数中，完成对相同key值（同一
 * 单词）的计数并将最后结果输出。Reducer类的泛型带包了reduce函数输入键值对的键的类、值的类，输出键 值对的键的类、值的类
 **/
public class CountReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

    private IntWritable result = new IntWritable();

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values,
            Reducer<Text, IntWritable, Text, IntWritable>.Context context) throws IOException, InterruptedException {
        int sum = 0;
        for (IntWritable value : values) {
            sum += value.get();
        }
        result.set(sum);
        context.write(key, result);
    }

}
