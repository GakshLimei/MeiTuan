package org.batch2.meituan.bean;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

import java.util.HashMap;

/**
 * @author:Yan
 * @date: 2022年08月12日 14:55
 * @desc:
 */
public class StatePartitioner extends Partitioner<Text,Text> {

    public static HashMap<String,Integer> stateMap= new HashMap<String, Integer>();
    static {
        stateMap.put("广东省",0);
        stateMap.put("陕西省",1);
        stateMap.put("四川省",2);
        stateMap.put("江苏省",3);
        stateMap.put("湖南省",4);
    }

    @Override
    public int getPartition(Text key, Text value, int i) {
        Integer code = stateMap.get(key.toString());  //
        if(code !=null ){
            return code;
        }else{
            return 5;
        }

    }
}
