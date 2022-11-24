package org.batch2.meituan.topFive;


import org.batch2.meituan.bean.MeituanBean;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class MeituanTopFiveReducer extends Reducer<MeituanBean, LongWritable, MeituanBean, NullWritable> {

    @Override
    protected void reduce(MeituanBean key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {
/**
 * 在reduce端利用自定义分组规则，将市相同的分为一组，然后遍历取值，取出每组中的前5个即可。
 * */
        int num = 0;
        for (LongWritable value : values) {
            if (num < 5) {//输出每个市最多的前五个
                context.write(key, NullWritable.get());
                num++;
            } else {
                return;
            }
        }
    }
}
