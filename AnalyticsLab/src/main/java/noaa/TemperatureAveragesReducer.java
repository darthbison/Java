package noaa;

import java.io.IOException;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;


public class TemperatureAveragesReducer extends
Reducer<Text, FloatWritable, Text, FloatWritable> {

	private FloatWritable avg = new FloatWritable();

	public void reduce(Text key, Iterable<FloatWritable> values,
			Context context) {
		float sum = 0;
		int count = 0;
		for (FloatWritable value : values) {
			sum += value.get();
			count++;
		}

		avg.set(sum / count);
		try {
			context.write(key, avg);
		} catch (IOException e) {
			// Do nothing
		} catch (InterruptedException e) {
			// Do nothing
		}
	}
	
}
