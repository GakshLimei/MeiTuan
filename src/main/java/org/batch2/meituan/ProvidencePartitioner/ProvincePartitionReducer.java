package org.batch2.meituan.ProvidencePartitioner;

/**
 * @author:Yan
 * @date: 2022年08月12日 15:10
 * @desc:
 */

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * 因为state在那一行数据里，再输出那行数据没有意义，可以使用nullWritable
 */
public class ProvincePartitionReducer extends Reducer<Text, Text,Text, NullWritable> {

    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        for (Text value : values){
            context.write(value,NullWritable.get());
        }
    }
}
