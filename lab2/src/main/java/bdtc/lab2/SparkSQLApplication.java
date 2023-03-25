package bdtc.lab2;

import lombok.extern.slf4j.Slf4j;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Считает количество событий по каждой странице.
 */
@Slf4j
public class SparkSQLApplication {

    /**
     * @param args - args[0]: входной файл, args[1] - выходная папка
     */
    public static void main(String[] args) {
        if (args.length < 2) {
            throw new RuntimeException("Usage: java -jar SparkSQLApplication.jar input.file outputDirectory");
        }
        log.info("Application started!");
        log.debug("Application started");

        SparkConf conf = new SparkConf().setAppName("SparkSQLApplication").setMaster("local");
        JavaSparkContext jsc = new JavaSparkContext(conf);
        JavaRDD<String> lines = jsc.textFile(args[0]);

        log.info("===============COUNTING...================");
        JavaPairRDD<String, Iterable<Integer>> counts = PagesCounter.countPages(lines);
        log.info("============SAVING FILE TO " + args[1] + " directory============");
        counts.saveAsTextFile(args[1]);
        jsc.stop();
        jsc.close();
    }
}
