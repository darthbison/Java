package noaa;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


/**
 * Map the line to a year-deviation key value pair.
 */
public class TemperatureAveragesMapper extends
Mapper<LongWritable, Text, Text, FloatWritable>{

	protected Text year = new Text();
	private FloatWritable temp = new FloatWritable();

	// Extract the article title and page count
	public void map(LongWritable key, Text value, Context context) {
		String line = value.toString();

		// Tokenize the data
		StringTokenizer tokenizer = new StringTokenizer(line);

		// Output the key and value
		try {
			// Get the year
			year.set(tokenizer.nextToken());
			
			// Skip the month
			tokenizer.nextToken();
			
			// Get the deviation. Ignore if the value is -999
			float deviation = Float.parseFloat(tokenizer.nextToken());
			if(deviation < -990) {
				return;
			}
			temp.set(deviation);

			context.write(year, temp);
		} catch (NumberFormatException e) {
			// Do nothing for now
		} catch (IOException e) {
			// Do nothing for now
		} catch (InterruptedException e) {
			// Do nothing for now
		} catch (NoSuchElementException e) {
			// Row doesn't have all the expected columns
		}
	}
	
}
