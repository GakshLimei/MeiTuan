package org.batch2.meituan.topFive;


import org.batch2.meituan.bean.MeituanBean;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class MeituanTopFiveMapper extends Mapper<LongWritable, Text, MeituanBean, LongWritable> {


    MeituanBean outKey = new MeituanBean();

    LongWritable outValue = new LongWritable();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] fields = value.toString().split(",");
        //
        //
        String city = fields[1];
        //
        String name = fields[2];
        //
        String month_sales = fields[3];

        outKey.set(city, name, Long.parseLong(month_sales));

        outValue.set(Long.parseLong(month_sales));

        //
        context.write(outKey, outValue);
    }
}
