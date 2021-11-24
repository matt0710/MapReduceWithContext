package MP.MaxPartecipants;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

public class MaxPartecipantsReducer extends MapReduceBase implements Reducer<Text, Text, Text, Text> {

    @Override
    public void reduce(Text text, Iterator<Text> iterator, OutputCollector<Text, Text> outputCollector, Reporter reporter) throws IOException {

        while (iterator.hasNext()) {
            String iter = iterator.next().toString();
            String[] data = iter.split(";"); //this array contains all the encoded bytes of the receving oganization assigned to that key

            int[] intOut = new int[data.length];

            for (int i = 0; i < data.length; i++) {
                intOut[i] = Integer.parseInt(data[i]);
            }

            StringBuilder str = new StringBuilder();

            for (int i : intOut) str.append((char) i); //decoding

            outputCollector.collect(text, new Text(str.toString()));
        }
    }
}
