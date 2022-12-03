package org.batch2.meituan.topOne;

import org.batch2.meituan.bean.MeituanBean;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;


public class MeituanTopOneReducer extends Reducer<MeituanBean, LongWritable, MeituanBean, LongWritable> {


    @Override
    protected void reduce(MeituanBean key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {

        //不遍历迭代器，此时key就是分组中的第一个key，也就是该市确定月销售量最多对应的数据
        context.write(key, values.iterator().next());
    }
}
