package MP.MaxPartecipants;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
//import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.*;

public class EducationReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
    private HashMap<Text, Integer> results;
    private LinkedList<Integer> list;
    private int index;
    //private String[] str;
    //ArrayList<String[]> arrayList = new ArrayList<>();


    @Override
    public void setup(Context context) throws IOException, InterruptedException {
        //super.setup(context);
        results = new HashMap<>();
        list = new LinkedList<>();
        index = 0;
        //str = new String[2];
    }

    public void reduce(Text key, Iterable<IntWritable> values, Context context)
            throws IOException, InterruptedException {

        int outcome = 0;

        for (IntWritable i : values) outcome += i.get();
        results.put(key, outcome);
    }


    /*protected void cleanup(Context context) throws IOException, InterruptedException {

        Collection<IntWritable> list =  results.values();
        int i = 0;
        Collections.sort(results.values().);

        for (Map.Entry<Text, IntWritable> entry : results.entrySet()) {
            if (i < 5) {
                if (entry.getValue().equals(list.get(i))) {
                    context.write(entry.getKey(), entry.getValue());
                }
            }
            else
                break;
            i++;
        }

        super.cleanup(context);
    }*/

    @Override
    public void cleanup(Context context) throws IOException, InterruptedException {


        /*LinkedList<Integer> list = new LinkedList<>();
        int i = 0;

        for (Map.Entry<Text, Integer> entry : results.entrySet()) {
            list.push(entry.getValue());
        }

        Collections.sort(list, Collections.reverseOrder());


        while (i < 5) {
            for (Map.Entry<Text, Integer> entry : results.entrySet()) {
                if (entry.getValue().equals(list.get(i)))
                    treeMap.put(entry.getKey(), entry.getValue());
            }
            i++;
        }

        for (Map.Entry<Text, Integer> entry : treeMap.entrySet())
            context.write(entry.getKey(), new IntWritable(entry.getValue()));

         */

        /*for (Map.Entry<Text, Integer> entry : results.entrySet())
            context.write(entry.getKey(), new IntWritable(entry.getValue()));*/

        for (Map.Entry<Text, Integer> entry : this.results.entrySet())
            this.list.push(entry.getValue());

        Collections.sort(this.list, Collections.reverseOrder());

        for (Map.Entry<Text, Integer> entry : this.results.entrySet()) {
            if (this.index < 5 && entry.getValue().equals(this.list.get(this.index))) {
                context.write(entry.getKey(), new IntWritable(entry.getValue()));
                this.index++;
            }
        }

        //super.cleanup(context);
    }

}
