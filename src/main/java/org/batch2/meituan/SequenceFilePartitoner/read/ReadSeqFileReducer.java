package org.batch2.meituan.SequenceFilePartitoner.read;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.Iterator;

public class ReadSeqFileReducer extends Reducer<NullWritable, Text,NullWritable,Text> {

    private NullWritable outputkey;

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        this.outputkey = NullWritable.get();
    }

    @Override
    protected void reduce(NullWritable key, Iterable<Text> value, Context context) throws IOException, InterruptedException {
        Iterator<Text> iterator = value.iterator();
        while (iterator.hasNext()){
            context.write(outputkey, iterator.next());
        }
    }

    @Override
    protected void cleanup(Context context) throws IOException, InterruptedException {
        this.outputkey = null;
    }
}
