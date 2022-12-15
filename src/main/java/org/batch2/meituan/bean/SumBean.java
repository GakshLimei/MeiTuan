package org.batch2.meituan.bean;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class SumBean implements WritableComparable {
    private long monthSales;//累计月销量
    private long ratingAmounts;//累计评论数
    private double minPrice;//累计最小价格
    private double deliveryFee;//累计配送费



    public SumBean() {
    }

    public SumBean(long monthSales, long ratingAmounts, double minPrice, double deliveryFee) {
        this.monthSales = monthSales;
        this.ratingAmounts = ratingAmounts;
        this.minPrice = minPrice;
        this.deliveryFee = deliveryFee;
    }

    /**
     * 对有参构造进行修改，提供一个set方法
     * 自己封装对象的set方法，用于对象属性赋值
     */
    public void set(long monthSales, long ratingAmounts,double minPrice,double deliveryFee) {
        this.monthSales = monthSales;
        this.ratingAmounts = ratingAmounts;
        this.minPrice = minPrice;
        this.deliveryFee = deliveryFee;
    }

    public long getMonthSales() {
        return monthSales;
    }

    public void setMonthSales(long monthSales) {
        this.monthSales = monthSales;
    }

    public long getRatingAmounts() {
        return ratingAmounts;
    }

    public void setRatingAmounts(long ratingAmounts) {
        this.ratingAmounts = ratingAmounts;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public double getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(double deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    //修改一下，返回的都是数据
    @Override
    public String toString() {
        return monthSales +
                "\t"+ ratingAmounts +
                "\t"+ minPrice +
                "\t"+ deliveryFee;

    }


    @Override
    public int compareTo(Object o) {
        return 0;
    }
    /**
     * 序列化方法，可以控制把哪写字段序列化出去
     */
    @Override
    public void write(DataOutput out) throws IOException {
        out.writeLong(monthSales);
        out.writeLong(ratingAmounts);
        out.writeDouble(minPrice);
        out.writeDouble(deliveryFee);
    }
    /**
     * 反序列化方法
     *  todo 注意反序列化的顺序和序列化顺序一致
     */
    @Override
    public void readFields(DataInput in) throws IOException {
        this.monthSales = in.readLong();
        this.ratingAmounts = in.readLong();
        this.minPrice = in.readDouble();
        this.deliveryFee = in.readDouble();
    }
}
