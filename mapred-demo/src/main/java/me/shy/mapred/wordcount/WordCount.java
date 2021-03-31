/**
 * @Since: 2019-05-27 17:44:29
 * @Author: shy
 * @Email: yushuibo@ebupt.com / hengchen2005@gmail.com
 * @Version: v1.0
 * @Licence: GPLv3
 * @Description: -
 * @LastTime: 2019-05-28 14:10:05
 */
package me.shy.mapred.wordcount;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.Job;

// import org.apache.hadoop.hdfs.server.namenode.NameNode;
// import org.apache.hadoop.hdfs.server.datanode.DataNode;
// import org.apache.hadoop.hdfs.DFSClient;
// import org.apache.hadoop.hdfs.net.TcpPeerServer;
// import org.apache.commons.cli.HelpFormatter;
// import org.apache.hadoop.ha.HAAdmin;


/**
 * main函数： 对作业进行相关配置以及向Hadoop提交作业
 **/
public class WordCount {
    public static void main(String[] args) {
        // if (args.length != 2) {
        // System.out.println("Usage: wordcount <input_file> <output_file>");
        // System.exit(-1);
        // }

        // Configuration类包含了对Hadoop的配置，代表了作业的配置
        // 该类会加载mapred-site.xml、hdfs-site.xml、core-site.xml、yarn-site.xml
        Configuration conf = new Configuration();
        try {
            Job job = Job.getInstance(conf);
            job.setJobName("WordCount");
            // 指定main函数所在类
            job.setJarByClass(WordCount.class);
            // 指定Mapper类
            job.setMapperClass(TokenizerMapper.class);
            // 指定Reducer类
            job.setReducerClass(CountReducer.class);
            // 指定Reducer输出的key类型
            job.setOutputKeyClass(Text.class);
            // 指定Reducer输出的value类型
            job.setOutputValueClass(IntWritable.class);
            // 指定输入和输出路径
            // FileInputFormat.addInputPath(conf, new Path(args[0]));
            // FileOutputFormat.addOutputPath(conf, new Path(args[1]));
            FileInputFormat.addInputPath(job,
                    new Path(WordCount.class.getClassLoader().getResource("wordcount.txt").toString()));
            FileOutputFormat.setOutputPath(job,
                    new Path(WordCount.class.getClassLoader().getResource("out.txt").toString()));
            // 提交Job并等待job运行结束
            System.exit(job.waitForCompletion(true) ? 0 : 1);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
