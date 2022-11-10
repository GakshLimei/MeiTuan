package org.batch2.meituan.reduceSide;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @author:Gary
 * @date: 2022年11月10日 17:46
 * @desc:
 */
public class ReduceJoinDriver {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        //配置文件对象
        Configuration conf = new Configuration();

        // 创建作业实例
        Job job = Job.getInstance(conf, ReduceJoinDriver.class.getSimpleName());
        // 设置作业驱动类
        job.setJarByClass(ReduceJoinDriver.class);

        // 设置作业mapper reducer类
        job.setMapperClass(ReduceJoinMapper.class);
        job.setReducerClass(ReduceJoinReducer.class);

        // 设置作业mapper阶段输出key value数据类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        //设置作业reducer阶段输出key value数据类型 也就是程序最终输出数据类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        // 配置作业的输入数据路径
        FileInputFormat.addInputPath(job, new Path("input/join"));
        // 配置作业的输出数据路径
        FileOutputFormat.setOutputPath(job, new Path("output/meituan/join"));

        boolean resultFlag = job.waitForCompletion(true);
        //程序退出
        System.exit(resultFlag ? 0 :1);

    }
}
