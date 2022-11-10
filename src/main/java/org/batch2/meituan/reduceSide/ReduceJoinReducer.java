package org.batch2.meituan.reduceSide;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author:Gary
 * @date: 2022年11月10日 17:45
 * @desc:
 */
public class ReduceJoinReducer extends Reducer<Text,Text,Text,Text> {
    //用来存放  商品编号、商品名称
    List<String> goodsList = new ArrayList<>();
    //用来存放  订单编号、实际支付价格
    List<String> orderList = new ArrayList<>();

    Text outValue = new Text();

    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException{
        //遍历values
        for (Text value : values) {
            //将结果添加到对应的list中
            if(value.toString().startsWith("shops#")){
//              String[] split =   value.toString().split("#")[1];//["goods","商品编号 商品名称"]
              String[] split =   value.toString().split("#");//["goods","商品编号 商品名称"]
                String goodsInfo = split[1];
                goodsList.add(goodsInfo);
            }
            if(value.toString().startsWith("category#")){
                String orderInfo = value.toString().split("#")[1];
                orderList.add(orderInfo);
            }
        }

        //获取2个集合的长度
        int goodsSize = goodsList.size();
        int orderSize = orderList.size();

        for (int i = 0; i < orderSize; i++) {
            for (int j = 0; j < goodsSize; j++) {
                outValue.set(orderList.get(i)+"\t"+goodsList.get(j));
                //最终输出：商品id、订单编号、实际支付价格、商品编号、商品名称
                context.write(key,outValue);
            }
        }

        orderList.clear();
        goodsList.clear();
    }
}

