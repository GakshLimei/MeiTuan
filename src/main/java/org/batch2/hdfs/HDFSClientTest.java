package org.batch2.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * @author:Gary
 * @date: 2022年11月04日 20:45
 * @desc: 这是一个HDFS客户端的测试类
 */
public class HDFSClientTest {


    private FileSystem fs = null;

    //1.建立连接
    @Before  //在@Test之前去执行。
    public void connect2HDFS() throws IOException {
        //1.设置客户端的身份，用来操作的HDFS。
        System.setProperty("HADOOP_USER_NAME", "root");

        //2.创建连接的配置对象
        Configuration conf = new Configuration();
        //3、执行操作文件系统的地址
        conf.set("fs.defaultFS", "hdfs://192.168.88.130:8020");
        //4.创建文件系统
        fs = FileSystem.get(conf);


    }

    //2.执行操作
    //2.1创建文件夹
    @Test
    public void mkdir() throws IOException {
        //创建文件夹的路径
        Path path = new Path("/meituan");
        fs.mkdirs(path);
    }

    //2.2上传
    @Test
    public void putFile2HDFS() throws IOException {
        //创建本地文件路径
        Path src = new Path("input");
        //创建上传路径
        Path dst = new Path("/meituan/");
        //上传
        fs.copyFromLocalFile(src, dst);
    }

    //2.3下载
//    @Test
//    public void getFile2Local() throws IOException {
//        //创建本地文件路径
//        Path src = new Path("/bd2/1.txt");
//        //创建上传路径
//        Path dst = new Path("D:\\bd2.txt");
//        //下载
//        fs.copyToLocalFile(src, dst);
//    }

    //2.4创建文件
//    @Test
//    public void createFile() throws IOException {
//        //创建文件的路径
//        Path path = new Path("/bd2/text.txt");
//        //利用IO流进行写入文件
//        FSDataOutputStream out = fs.create(path);
//        out.write("Hello Hadoop".getBytes());
//        out.close();
//    }

    //2.5读取文件
    @Test
    public void readFile() throws IOException {
        //创建文件的路径
        Path path = new Path("/meituan/output/meituan/sort/part-r-00000");
        //利用IO流进行读取文件
        FSDataInputStream open = fs.open(path);
        //用于缓冲读取的字节数组
        byte[] bytes = new byte[1024];
        //读取长度
        int len = 0;
        while ((len = open.read(bytes)) != -1) {
            String res = new String(bytes, 0, len);
            System.out.println(res);
        }
    }

    //2.6遍历目录下的内容
    @Test
    public void fileList() throws IOException {
        //创建要遍历的路径
        Path path = new Path("/meituan");

        FileStatus[] fileStatuses = fs.listStatus(path);

        for (FileStatus fileStatus : fileStatuses) {
            System.out.println(fileStatus.toString());
        }

    }


    //3.关闭连接
    @After //在@Test之前去执行。
    public void close() throws IOException {
        if (fs != null) {
            fs.close();
        }
    }
}
