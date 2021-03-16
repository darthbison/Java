package wikipedia.counts;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


public class WikipediaCountsMapper extends
Mapper<LongWritable, Text, Text, IntWritable> {

	private Text article = new Text();
	private IntWritable pageCount = new IntWritable();
	private final static String ENGLISH = "en";

	// Extract the article title and page count
	public void map(LongWritable key, Text value, Context context) {
		String line = value.toString();

		// Data should be in the following format:
		// article page count page bytes
		String[] columns = line.split(" ");

		// Check if there are 4 columns
		if (columns.length != 4)
			return;

		// Only get the English entries
		if (!columns[0].equals(ENGLISH))
			return;

		// Output the key and value
		try {
			// TODO Set the article (hint: can reuse the same article object)
			article.set(columns[1]);
			
			// TODO Set the pageCount (hint: can reuse the same pageCounts object)
			pageCount.set(Integer.parseInt(columns[2]));

			// TODO Write out the key-value pair to the context
			context.write(article, pageCount);
		} catch (NumberFormatException e) {
			// Do nothing for now
		} catch (IOException e) {
			// Do nothing for now
		} catch (InterruptedException e) {
			// Do nothing for now
		}
	}
	
}
