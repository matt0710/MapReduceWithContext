package MP.MaxPartecipants;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import com.opencsv.CSVReader;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.*;

public class MaxPartecipantsMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text>{


    @Override
    public void map(LongWritable longWritable, Text value, OutputCollector<Text, Text> outputCollector, Reporter reporter) throws IOException {

        String[] tempArray = new String[3];
        HashMap<String, HashMap<String, IntWritable>> mainMap = new HashMap<>();
        List<List<String>> records = new ArrayList<List<String>>();


        try (CSVReader csvReader = new CSVReader(new FileReader(value.toString()))) {
            String[] row = null;
            csvReader.readNext(); //skip the first line of csv file.

            while ((row = csvReader.readNext()) != null) {
                records.add(Arrays.asList(row));
            }
        }

        for (int i = 0; i < records.size(); i++) {
            tempArray[0] = records.get(i).get(17); //sending_organization_name
            tempArray[1] = records.get(i).get(21); //receiving_organization_name
            tempArray[2] = records.get(i).get(23); //num_of_partecipants

            HashMap<String, IntWritable> submap = new HashMap<>();

            submap.put(tempArray[1], new IntWritable(Integer.parseInt(tempArray[2])));
            mainMap.put(tempArray[0], submap);
        }


        for (Map.Entry<String, HashMap<String, IntWritable>> entry : mainMap.entrySet()){

            HashMap<String, IntWritable> submap = new HashMap<>();

            for (Map.Entry<String, IntWritable> subEntry : entry.getValue().entrySet()) submap.put(subEntry.getKey(), subEntry.getValue());

            for (Map.Entry<String, IntWritable> subEntry : entry.getValue().entrySet()) {

                IntWritable maxValueInMap = Collections.max(submap.values());

                if (subEntry.getValue() == maxValueInMap) {

                    byte[] bytes = subEntry.getKey().getBytes("US-ASCII"); //encoding
                    int[] intOut = new int[bytes.length];

                    for (int i=0; i < bytes.length; i++) intOut[i] = Integer.parseInt(String.valueOf(bytes[i]));

                    StringBuilder str = new StringBuilder();

                    for (int i : intOut) str.append(i + ";");

                    outputCollector.collect(new Text(entry.getKey()), new Text(str.toString()));
                }
            }
        }
    }
}
