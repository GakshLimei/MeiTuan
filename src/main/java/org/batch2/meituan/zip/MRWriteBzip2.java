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
public class MRWriteBzip2 {

    public static void main(String[] args) throws InterruptedException, IOException, ClassNotFoundException {

        //用于管理当前程序的所有配置 如果conf没有设置fs.defaultFS，默认使用本地文件系统
        Configuration conf = new Configuration();
        //配置输出结果压缩为Bzip2或者Gzip格式
        //打开文件压缩
        conf.set("mapreduce.output.fileoutputformat.compress", "true");
        //设置压缩类型
        conf.set("mapreduce.output.fileoutputformat.compress.codec", "org.apache.hadoop.io.compress.BZip2Codec");


        //构建Job
        Job job = Job.getInstance(conf, MRWriteBzip2.class.getSimpleName());
        job.setJarByClass(MRWriteBzip2.class);

        //input：配置输入 指定要压缩文件的位置
        Path inputPath = new Path("input/seq_file_input");
        TextInputFormat.setInputPaths(job, inputPath);


        //map：配置Map
        job.setMapperClass(MrMapper.class);
        job.setMapOutputKeyClass(NullWritable.class);
        job.setMapOutputValueClass(Text.class);

        //reduce：配置Reduce
        job.setReducerClass(MrReduce.class);
        job.setOutputKeyClass(NullWritable.class);
        job.setOutputValueClass(Text.class);
        //output：配置输出 压缩文件的位置 输出路径的文件夹一等不能事先存在
        Path outputPath = new Path("output/meituan/zip_write");
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
