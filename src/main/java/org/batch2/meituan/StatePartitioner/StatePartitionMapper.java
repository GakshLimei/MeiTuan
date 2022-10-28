package org.batch2.meituan.mapper;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author:Yan
 * @date: 2022年08月12日 15:07
 * @desc:
 */
public class StatePartitionMapper extends Mapper<LongWritable, Text,Text,Text> {

    Text outKey = new Text();
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //1、切割，为了拿到州
        String[] lines = value.toString().split(",");

        //2、以州作为key，参与分区，通过自定义分区，同一个州的数据到同一个分区同一个reducetask处理
        String state = lines[5];

        //3、赋值
        outKey.set(state);

        //4、写出去
        context.write(outKey,value);


    }

}
