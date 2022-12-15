package org.batch2.meituan.sort.commentSortByCity;


import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.batch2.meituan.bean.CommentSortByCityBean;

import java.io.IOException;

/**
 * @author:Gary
 * @date: 2022年09月25日 14:48
 * @desc:
 */
public class CommentSortByCityReducer extends Reducer<CommentSortByCityBean, Text, Text, CommentSortByCityBean> {

    @Override
    protected void reduce(CommentSortByCityBean key, Iterable<Text> values, Context context) throws IOException,
            InterruptedException {
        /**
         * 排序好之后 reduce会进行分组操作，分组规则是判断key是否相等
         * 本业务中，使用自定义对象作为key，并且没有重写分组规则，默认就会比较对象的地址，导致每个kv队就是一组
         */

        Text outValues = values.iterator().next(); //每执行一次reduce就会执行迭代器的next(); 拿到 州 名
        CommentSortByCityBean value = key;
        context.write(outValues, key);//key:Alabama value(319025 1165)

    }
}