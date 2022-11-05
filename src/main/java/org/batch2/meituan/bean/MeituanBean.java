package org.batch2.meituan.bean;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class MeituanBean implements WritableComparable<MeituanBean>{

     private String city;//市

     private String name;//店铺名

     private long month_sales;//月销量


    public MeituanBean(){
    }

     public MeituanBean(String city, String name, long month_sales){
        this.city=city;
        this.name=name;
        this.month_sales=month_sales;
     }

     public void set(String city,String name,long month_sales){
         this.city=city;
         this.name=name;
         this.month_sales=month_sales;
     }



    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getMonth_sales() {
        return month_sales;
    }

    public void setMonth_sales(long month_sales) {
        this.month_sales = month_sales;
    }


    @Override
    public String toString(){
        return "MeituanBean{"+
                "city='"+city+'\''+
                ",name='"+name+'\''+
                ",month_sales="+month_sales+
                '}';
    }

    //todo 排序规则，


    @Override
    public int compareTo(MeituanBean o) {
        int result;

        int i=city.compareTo(o.getCity());

        if (i>0){
            result=1;
        }else if(i<0){
            result =-1;
        }else {
            //月销售量倒序排序
            result =month_sales>o.getMonth_sales() ? -1 : (month_sales<o.getMonth_sales() ? 1:0);
        }
        return result;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(city);
        dataOutput.writeUTF(name);
        dataOutput.writeLong(month_sales);

    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.city=dataInput.readUTF();
        this.name=dataInput.readUTF();
        this.month_sales=dataInput.readLong();

    }
}
