package org.batch2.meituan.sort;


import org.batch2.meituan.bean.CovidCountBean4;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author:Lawrence
 * @date: 2022年12月05日 14:48
 * @desc:
 */
public class NewSortReducer extends Reducer<CovidCountBean4, Text,Text,CovidCountBean4> {

    @Override
    protected void reduce(CovidCountBean4 key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        /**
         * 排序好之后 reduce会进行分组操作，分组规则是判断key是否相等
         * 本业务中，使用自定义对象作为key，并且没有重写分组规则，默认就会比较对象的地址，导致每个kv队就是一组
         */
Text state_name = values.iterator().next();

        CovidCountBean4 value = key;
context.write(state_name,value);


    }
}
