package life;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class NeighborAveragesReducer extends
        Reducer<Text, IntWritable, Text, IntWritable> {

    private FloatWritable avg = new FloatWritable();

    private static final Log LOG = LogFactory
            .getLog(NeighborAveragesReducer.class);

    public void reduce(Text key, Iterable<IntWritable> values, Context context) {

        LOG.info(key.toString());

        int sum = 0;
        int count = 0;
        for (IntWritable value : values) {
            sum += value.get();
            count++;
        }

        float average = (sum / (float) count);
        avg.set(average);
        IntWritable result = new IntWritable();
        result.set(sum);

        try {
            context.write(key, result);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // Do nothing for now
        }

    }

}
