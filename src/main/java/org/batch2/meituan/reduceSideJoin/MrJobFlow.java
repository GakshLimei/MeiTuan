package org.batch2.meituan.reduceSideJoin;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.jobcontrol.ControlledJob;
import org.apache.hadoop.mapreduce.lib.jobcontrol.JobControl;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @author:Gary
 * @date: 2022年11月10日 14:17
 * @desc: 当第一个MapReduce程序运行完成后，自动运行第二个MapReduce程序
 */
public class MrJobFlow {

    public static void main(String[] args) throws IOException {

        //配置文件对象
        Configuration conf = new Configuration();


        // 创建作业实例
        Job job1 = Job.getInstance(conf, ReduceJoinDriver.class.getSimpleName());
        // 设置作业驱动类
        job1.setJarByClass(ReduceJoinDriver.class);

        // 设置作业mapper reducer类
        job1.setMapperClass(ReduceJoinMapper.class);
        job1.setReducerClass(ReduceJoinReducer.class);

        // 设置作业mapper阶段输出key value数据类型
        job1.setOutputKeyClass(Text.class);
        job1.setOutputValueClass(Text.class);

        //设置作业reducer阶段输出key value数据类型 也就是程序最终输出数据类型
        job1.setOutputKeyClass(Text.class);
        job1.setOutputValueClass(Text.class);

        // 配置作业的输入数据路径
        FileInputFormat.addInputPath(job1, new Path("input/goods"));
        // 配置作业的输出数据路径
        FileOutputFormat.setOutputPath(job1, new Path("goods_output"));

        //将普通作业包装成受控作业
        ControlledJob ctrljob1 = new ControlledJob(conf);
        ctrljob1.setJob(job1);


        // 创建作业实例
        Job job2 = Job.getInstance(conf, ReduceJoinSortApp.class.getSimpleName());
        // 设置作业驱动类
        job2.setJarByClass(ReduceJoinSortApp.class);

        // 设置作业mapper reducer类
        job2.setMapperClass(ReduceJoinSortApp.ReduceJoinMapper.class);
        job2.setReducerClass(ReduceJoinSortApp.ReduceJoinReducer.class);

        // 设置作业mapper阶段输出key value数据类型
        job2.setMapOutputKeyClass(Text.class);
        job2.setMapOutputValueClass(Text.class);

        //设置作业reducer阶段输出key value数据类型 也就是程序最终输出数据类型
        job2.setOutputKeyClass(Text.class);
        job2.setOutputValueClass(NullWritable.class);

        // 配置作业的输入数据路径
        FileInputFormat.addInputPath(job2, new Path("goods_output"));
        // 配置作业的输出数据路径
        FileOutputFormat.setOutputPath(job2, new Path("goods_result"));

        //将普通作业包装成受控作业
        ControlledJob ctrljob2 = new ControlledJob(conf);
        ctrljob2.setJob(job2);


        //设置依赖job的依赖关系
        ctrljob2.addDependingJob(ctrljob1);

        // 主控制容器，控制上面的总的两个子作业
        JobControl jobCtrl = new JobControl("myctrl");

        // 添加到总的JobControl里，进行控制
        jobCtrl.addJob(ctrljob1);
        jobCtrl.addJob(ctrljob2);

        // 在线程启动，记住一定要有这个
        Thread t = new Thread(jobCtrl);
        t.start();

        while(true) {
            if (jobCtrl.allFinished()) {// 如果作业成功完成，就打印成功作业的信息
                System.out.println(jobCtrl.getSuccessfulJobList());
                jobCtrl.stop();
                break;
            }
        }




    }
}
