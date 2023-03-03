package bdtc.lab1;

import lombok.extern.log4j.Log4j;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

/**
 * Вычисляет количество сообщений различной значимости в syslog Linux с почасовой гранулярностью.
 * Использует маппер {@link HW1Mapper} и редьюсер {@link HW1Reducer}.
 * Недопустимые и поврежденные строки не учитываются в результате. Их число будет выведено на экран по итогу работы.
 */
@Log4j
public class MapReduceApplication {

    public static void main(String[] args) throws Exception {

        if (args.length < 2) {
            throw new RuntimeException("You should specify exactly input and output folders!");
        }
        Configuration conf = new Configuration();

        Job job = Job.getInstance(conf, "browser count");
        job.setJarByClass(MapReduceApplication.class);
        job.setMapperClass(HW1Mapper.class);
        job.setReducerClass(HW1Reducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        Path outputDirectory = new Path(args[1]);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, outputDirectory);
        log.info("=====================JOB STARTED=====================");
        job.waitForCompletion(true);
        log.info("=====================JOB ENDED=====================");
        // проверяем статистику по счётчикам
        Counter counter1 = job.getCounters().findCounter(CounterType.INVALID);
        log.info("=====================COUNTER " + counter1.getName() + ": " + counter1.getValue() + "=====================");
        Counter counter2 = job.getCounters().findCounter(CounterType.CORRUPTED);
        log.info("=====================COUNTER " + counter2.getName() + ": " + counter2.getValue() + "=====================");
    }
}
