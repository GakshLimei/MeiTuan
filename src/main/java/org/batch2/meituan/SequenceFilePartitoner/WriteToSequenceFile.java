package org.batch2.meituan.SequenceFilePartitoner;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;

import java.io.IOException;

public class WriteToSequenceFile {
    public static void main(String[] args) throws IOException,ClassNotFoundException,InterruptedException {
        //用于管理当前程序的所有配置
        Configuration conf = new Configuration();

        // 实例化作业
        Job job = Job.getInstance(conf, WriteToSequenceFile.class.getSimpleName());
        // 设置作业的主程序
        job.setJarByClass(WriteToSequenceFile.class);
        // 设置作业的输入为TextInputFormat（普通文本）
        job.setInputFormatClass(TextInputFormat.class);
        // 设置作业的输入路径
        TextInputFormat.addInputPath(job,new Path("output/meituan/partitoner"));
        // 设置Map端的实现类
        job.setMapperClass(WriteSeqFileMapper.class);
        // 设置Map端输入的Key类型
        job.setMapOutputKeyClass(NullWritable.class);
        // 设置Map端输入的Value类型
        job.setMapOutputValueClass(Text.class);
        // 设置作业的输出为SequenceFileOutputFormat
        job.setOutputFormatClass(SequenceFileOutputFormat.class);
        // 使用SequenceFile的块级别压缩
        SequenceFileOutputFormat.setOutputCompressionType(job,SequenceFile.CompressionType.BLOCK);
        // 设置Reduce端的实现类
        job.setReducerClass(WriteSeqFileReducer.class);
        // 设置Reduce端输出的Key类型
        job.setOutputKeyClass(NullWritable.class);
        // 设置Reduce端输出的Value类型
        job.setOutputValueClass(Text.class);
        // 从参数中获取输出路径
        Path outputDir = new Path("output/seq_partitoner");
        // 如果输出路径已存在则删除
        outputDir.getFileSystem(conf
        ).delete(outputDir, true);
        // 设置作业的输出路径
        FileOutputFormat.setOutputPath(job, outputDir);
        boolean resultFlag = job.waitForCompletion(true);
        //程序退出
        System.exit(resultFlag ? 0 :1);
    }
}
