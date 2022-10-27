package org.batch2.MeiTuan.driver;


import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;

/**
 * @author:Yan
 * @date: 2022年08月12日 15:14
 * @desc:
 */
public class StatePartitionDriver {

    public static void main(String[] args) throws Exception {

        //创建驱动类
        Configuration conf = new Configuration();

        //构造job作业的实例,参数（配置对象，job名字）
        Job job = Job.getInstance(conf, StatePartitionDriver.class.getSimpleName());

        //设置mr程序运行的主类
        job.setJarByClass(StatePartitionDriver.class);

        //设置本次mr程序的mapper类型、reducer类型
        job.setMapperClass(org.batch2.MeiTuan.mapper.StatePartitionMapper.class);
        job.setReducerClass(org.batch2.MeiTuan.reduce.StatePartitionReducer.class);

        //指定mapper阶段输出的key value数据类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);

        //指定reducer阶段输出的key value数据类型，也是mr程序最终的输出数据类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);

        //todo 设置程序Partition类; 注意：分区组件能够生效的前提是MapReduce程序中的reduceTask的个数 >=2
        /**
         * 探究： reducetask个数和分区个数之间的关系
         * 正常情况下： reducetask个数 == 分区个数
         * 特殊情况下： reducetask个数 > 分区个数  ====> 程序可以运行，但多有空文件产生，浪费性能
         *            reducetask个数 < 分区个数  ====> 程序直接报错：非法分区
         */
        job.setPartitionerClass(org.batch2.MeiTuan.Been.StatePartitioner.class);
        job.setNumReduceTasks(6);// 5 是已经在StatePartitioner已经分好的区 1：其他州的数据  5 + 1 = 6


        //配置本次作业的输入数据路径和输出数据路径
        Path inputPath = new Path("input/eleme_shops_shenzhen_20220913_sample (1).csv");
        Path outputPath = new Path("output/covid/meituanpartitoner");

        //todo 默认组件 TextInputFormat TextOutputFormat
        FileInputFormat.setInputPaths(job, inputPath);
        FileOutputFormat.setOutputPath(job,outputPath);

        //todo 判断输出路径是否已经存在，如果已经存在，先删除
        FileSystem fs = FileSystem.get(conf);
        if(fs.exists(outputPath)){
            fs.delete(outputPath,true); //递归删除
        }

        //最终提交本次job作业
        //job.submit();
        //采用waitForCompletion提交job，参数表示是否开启实时监视追踪作业的执行情况
        boolean resultFlag = job.waitForCompletion(true);
        //退出程序 和job结果进行绑定, 0是正常退出，1是异常退出
        System.exit(resultFlag ? 0: 1);

    }

}
