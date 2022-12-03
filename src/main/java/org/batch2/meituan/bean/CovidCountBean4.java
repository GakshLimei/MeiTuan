package org.batch2.meituan.bean;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class CovidCountBean4 implements WritableComparable<CovidCountBean4> {

    private double Score;//
    private double Comment_number;//

    public CovidCountBean4(){

    }

    public CovidCountBean4(double Score, double Comment_number) {
        this.Score = Score;
        this.Comment_number = Comment_number;
    }

    /**
     *对有参构造进行修改，提供一个set方法
     * 自己封装对象的set方法，用于对象属性赋值
     */
    public void set(double Score, double Comment_number) {
        this.Score = Score;
        this.Comment_number = Comment_number;
    }

    //3、set和get方法


    public double getScore() {
        return Score;
    }

    public void setScore(double score) {
        Score = score;
    }

    public double getComment_number() {
        return Comment_number;
    }

    public void setComment_number(double comment_number) {
        Comment_number = comment_number;
    }

    //修改一下，返回的都是数据

    @Override
    public String toString() {
        return Score + "\t" + Comment_number;

    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeDouble(Score);
        dataOutput.writeDouble(Comment_number);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.Score = dataInput.readDouble();
        this.Comment_number = dataInput.readDouble();
    }

    /**
     *自定义对象的排序方法
     * 返回结果： 0 相等， 负数 小于， 正数 大于
     * 倒序精髓：如果大于，强制返回负数； 如果小于，强制返回正数
     * ?:三(目）元运算符
     * ？前面的条件是if(a>b)
     * :的左边是指当条件成立时返回值
     * :的右边是指当条件成立时返回值
     */
    @Override
    public int compareTo(CovidCountBean4 o) {
        return this.Score - o.getScore() > 0 ? -1 : (this.Score - o.getScore() < 0 ? 1 : 0) ;
    }
}
