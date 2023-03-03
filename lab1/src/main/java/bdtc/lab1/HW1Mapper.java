package bdtc.lab1;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import java.io.IOException;

/**
 * Маппер: проверяет строку на повреждённость, затем на допустимость.
 * Если строка прошла проверки, то получает из строки код важности и час получения,
 * и передаёт эти данные в редьюсер {@link HW1Reducer}.
 */
public class HW1Mapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    private final static String syslogPattern = "^(<([0-9]|[1-9][0-9]|1[0-8][0-9]|19[0-1])>)" +  // Код важности
            "(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)" +  // Месяц
            " ([0-2][0-9]|3[0-1])" +  // Число
            " (([0-1][0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9])" +  // Время
            " ((([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])[.]" +
                "([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])[.]" +
                "([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])[.]" +
                "([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5]))" +
                "|[a-zA-Z_0-9]+)" +  // Возможное имя
            " (.+)$";  // Возможное сообщение

    private final static IntWritable one = new IntWritable(1);
    private Text word = new Text();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        try {
            String line = value.toString();
            if (line.matches(syslogPattern)) {
                word.set(KeyParser.parseKey(line));
                context.write(word, one);
            } else {
                context.getCounter(CounterType.INVALID).increment(1);
            }
        } catch (Exception ex) {
            context.getCounter(CounterType.CORRUPTED).increment(1);
        }
    }
}
