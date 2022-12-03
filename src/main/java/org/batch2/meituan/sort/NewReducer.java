package org.batch2.meituan.sort;


import org.batch2.meituan.bean.CovidCountBean3;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class NewReducer extends Reducer<Text, CovidCountBean3,Text, CovidCountBean3> {
    CovidCountBean3 outValue = new CovidCountBean3();
    int i;
    @Override
    protected void reduce(Text key, Iterable<CovidCountBean3> values, Context context) throws IOException, InterruptedException {
        /**
         * 创建统计变量
         */
        double totalScore = 0;
        double totalComment_number = 0;
        /**
         * 遍历数据
         */
        for ( CovidCountBean3 value : values ) {
            totalScore+=value.getScore();
            totalComment_number+=value.getComment_number();
        }

        outValue.set(Math.round(totalScore),Math.round(totalComment_number));

        /**
         * 输出结果赋值
         */
        context.write(key,outValue);

    }
}
