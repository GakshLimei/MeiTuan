package org.batch2.meituan.bean;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author:Ys
 * @date: 2022年08月12日 15:28
 * @desc:
 */
public class CovidBean implements WritableComparable<CovidBean> {


    private String state;//州
    private String county;//县
    private long cases;//确诊病例

    public CovidBean() {
    }

    public CovidBean(String state, String county, long cases) {
        this.state = state;
        this.county = county;
        this.cases = cases;
    }

    //为了避免重复创建 该对象去传值
    public void set(String state, String county, long cases) {
        this.state = state;
        this.county = county;
        this.cases = cases;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public long getCases() {
        return cases;
    }

    public void setCases(long cases) {
        this.cases = cases;
    }

    @Override
    public String toString() {
        return "CovidBean{" +
                "state='" + state + '\'' +
                ", county='" + county + '\'' +
                ", cases=" + cases +
                '}';
    }

    //todo 排序规则 根据州state正序进行排序 如果州相同 则根据确诊数量cases倒序排序
    @Override
    public int compareTo(CovidBean o) {
        //定义返回结果值 1 0 - 1
        int result;

        int i = state.compareTo(o.getState());

        if (i > 0) {
            result = 1;
        } else if (i < 0) {
            result = -1;
        } else {
            // 确诊病例数倒序排序
            result = cases > o.getCases() ? -1 : (cases < o.getCases() ? 1 : 0);
        }

        return result;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(state);
        dataOutput.writeUTF(county);
        dataOutput.writeLong(cases);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.state = dataInput.readUTF();
        this.county = dataInput.readUTF();
        this.cases = dataInput.readLong();
    }
}
