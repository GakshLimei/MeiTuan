package org.batch2.meituan.sum;


import org.batch2.meituan.bean.CommentCountBean;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;


import java.io.IOException;

/**
 * @author:Gary
 * @date: 2022年11月4日 14:07
 * @desc:
 */
public class CommentSumReducer extends Reducer<Text, CommentCountBean, Text, CommentCountBean> {
    CommentCountBean outValue = new CommentCountBean();

    @Override
    protected void reduce(Text key, Iterable<CommentCountBean> values, Context context) throws IOException, InterruptedException {
        /**
         * 创建统计变量
         */
        long totalCases = 0;//累计出售
        long totalComments = 0;//累计评论数

        /**
         * 遍历该州的各个县的数据
         */
        for (CommentCountBean value : values) {

            totalCases += value.getCases();
            totalComments += value.getComments();
        }

        /**
         * 输出结果赋值
         */
        outValue.set(totalCases, totalComments);
        context.write(key, outValue);


    }
}
