package life;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class NeighborAverages extends Configured implements Tool {

    public int run(String[] args) throws Exception {
        Configuration conf = getConf();
        
        Job job =  Job.getInstance(conf);

        job.setJarByClass(this.getClass());
        job.setJobName("NeighborAverages");
        

        // Set the output key and value formats
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        
        // Set the Mapper and Reducer classes
        job.setMapperClass(NeighborAveragesMapper.class);
        job.setCombinerClass(NeighborAveragesReducer.class);
        job.setReducerClass(NeighborAveragesReducer.class);

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
                    .println("Usage: hadoop jar <jar_file> Life.NeighborAverages <input_dir> <output_dir>");
        } else {
            int res = ToolRunner.run(new NeighborAverages(), args);
            System.exit(res);
        }
    }
    
    
    
}
