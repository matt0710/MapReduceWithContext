package MP.MaxPartecipants;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.*;

public class EducationReducer2 extends Reducer<Text, IntWritable, Text, IntWritable> {

    private TreeMap<Integer, String> map;

    @Override
    public void setup (Context context) {
        map = new TreeMap<>(Comparator.reverseOrder());
    }

    public void reduce (Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {

        String name = key.toString();

        int outcome = 0;

        for (IntWritable val : values)
            outcome += val.get();

        map.put(outcome, name);

    }

    @Override
    public void cleanup (Context context) throws  IOException, InterruptedException{

        List<Map.Entry<Integer, String>> entryList = new ArrayList<>(map.entrySet());

        if (entryList.size() < 5) {
            for (int i = 0; i < entryList.size(); i++) {
                context.write(new Text(entryList.get(i).getValue()), new IntWritable(entryList.get(i).getKey()));
            }
        } else {
            for (int i = 0; i < 5; i++) {
                context.write(new Text(entryList.get(i).getValue()), new IntWritable(entryList.get(i).getKey()));
            }
        }

    }
}
