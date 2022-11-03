package org.batch2.meituan.sort;


import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.batch2.meituan.bean.CommentCountBean2;

import java.io.IOException;

/**
 * @author:Gary
 * @date: 2022年11月2日 14:48
 * @desc:
 */
public class CommentSortSumReducer extends Reducer<CommentCountBean2, Text, Text, CommentCountBean2> {

    @Override
    protected void reduce(CommentCountBean2 key, Iterable<Text> values, Context context) throws IOException,
            InterruptedException {
        /**
         * 排序好之后 reduce会进行分组操作，分组规则是判断key是否相等
         * 本业务中，使用自定义对象作为key，并且没有重写分组规则，默认就会比较对象的地址，导致每个kv队就是一组
         * <CommentCountBean2,加州> --------------><CommentCountBean2,Iterable[加州]>
         * <CommentCountBean2,德州> --------------><CommentCountBean2,Iterable[德州]>
         */

        Text outValues = values.iterator().next(); //每执行一次reduce就会执行迭代器的next(); 拿到 州 名
        CommentCountBean2 value = key;
        context.write(outValues, key);//key:Alabama value(319025 1165)

    }
}