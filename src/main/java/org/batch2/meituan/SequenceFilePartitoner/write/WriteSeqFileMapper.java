package org.batch2.meituan.SequenceFilePartitoner.write;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class WriteSeqFileMapper extends Mapper<LongWritable, Text, NullWritable,Text> {
    private NullWritable outputKey;
    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        this.outputKey = NullWritable.get();
    }
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        context.write(outputKey, value);
    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        this.outputKey = null;
    }
}
