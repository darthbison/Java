package wikipedia.counts;



import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;




/**
 * Mapper, Reducer, and Job to aggregate the hourly page counts for Wikipedia
 * articles.
 * 
 */
public class WikipediaCounts extends Configured implements Tool {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.hadoop.util.Tool#run(java.lang.String[])
	 * 
	 * Configure and execute the MapReduce job
	 */
	public int run(String[] args) throws Exception {
		Configuration conf = getConf();

		Job job = Job.getInstance(conf);

		job.setJarByClass(this.getClass());
		job.setJobName("WikipediaCounts");

		// Set the Mapper and Reducer classes
		// TODO Set the Mapper and Reducer class
		job.setMapperClass(WikipediaCountsMapper.class);
		job.setReducerClass(WikipediaCountsReducer.class);

		// Set the output key-value formats
		// TODO Set the output key and value classes
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);

		// Set the input and output formats
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);

		// Set the intput and output paths
		Path inputPath = new Path(args[0]);
		Path outputPath = new Path(args[1]);
		FileInputFormat.setInputPaths(job, inputPath);
		FileOutputFormat.setOutputPath(job, outputPath);

		// Execute the job
		boolean success = job.waitForCompletion(true);

		return success ? 0 : 1;
	}

	public static void main(String[] args) throws Exception {
		int res = ToolRunner.run(new WikipediaCounts(), args);
		System.exit(res);
	}
}
