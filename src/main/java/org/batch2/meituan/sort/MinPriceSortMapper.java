package org.batch2.meituan.sort;


import org.batch2.meituan.bean.CovidCountBean4;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author:Lawrence
 * @date: 2022年12月05日 14:46
 * @desc:
 *  需求：将每个州累计确诊病例，每个州state的确诊案例数进行倒序排序。
 * 分析：
 *  如果你的需求中需要根据某个属性进行排序 ，不妨把这个属性作为key。因为MapReduce中key有默认排序行为的。但是需要进行如下考虑：
 *  如果你的需求是正序，并且数据类型是Hadoop封装好的基本类型。这种情况下不需要任何修改，直接使用基本类型作为key即可。因为Hadoop封装好的类型已经实现了排序规则。
 */
public class MinPriceSortMapper extends Mapper<LongWritable, Text, CovidCountBean4, Text> {

    /**
     * 3、创建输出对象
     */
    CovidCountBean4 outKey = new CovidCountBean4();
    Text outValue = new Text();


    /**
     * 1、重写map父类方法：map回车
     */

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        /**
         * 2、读取一行数据进行切割
         */
        String[] fields = value.toString().split("\t");
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
