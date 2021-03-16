package wikipedia.hourlymax;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * Determines the article with the most pagecounts.
 */
public class WikipediaHourlyMaxReducer extends
		Reducer<Text, ArticleCountFormat, Text, ArticleCountFormat> {

	private ArticleCountFormat article = new ArticleCountFormat();

	public void reduce(Text key, Iterable<ArticleCountFormat> values,
			Context context) {
		int max = 0;
		Text articleTitle = new Text();

		// Find the max value
		for (ArticleCountFormat value : values) {
			if (value.getCount() > max) {
				max = value.getCount();
				articleTitle.set(value.getArticle());
			}
		}

		// Set the article properties
		// TODO Set the article properties
		article.setArticle(articleTitle.toString());
		article.setCount(max);

		try {
			// TODO Write out the key and value to the context
			context.write(key, article);
		} catch (IOException e) {
			// Do nothing
		} catch (InterruptedException e) {
			// Do nothing
		}
	}

}
