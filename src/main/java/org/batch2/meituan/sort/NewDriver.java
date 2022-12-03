package org.batch2.meituan.sort;

import org.batch2.meituan.bean.CovidCountBean3;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * 该类就是MapReduce程序客户端驱动类
 *     主要是构造Job对象实例
 *     指定各种组件属性：mapper、reducer类，输入输出的数据类型，输入输出的数据路径，提交job作业（job.submit()）
 */
public class NewDriver {

    public static void main(String[] args) throws Exception {

        //创建驱动类
        Configuration conf = new Configuration();

        //构造job作业的实例,参数（配置对象，job名字）
        Job job = Job.getInstance(conf, NewDriver.class.getSimpleName());

        //设置mr程序运行的主类
        job.setJarByClass(NewDriver.class);

        //设置本次mr程序的mapper类型、reducer类型
        job.setMapperClass(NewMapper.class);
        job.setReducerClass(NewReducer.class);

        //指定mapper阶段输出的key value数据类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(CovidCountBean3.class);

        //指定reducer阶段输出的key value数据类型，也是mr程序最终的输出数据类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NewMapper.class);

        //配置本次作业的输入数据路径和输出数据路径
        Path inputPath = new Path("input/new.csv");
        Path outputPath = new Path("output/meituan/sum");

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
