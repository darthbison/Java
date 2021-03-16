package wikipedia.hourlymax;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

/**
 * InputFormat for storing an article's title and pagecount
 */
public class ArticleCountFormat implements Writable {
	private String article;
	private int count;

	public ArticleCountFormat() {
	}

	public ArticleCountFormat(String article, int count) {
		this.article = article;
		this.count = count;
	}

	public String getArticle() {
		return article;
	}

	public void setArticle(String article) {
		this.article = article;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void readFields(DataInput in) throws IOException {
		// TODO Read the article
		article = in.readUTF();
		// TODO Read the count
		count = in.readInt();
	}

	public void write(DataOutput out) throws IOException {
		// TODO Write the article
		out.writeUTF(article);
		// TODO Write the count
		out.writeInt(count);
	}

	public String toString() {
		return article + "\t" + count;
	}

}
