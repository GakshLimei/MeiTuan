package org.batch2.meituan.bean;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author:Ys
 * @date: 2022年08月11日 13:58
 * @desc:自定义对象作为数据类型在MR中传递
 */
public class CommentCountBean implements Writable {

    private long cases;//累计出售数
    private long comments;//累计评论数

    public CommentCountBean() {

    }

    public CommentCountBean(long cases, long comments) {
        this.cases = cases;
        this.comments = comments;
    }

    /**
     * 对有参构造进行修改，提供一个set方法
     * 自己封装对象的set方法，用于对象属性赋值
     */
    public void set(long cases, long comments) {
        this.cases = cases;
        this.comments = comments;
    }

    //3、set和get方法

    public long getCases() {
        return cases;
    }

    public void setCases(long cases) {
        this.cases = cases;
    }

    public long getComments() {
        return comments;
    }
    public void setComments(long comments) {
        this.comments = comments;
    }


    //修改一下，返回的都是数据
    @Override
    public String toString() {
        return cases + "\t" + comments;
    }

    /**
     * 序列化方法，可以控制把哪写字段序列化出去
     */
    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeLong(cases);
        dataOutput.writeLong(comments);
    }

    /**
     * 反序列化方法
     *  todo 注意反序列化的顺序和序列化顺序一致
     */
    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.cases = dataInput.readLong();
        this.comments = dataInput.readLong();
    }
}
