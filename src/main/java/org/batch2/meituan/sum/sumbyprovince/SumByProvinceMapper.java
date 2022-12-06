package org.batch2.meituan.sum.sumbyprovince;


import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.batch2.meituan.bean.SumBean;

import java.io.IOException;

/**
 * @author:Gary
 * @date: 2022年11月4日 14:03
 * @desc:
 */
public class SumByProvinceMapper extends Mapper<LongWritable, Text, Text, SumBean> {


    Text outKey = new Text();
    SumBean outValue = new SumBean();


    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        String[] fields = value.toString().split(",");//用 ,作为切割数据的分隔符，将切割好的数字变成字符串数组
        //["2020/1/21","Snohomish","Washington","1","0"]
        //  索引器起始值都是从0开始的


        String province = fields[5];
        outKey.set(province);
        //获得月销量 并转换为数字的long类型
        long monthSales = Long.parseLong(fields[11]);
        //获得评论数 并转换为数字的long类型
        long ratingAmounts = Long.parseLong(fields[16]);
        //获得最小价格数 并转换为double类型
        double minPrice = Double.parseDouble(fields[12]);
        //获得最配送费 并转换为double类型
        double deliveryFee = Double.parseDouble(fields[13]);
        outValue.set(monthSales, ratingAmounts, minPrice, deliveryFee);

        /**
         * 5、输出结果
         */
        context.write(outKey, outValue);


    }
}
