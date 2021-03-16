package sound;



import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 * Mapper, Reducer, and Job to average the global surface temperature
 * deviations.
 * 
 * Sample data:
 * Hour Deviation (C)
 * 2    -0.0622
 * 10   -0.1925
 * 
 */

public class SoundAverages extends Configured implements Tool {
	/* (non-Javadoc)
	 * @see org.apache.hadoop.util.Tool#run(java.lang.String[])
	 */
	public int run(String[] args) throws Exception {
		Configuration conf = getConf();

		Job job  = Job.getInstance(conf);
		
		job.setJarByClass(this.getClass());
		job.setJobName("SoundAverages");

		// Set the Mapper and Reducer classes
		job.setMapperClass(SoundAveragesMapper.class);
		job.setReducerClass(SoundAveragesReducer.class);

		// Set the output key and value formats
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(FloatWritable.class);

		// Set the input and output formats
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);

		// Set the input and output paths
		Path inputPath = new Path(args[0]);
		Path outputPath = new Path(args[1]);
		FileInputFormat.setInputPaths(job, inputPath);
		FileOutputFormat.setOutputPath(job, outputPath);

		// Execute the job
		boolean success = job.waitForCompletion(true);

		return success ? 0 : 1;
	}

	public static void main(String[] args) throws Exception {
		if (args.length < 2) {
			System.out
					.println("Usage: hadoop jar <jar_file> sound.SoundAverages <input_dir> <output_dir>");
		} else {
			int res = ToolRunner.run(new SoundAverages(), args);
			System.exit(res);
		}
	}
}
