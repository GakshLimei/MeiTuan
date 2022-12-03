package org.batch2.meituan.bean;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class CovidCountBean3 implements Writable {

    private double Score;//
    private double Comment_number;//

    public CovidCountBean3(){

    }

    public CovidCountBean3(double Score, double Comment_number) {
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
}
