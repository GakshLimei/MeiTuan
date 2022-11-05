package org.batch2.meituan.topN;

import org.batch2.meituan.bean.MeituanBean;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**MapReduce中自定义分组的类**/

public class MeituanGroupingComparator extends WritableComparator{

    protected MeituanGroupingComparator(){

        super(MeituanBean.class,true);
    }

    @Override
    public int compare(WritableComparable a,WritableComparable b){
        //面向对象的三大特点 ：封装  继承  多态
        MeituanBean aBean=(MeituanBean) a;
        MeituanBean bBean=(MeituanBean) b;

        //
        //只要compare返回 0 。mapReduce框架就会认为是一样的。返回值不为0，就认为是不一样
        return aBean.getCity().compareTo(bBean.getCity());

    }
}
