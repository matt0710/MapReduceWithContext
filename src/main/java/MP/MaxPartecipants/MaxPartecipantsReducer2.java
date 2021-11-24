package MP.MaxPartecipants;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MaxPartecipantsReducer2 extends Reducer<Text, Text, Text, Text> {

    public void reduce(Text text, Iterator<Text> iterator, Context context) throws IOException, InterruptedException {

        String[] row = new String[2];

        HashMap<String, IntWritable> map = new HashMap<>();

        //the iterator contains, for each key called text, the string built with the pair receiving_organization;num_of_partecipants
        //so I extract these two data from each element of the iterator, putting them into the string array row
        //then I put for each receiving_organization (row[0]) the corresponding num_of_partecipants (row[1])
        //finally, using the maxValueInMap, I put in context the key 'text' for which we are using the corresponding iterator and the key of the hashmap created by me.

        while (iterator.hasNext()) {
            row = iterator.next().toString().split(";");
            map.put(row[0], new IntWritable(Integer.parseInt(row[1]))); //i'm putting in the map receiving_organization as key and num_part as values
        }

        IntWritable maxValueInMap = Collections.max(map.values());

        for (Map.Entry<String, IntWritable> entry : map.entrySet()) {
            if (entry.getValue().equals(maxValueInMap)) context.write(text, new Text(entry.getKey()));
        }

    }
}
