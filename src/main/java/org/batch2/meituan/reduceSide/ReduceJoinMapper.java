package org.batch2.meituan.reduceSide;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

/**
 * @author:Gary
 * @date: 2022年11月10日 17:43
 * @desc: 使用 mapper 处理订单数据和商品数据，输出的时候以 goodsId 商品编号作为 key。相
 * 同 goodsId 的商品和订单会到同一个 reduce 的同一个分组，在分组中进行订单和商品信息的关联合并。
 * 在 MapReduce 程序中可以通过 context 获取到当前处理的切片所属的文件名称。
 * 根据文件名来判断当前处理的是订单数据还是商品数据，以此来进行不同逻辑的输出。
 * join 处理完之后，最后可以再通过 MapReduce 程序排序功能，将属于同一笔订单的所有商品信息汇聚在一起。
 * 1.读取两个数据
 * 2.根据文件名称进行响应的数据切割和拼接
 * 3.输出数据
 */
public class ReduceJoinMapper extends Mapper<LongWritable, Text, Text, Text> {

    Text outKey = new Text();//声明了输出的key
    Text outValue = new Text();//生命了输出的value
    StringBuilder sb = new StringBuilder();//是一个字符串的升级版，里面包含了一些方法，比如切割、切分、拼接等等一些操作
    String filename = null;//生命一个用来装文件名得字符串

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        //获取当前处理的切片所属的文件名字
        FileSplit inputSplit = (FileSplit) context.getInputSplit();
        filename = inputSplit.getPath().getName();//获得正在读取的数据源的文件名称
        System.out.println("当前正在处理的文件是：" + filename);
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        //设置字符串长度，此处用于清空数据
        sb.setLength(0);

        //切割处理输入数据
        String[] fields = value.toString().split("\\|");
        //判断处理的是哪个文件
        if (filename.contains("eleme_shops.txt")) {//处理的是商品数据
            // 100101|155083444927602|四川果冻橙6个约180g  （商品id、商品编号、商品名称）
            outKey.set(fields[0]);//将商品表里的id作为key。因为在reduce根据key来进行数关联操作。
//            StringBuilder append =sb.append(fields[1]+"\t"+fields[2]);
            StringBuilder append =sb.append(fields[1]).append("\t").append(fields[2]);
            //在起始位置添加一个goods#字符串 目的是在reduce进行识别和拆分
            outValue.set(sb.insert(0,"shops#").toString());
            //向reduce阶段输出的是商品信息的数据
            context.write(outKey,outValue);
        } else {//处理的是订单数据
            //  2|113561|11192  （订单编号、商品id、实际支付价格）
            outKey.set(fields[1]);
            StringBuilder append =sb.append(fields[0]).append("\t").append(fields[2]);
            outValue.set(sb.insert(0,"category#").toString());
            context.write(outKey,outValue);
        }
    }
}

