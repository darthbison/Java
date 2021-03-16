package wikipedia.counts;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;


public class WikipediaCountsReducer extends
Reducer<Text, IntWritable, Text, IntWritable> {
	private IntWritable count = new IntWritable();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.hadoop.mapreduce.Reducer#reduce(KEYIN,
	 * java.lang.Iterable, org.apache.hadoop.mapreduce.Reducer.Context)
	 * 
	 * Sum the the pagecounts
	 */
	public void reduce(Text key, Iterable<IntWritable> values,
			Context context) {
		int sum = 0;
		
		// TODO Loop through the values and calculate the sum
		for (IntWritable value : values) {
			sum += value.get();
		}

		// TODO Set the count object
		count.set(sum);
		try {
			
			// TODO Write out the key-value pair to the context
			context.write(key, count);
		} catch (IOException e) {
			// Do nothing
		} catch (InterruptedException e) {
			// Do nothing
		}
	}
}
