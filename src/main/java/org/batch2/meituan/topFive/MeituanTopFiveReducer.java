package org.batch2.meituan.topFive;


import org.batch2.meituan.bean.MeituanBean;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class MeituanTopFiveReducer extends Reducer<MeituanBean, LongWritable, MeituanBean, NullWritable> {

    @Override
    protected void reduce(MeituanBean key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {

        int num = 0;
        for (LongWritable value : values) {
            if (num < 5) {//
                context.write(key, NullWritable.get());
                num++;
            } else {
                return;
            }
        }
    }
}
