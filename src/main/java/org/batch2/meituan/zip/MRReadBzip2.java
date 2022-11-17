package org.batch2.meituan.zip;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import java.io.IOException;

/**
 * @author:Gary
 * @date: 2022年11月13日 15:18
 * @desc:
 */
public class MRReadBzip2 {

    public static void main(String[] args) throws InterruptedException, IOException, ClassNotFoundException {

        //用于管理当前程序的所有配置
        Configuration conf = new Configuration();
        //构建Job
        Job job = Job.getInstance(conf, MRReadBzip2.class.getSimpleName());
        job.setJarByClass(MRReadBzip2.class);

        //input：配置输入
        Path inputPath = new Path("output/meituan/zip_write");
        TextInputFormat.setInputPaths(job, inputPath);

        //map：配置Map
        job.setMapperClass(MrMapper.class);
        job.setMapOutputKeyClass(NullWritable.class);
        job.setMapOutputValueClass(Text.class);

        //reduce：配置Reduce
        job.setReducerClass(MrReduce.class);
        job.setOutputKeyClass(NullWritable.class);
        job.setOutputValueClass(Text.class);
        //output：配置输出
        Path outputPath = new Path("output/meituan/zip_read");
        TextOutputFormat.setOutputPath(job, outputPath);

        boolean resultFlag = job.waitForCompletion(true);
        //程序退出
        System.exit(resultFlag ? 0 : 1);

    }

    /**
     * 定义Mapper类
     */
    public static class MrMapper extends Mapper<LongWritable, Text, NullWritable, Text> {

        private NullWritable outputKey = NullWritable.get();

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            //直接输出每条数据
            context.write(this.outputKey, value);
        }
    }

    /**
     * 定义Reduce类
     */
    public static class MrReduce extends Reducer<NullWritable, Text, NullWritable, Text> {

        @Override
        protected void reduce(NullWritable key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            //直接输出每条数据
            for (Text value : values) {
                context.write(key, value);
            }
        }
    }

}
