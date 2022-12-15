package org.batch2.meituan.commentSortByCity;


import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.batch2.meituan.bean.CommentSortByCityBean;

import java.io.IOException;

/**
 * @author:Gary
 * @date: 2022年09月25日 14:46
 * @desc:
 */
public class CommentSortByCityMapper extends Mapper<LongWritable, Text, CommentSortByCityBean, Text> {

    /**
     * 创建输出对象
     */
    CommentSortByCityBean outKey = new CommentSortByCityBean();
    Text outValue = new Text();


    /**
     * 重写map父类方法：map回车
     */

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        /**
         * 读取一行数据进行切割
         */
        String[] fields = value.toString().split("\t");
        //Alabama	319025	11654
        //["Alabama","319025","11654"]
        /**
         * 提取数据 州、确诊数、死亡数
         * outKey 是key 里面需要传递的是 确诊数 和 死亡数
         * outvalue 是 value 里面需要传的是 州
         */
        String state = fields[0];
        long cases = Long.parseLong(fields[1]);
        long comments = Long.parseLong(fields[2]);
        outKey.set(cases, comments);


        outValue.set(state);
        /**
         * 输出结果
         */
        context.write(outKey, outValue);

    }
}
