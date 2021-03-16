package wikipedia.hourlymax;

import java.io.IOException;
import java.util.HashSet;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;




/**
 * Map the Wikipedia data to a filename-"article count" key-value pair
 */
public class WikipediaHourlyMaxMapper extends
		Mapper<LongWritable, Text, Text, ArticleCountFormat> {

	private final static String ENGLISH = "en";
	
	// Black list of article titles that we don't care about
	private static final HashSet<String> articleBlacklist = new HashSet<String>();
	static {
		articleBlacklist.add("Main_Page");
		articleBlacklist.add("Special:Search");
		articleBlacklist.add("Special:Random");
	}
	
	private Text date = new Text();
	private ArticleCountFormat article = new ArticleCountFormat();

	// Extract the article title and page count
	public void map(LongWritable key, Text value, Context context) {
		String line = value.toString();

		// Get the filename which includes the date
		Path filePath = ((FileSplit) context.getInputSplit()).getPath();

		// Data should be in the following format:
		// article page count page bytes
		String[] columns = line.split(" ");

		// Check if there are 4 columns
		if (columns.length != 4)
			return;

		// Only get the English entries
		if (!columns[0].equals(ENGLISH))
			return;

		// Determine if this is a blacklisted article
		if (articleBlacklist.contains(columns[1]))
			return;

		// Output the key and value
		try {
			// TODO Set the key and value
			date.set(filePath.getName());
			article.setArticle(columns[1]);
			article.setCount(Integer.parseInt(columns[2]));

			// TODO Write out the key and value
			context.write(date, article);
		} catch (NumberFormatException e) {
			// Do nothing for now
		} catch (IOException e) {
			// Do nothing for now
		} catch (InterruptedException e) {
			// Do nothing for now
		}
	}
}
