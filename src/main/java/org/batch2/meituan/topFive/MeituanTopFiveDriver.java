package org.batch2.meituan.topFive;

import org.batch2.meituan.bean.MeituanBean;
import org.batch2.meituan.topOne.MeituanGroupingComparator;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class MeituanTopFiveDriver {

    public static void main(String[] args) throws Exception {
        //配置文件
        Configuration conf = new Configuration();
        //创建作业实例
        Job job = Job.getInstance(conf, MeituanTopFiveDriver.class.getSimpleName());
        //设置作业驱动类
        job.setJarByClass(MeituanTopFiveDriver.class);

        //设置作业mapper reducer类
        job.setMapperClass(MeituanTopFiveMapper.class);
        job.setReducerClass(MeituanTopFiveReducer.class);
        //设置作业mapp二阶段输出key value数据类型
        job.setMapOutputKeyClass(MeituanBean.class);
        job.setMapOutputValueClass(LongWritable.class);
        //设置作业reducer阶段输出key value数据类型 也就是程序最终输出数据类型
        job.setOutputKeyClass(MeituanBean.class);
        job.setOutputValueClass(NullWritable.class);
        //todo 设置自定义分组
        job.setGroupingComparatorClass(MeituanGroupingComparator.class);

        //配置作业的输入数据路径
        FileInputFormat.addInputPath(job, new Path("input/meituan.csv"));
        //配置作业的输出数据路径
        FileOutputFormat.setOutputPath(job, new Path("output/meituan/topFive"));
        //判断输出路径是否存在 如果存在删除
        FileSystem fs = FileSystem.get(conf);
        if (fs.exists(new Path("output/meituan/topFive"))) {
            fs.delete(new Path("output/meituan/topFive"), true);

        }
        //提交作业并等待执行完成
        boolean resultFlag = job.waitForCompletion(true);
        //程序退出
        System.exit(resultFlag ? 0 : 1);

    }
}
