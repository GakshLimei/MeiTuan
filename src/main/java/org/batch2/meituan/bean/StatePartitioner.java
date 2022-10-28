package org.batch2.meituan.Been;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

import java.util.HashMap;

/**
 * @author:Yan
 * @date: 2022年08月12日 14:55
 * @desc:
 */
public class StatePartitioner extends Partitioner<Text,Text> {

    //模拟美国各州的数据字典，实际中可以从redis进行读取加载，如果数据量不大，也可以创建数据集合保存
    public static HashMap<String,Integer> stateMap= new HashMap<String, Integer>();
    static {
        stateMap.put("广东省",0);
        stateMap.put("陕西省",1);
        stateMap.put("四川省",2);
        stateMap.put("江苏省",3);
        stateMap.put("湖南省",4);
    }

    /**
     *  todo 自定义分区器中分区规则的实现方法  只要getPartition返回的int一样，数据就会被分到同一个分区
     *  所谓同一个分区指的是数据到同一个reducetask处理
     *  k：state州 -------字符串===Text
     *  v：这一行内容  -------字符串整体，无需封装==Text
     */
    @Override
    public int getPartition(Text key, Text value, int i) {
        Integer code = stateMap.get(key.toString());  //
        if(code !=null ){
            return code;
        }else{
            return 5;// 其他州 放到 第六个分区文件
        }

    }
}
