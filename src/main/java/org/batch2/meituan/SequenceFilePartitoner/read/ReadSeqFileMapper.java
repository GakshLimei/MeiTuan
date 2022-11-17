package org.batch2.meituan.SequenceFilePartitoner.read;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class ReadSeqFileMapper extends Mapper<NullWritable, Text,NullWritable,Text> {

    private NullWritable outpukey;

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        this.outpukey = NullWritable.get();
    }

    @Override
    protected void map(NullWritable key, Text value, Context context) throws IOException, InterruptedException {
        context.write(outpukey,value);
    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        this.outpukey = null;
    }
}
