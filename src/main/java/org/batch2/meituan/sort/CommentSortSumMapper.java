package org.batch2.meituan.sort;


import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.batch2.meituan.bean.CommentCountBean2;

import java.io.IOException;

/**
 * @author:Gary
 * @date: 2022年11月2日 14:46
 * @desc: 需求：将每个省份累计销售量，每个州的销售量进行倒序排序。
 * 分析：
 * 如果你的需求中需要根据某个属性进行排序 ，不妨把这个属性作为key。因为MapReduce中key有默认排序行为的。但是需要进行如下考虑：
 * 如果你的需求是正序，并且数据类型是Hadoop封装好的基本类型。这种情况下不需要任何修改，直接使用基本类型作为key即可。
 * 因为Hadoop封装好的类型已经实现了排序规则。
 */
public class CommentSortSumMapper extends Mapper<LongWritable, Text, CommentCountBean2, Text> {

    /**
     * 3、创建输出对象
     */
    CommentCountBean2 outKey = new CommentCountBean2();
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
        //Alabama	319025	11654
        //["Alabama","319025","11654"]
        /**
         * 4、提取数据 州、确诊数、死亡数
         * outKey 是key 里面需要传递的是 确诊数 和 死亡数
         * outvalue 是 value 里面需要传的是 州
         */
        String state = fields[0];
        long cases = Long.parseLong(fields[1]);
        long comments = Long.parseLong(fields[2]);
        outKey.set(cases, comments);


        outValue.set(state);
        /**
         * 5、输出结果
         */
        context.write(outKey, outValue);

    }
}
