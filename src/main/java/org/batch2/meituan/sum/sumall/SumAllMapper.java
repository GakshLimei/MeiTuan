package org.batch2.meituan.sum.sumall;


import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.batch2.meituan.bean.SumBean;

import java.io.IOException;

/**
 * @author:Gary
 * @date: 2022年11月4日 14:03
 * @desc: 需求：统计每个州累计确诊病例。
 * 分析：
 * 自定义一个对象CovidCountBean，用于封装每个县的确诊病例数和死亡病例数。注意需要实现hadoop的序列化机制。
 * 以州state作为map阶段输出的key,以CovidCountBean作为value，
 * 这样经过MapReduce的默认排序分组规则，属于同一个州的数据就会变成一组进行reduce处理，
 * 进行累加即可得出每个州累计确诊病例。
 */
public class SumAllMapper extends Mapper<LongWritable, Text, Text, SumBean> {

    /**
     * 3、创建输出对象
     */
    Text outKey = new Text();
    SumBean outValue = new SumBean();

    /**
     * 1、重写map父类方法：map回车
     */
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        /**
         * 2、读取一行数据进行切割
         */
        String[] fields = value.toString().split(",");//用 ,作为切割数据的分隔符，将切割好的数字变成字符串数组
        //["2020/1/21","Snohomish","Washington","1","0"]
        //  索引器起始值都是从0开始的

        /**
         * 4、提取数据 州、确诊数、死亡数
         */
        String city = fields[6];//获得了 州 state = "Snohomish"
        outKey.set(city);
        //获得确诊数 并转换为数字的long类型
        long monthSales = Long.parseLong(fields[11]);
        //获得死亡数 并转换为数字的long类型
        long ratingAmounts = Long.parseLong(fields[16]);
        double minPrice = Double.parseDouble(fields[12]);
        double deliveryFee = Double.parseDouble(fields[13]);
        outValue.set(monthSales, ratingAmounts, minPrice, deliveryFee);

        /**
         * 5、输出结果
         */
        context.write(outKey, outValue);


    }
}
