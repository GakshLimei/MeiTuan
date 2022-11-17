package org.batch2.meituan.SequenceFilePartitoner.write;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Iterator;

public class WriteSeqFileReducer extends Reducer<NullWritable, Text,NullWritable,Text> {
    private NullWritable outputKey;

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        this.outputKey = NullWritable.get();
    }

    @Override
    protected void reduce(NullWritable key, Iterable<Text> value, Context context) throws IOException, InterruptedException {
        Iterator<Text> iterator = value.iterator();
        while (iterator.hasNext()) {
            context.write(outputKey, iterator.next());
        }
    }
    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        this.outputKey = null;
    }
}
