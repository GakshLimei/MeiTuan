package org.batch2.meituan.sort.minpricesort;


import org.batch2.meituan.bean.MinPriceBean;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author:Lawrence
 * @date: 2022年12月05日 14:46
 * @desc:
 则。
 */
public class MinPriceSortMapper extends Mapper<LongWritable, Text, MinPriceBean, Text> {

    /**
     * 3、创建输出对象
     */
    MinPriceBean outKey = new MinPriceBean();
    Text outValue = new Text();


    /**
     * 1、重写map父类方法：map回车
     */

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        /**
         * 2、读取一行数据进行切割
         */
        String[] fields = value.toString().split("\t");//以空格区分每个字符串
        String state = fields[0];
        double minPrice = Double.parseDouble(fields[3]);
        double Comment_number =Double.parseDouble(fields[4]);
        outKey.set(minPrice,Comment_number);
        outValue.set(state);

        /**
         * 5、输出结果
         */
context.write(outKey,outValue);
    }
}
