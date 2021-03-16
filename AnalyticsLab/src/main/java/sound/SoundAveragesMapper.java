package sound;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


public class SoundAveragesMapper extends
Mapper<LongWritable, Text, Text, FloatWritable>{
    
	protected Text second = new Text();
	private FloatWritable dBA = new FloatWritable();

	// Extract the article title and page count
	public void map(LongWritable key, Text value, Context context) {
		String line = value.toString();

		// Tokenize the data
		StringTokenizer tokenizer = new StringTokenizer(line);

		// Output the key and value
		try {
			// Get the seconds
			long time = Long.parseLong(tokenizer.nextToken());
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date(time));
			second.set(Integer.toString(cal.get(Calendar.SECOND)));
		
			float decible = Float.parseFloat(tokenizer.nextToken());
			
			dBA.set(decible);

			context.write(second, dBA);
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
