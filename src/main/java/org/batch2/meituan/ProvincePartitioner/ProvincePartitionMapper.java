package org.batch2.meituan.ProvincePartitioner;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author:Yan
 * @date: 2022年08月12日 15:07
 * @desc:
 */
public class ProvincePartitionMapper extends Mapper<LongWritable, Text,Text,Text> {

    Text outKey = new Text();
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        String[] lines = value.toString().split(",");

        String state = lines[2];

        outKey.set(state);

        context.write(outKey,value);


    }

}
