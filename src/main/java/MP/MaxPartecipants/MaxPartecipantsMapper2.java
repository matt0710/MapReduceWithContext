package MP.MaxPartecipants;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;


public class MaxPartecipantsMapper2 extends org.apache.hadoop.mapreduce.Mapper<LongWritable, Text, Text, Text> {

    boolean check = true;
    String regex = "[0-9]+";
    Pattern p = Pattern.compile(regex);

    @Override
    public void map(LongWritable longWritable, Text value, Context context) throws IOException, InterruptedException {

        String[] row = value.toString().split(";");

        if (check) {
            check = false;
            return;
        }

        Matcher m = p.matcher(row[23]);

        if (!m.matches()) return;

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(row[21] + ";" + row[23]);

        context.write(new Text(row[17]), new Text(stringBuilder.toString()));
    }
}
