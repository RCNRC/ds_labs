package bdtc.lab2;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Класс подсчёта по каждой входной строке
 */
public class PagesCounter {

    /**
     * Вспомогательный класс для сплита строк
     */
    static class SplitFunction implements FlatMapFunction<String, String> {
        private static final long serialVersionUID = 1L;

        /**
         * Функция сплита строки
         * @param s - строка для сплита
         * @return итератор расспличенной строки
         */
        @Override
        public Iterator<String> call(String s) {
            return Arrays.asList(s.split("\n")).iterator();
        }
    }

    /**
     * Функция подсчёта в строке
     * @param lines - считывающиеся строки из входного файла
     * @return ыозвращает пару ключ (идентификатор взаимодействия и номер новости) и значение (число встреченных раз ключа)
     */
    public static JavaPairRDD<String, Iterable<Integer>> countPages(JavaRDD<String> lines){
        JavaRDD<String> words = lines.flatMap(new PagesCounter.SplitFunction());

        JavaPairRDD<String, Integer> pairs = words
                .mapToPair(new PairFunction<String, String, Integer>() {
                    public Tuple2<String, Integer> call(String s) {
                        String[] fields = s.split("]")[1].split(" ");
                        return new Tuple2<String, Integer>(fields[3]+": "+fields[0], 1);
                    }
                });

        JavaPairRDD<String, Iterable<Integer>> counts = pairs.reduceByKey(
                new Function2<Integer, Integer, Integer>() {
                    public Integer call(Integer a, Integer b) {
                        return a + b;
                    }
                }).groupByKey(1).repartition(1).sortByKey();
        return counts;
    }
}
