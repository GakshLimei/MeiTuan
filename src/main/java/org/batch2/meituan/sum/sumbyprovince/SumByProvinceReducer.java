package org.batch2.meituan.sum.sumbyprovince;


import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.batch2.meituan.bean.SumBean;

import java.io.IOException;

/**
 * @author:Gary
 * @date: 2022年11月4日 14:07
 * @desc:
 */
public class SumByProvinceReducer extends Reducer<Text, SumBean, Text, SumBean> {
    SumBean outValue = new SumBean();

    @Override
    protected void reduce(Text key, Iterable<SumBean> values, Context context) throws IOException, InterruptedException {

        long totalMonthSales = 0;//累计月销量
        long totalRatingAmounts = 0;//累计评论数
        double totalMinPrice = 0;//累计最小价格
        double totalDeliveryFee = 0;//累计配送费


        for (SumBean value : values) {

            totalMonthSales += value.getMonthSales();
            totalRatingAmounts += value.getRatingAmounts();
            totalMinPrice += value.getMinPrice();
            totalDeliveryFee += value.getDeliveryFee();
        }

        /**
         * 输出结果赋值
         */
        outValue.set(totalMonthSales, totalRatingAmounts, Math.round(totalMinPrice), Math.round(totalDeliveryFee));
        context.write(key, outValue);


    }
}
