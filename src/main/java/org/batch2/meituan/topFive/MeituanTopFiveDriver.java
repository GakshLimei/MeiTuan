package org.batch2.meituan.topFive;

import org.batch2.meituan.bean.MeituanBean;
import org.batch2.meituan.topN.MeituanGroupingComparator;
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
        //
        Configuration conf = new Configuration();
        //
        Job job = Job.getInstance(conf, MeituanTopFiveDriver.class.getSimpleName());
        //
        job.setJarByClass(MeituanTopFiveDriver.class);

        //
        job.setMapperClass(MeituanTopFiveMapper.class);
        job.setReducerClass(MeituanTopFiveReducer.class);
        //
        job.setMapOutputKeyClass(MeituanBean.class);
        job.setMapOutputValueClass(LongWritable.class);
        //
        job.setOutputKeyClass(MeituanBean.class);
        job.setOutputValueClass(NullWritable.class);
        //
        job.setGroupingComparatorClass(MeituanGroupingComparator.class);

        //
        FileInputFormat.addInputPath(job, new Path("input/meituan.csv"));
        //
        FileOutputFormat.setOutputPath(job, new Path("output/meituan/topFive"));
        //
        FileSystem fs = FileSystem.get(conf);
        if (fs.exists(new Path("output/meituan/topFive"))) {
            fs.delete(new Path("output/meituan/topFive"), true);

        }
        //
        boolean resultFlag = job.waitForCompletion(true);
        //
        System.exit(resultFlag ? 0 : 1);

    }
}
