package org.batch2.meituan.reduceSide;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @author:Gary
 * @date: 2022年11月10日 17:50
 * @desc:
 */
public class ReduceJoinSortApp {

    public static class ReduceJoinMapper extends Mapper<LongWritable, Text,Text,Text> {

        Text outKey = new Text();
        Text outvalue = new Text();

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] fields = value.toString().split("\t");
            outKey.set(fields[1]);
            outvalue.set(fields[1]+"\t"+fields[0]+"\t"+fields[3]+"\t"+fields[4]+"\t"+fields[2]);
            context.write(outKey,outvalue);
        }
    }

    public static class ReduceJoinReducer extends Reducer<Text,Text,Text, NullWritable> {
        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            for (Text value : values) {
                context.write(value,NullWritable.get());
            }
        }
    }

    public static void main(String[] args) throws Exception{

        Configuration conf = new Configuration();
        // 创建作业实例
        Job job = Job.getInstance(conf, ReduceJoinSortApp.class.getSimpleName());
        // 设置作业驱动类
        job.setJarByClass(ReduceJoinSortApp.class);

        // 设置作业mapper reducer类
        job.setMapperClass(ReduceJoinMapper.class);
        job.setReducerClass(ReduceJoinReducer.class);

        // 设置作业mapper阶段输出key value数据类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);

        //设置作业reducer阶段输出key value数据类型 也就是程序最终输出数据类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);

        // 配置作业的输入数据路径
        FileInputFormat.addInputPath(job, new Path("goods_output"));
        // 配置作业的输出数据路径
        FileOutputFormat.setOutputPath(job, new Path("goods_result"));

        // 提交作业并等待执行完成
        boolean b = job.waitForCompletion(true);

        System.exit(b ? 0 :1);
    }
}

