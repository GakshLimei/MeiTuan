package org.batch2.meituan.sort;

import org.batch2.meituan.bean.CovidCountBean3;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class NewMapper extends Mapper<LongWritable, Text, Text, CovidCountBean3> {
int i=1;
    Text outKey = new Text();

        CovidCountBean3 outValue = new CovidCountBean3();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        String[] fields = value.toString().split(",");

        String state = fields[5];
        System.out.println(i + "----" + fields[12].trim());
        double Score = Double.parseDouble(fields[12]);
        double Comment_number =Double.parseDouble(fields[13]);
        outKey.set(state);
        outValue.set(Score,Comment_number);
i++;
        /**
         * 4、输出结果
         */
        context.write(outKey,outValue);
        }
}
