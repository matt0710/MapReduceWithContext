package MP.MaxPartecipants;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;
import java.util.*;

public class AvgAgeReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

    public void reduce(Text text, Iterator<IntWritable> iterator, Context context) throws IOException, InterruptedException {

        int size = 0;
        LinkedList<Integer> list = new LinkedList<>();
        int outcome = 0;


        while (iterator.hasNext()) {
            list.add(iterator.next().get());
            size++;
        }

        for (int i = 0; i < list.size(); i++) {
            outcome += list.get(i);
        }

        context.write(text, new IntWritable(outcome/size));

    }
}
