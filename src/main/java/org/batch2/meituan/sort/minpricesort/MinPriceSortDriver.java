package org.batch2.meituan.sort.minpricesort;


import org.batch2.meituan.bean.CovidCountBean4;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * @author:Lawrence
 * @date: 2022年12月05日 14:50
 * @desc:
 */
public class MinPriceSortDriver {

    public static void main(String[] args) throws Exception {

        //创建驱动类
        Configuration conf = new Configuration();

        //构造job作业的实例,参数（配置对象，job名字）
        Job job = Job.getInstance(conf, MinPriceSortDriver.class.getSimpleName());

        //设置mr程序运行的主类
        job.setJarByClass(MinPriceSortDriver.class);

        //设置本次mr程序的mapper类型、reducer类型
        job.setMapperClass(MinPriceSortMapper.class);
        job.setReducerClass(MinPriceSortReducer.class);

        //指定mapper阶段输出的key value数据类型
        job.setMapOutputKeyClass(CovidCountBean4.class);
        job.setMapOutputValueClass(Text.class);
        //指定reducer阶段输出的key value数据类型，也是mr程序最终的输出数据类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(CovidCountBean4.class);

        //配置本次作业的输入数据路径和输出数据路径
        Path inputPath = new Path("output/meituan/sum");
        Path outputPath = new Path("output/meituan/sort");

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
