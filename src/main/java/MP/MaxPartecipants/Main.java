package MP.MaxPartecipants;

import java.io.IOException;
import java.util.Date;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class Main {
    public static void main(String[] args) throws Exception {

        long startTime = new Date().getTime();

        if (args.length != 2) {
            System.err.println("Usage: WordCount <InPath> <OutPath>");
            System.exit(2);
        }

        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Education Main");

        job.setJarByClass(Main.class);
        job.setMapperClass(MaxPartecipantsMapper2.class);
        job.setReducerClass(MaxPartecipantsReducer2.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.waitForCompletion(true);

        long endTime = new Date().getTime();

        System.out.println("Time required is: " + (endTime-startTime) + " ms");

        System.exit(job.waitForCompletion(true) ? 0 : 1);

    }
}
