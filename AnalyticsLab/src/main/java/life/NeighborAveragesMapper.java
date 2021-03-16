package life;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class NeighborAveragesMapper extends
Mapper<Object, Text, Text, IntWritable> {

    protected Text player = new Text();
    private IntWritable neighborCount = new IntWritable();

    // Extract the article title and page count
    public void map(Object key, Text value, Context context) {
        String line = value.toString();        
        
        // Tokenize the data
        StringTokenizer tokenizer = new StringTokenizer(line);
        while (tokenizer.hasMoreTokens()) {
            String item = tokenizer.nextToken();
            if (item.contains("P"))
            {
                player.set(item);
                
                int neighbors = Integer.parseInt(tokenizer.nextToken());
                neighborCount.set(neighbors);
                
                try {
                    context.write(player, neighborCount);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
           
        }
    }
    
}
