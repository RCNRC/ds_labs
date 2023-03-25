package bdtc.lab2;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.streaming.Durations;
import org.junit.Test;
import scala.Tuple2;

import java.util.Arrays;
import java.util.List;
import org.apache.spark.streaming.api.java.JavaStreamingContext;


public class SparkTest {

    final String testString1 = "[1=opened and read,2=opened and preview read,3=don't interact]67 110 09:34:00 2";
    final String testString2 = "[1=opened and read,2=opened and preview read,3=don't interact]77 69 15:13:46 3";
    final String testString3 = "[1=opened and read,2=opened and preview read,3=don't interact]67 33 05:11:28 2";

    @Test
    public void testString() {
        SparkConf conf = new SparkConf().setAppName("SparkSQLApplication1").setMaster("local");
        JavaStreamingContext jstrc = new JavaStreamingContext(conf, Durations.seconds(2000));
        JavaSparkContext jsc = jstrc.sparkContext();
        JavaRDD<String> testString = jsc.parallelize(Arrays.asList(testString1));

        JavaPairRDD<String, Iterable<Integer>> result = PagesCounter.countPages(testString);
        List<Tuple2<String, Iterable<Integer>>> rowList = result.collect();

        assert rowList.get(0).toString().equals("(2: 67,[1])");
        jsc.stop();
        jsc.close();
    }
    @Test
    public void testDifferentStrings() {
        SparkConf conf = new SparkConf().setAppName("SparkSQLApplication2").setMaster("local");
        JavaStreamingContext jstrc = new JavaStreamingContext(conf, Durations.seconds(2000));
        JavaSparkContext jsc = jstrc.sparkContext();
        JavaRDD<String> testString = jsc.parallelize(Arrays.asList(testString1, testString2));

        JavaPairRDD<String, Iterable<Integer>> result = PagesCounter.countPages(testString);
        List<Tuple2<String, Iterable<Integer>>> rowList = result.collect();

        assert rowList.get(0).toString().equals("(2: 67,[1])");
        assert rowList.get(1).toString().equals("(3: 77,[1])");
        jsc.stop();
        jsc.close();
    }

    @Test
    public void testSameStrings(){
        SparkConf conf = new SparkConf().setAppName("SparkSQLApplication3").setMaster("local");
        JavaStreamingContext jstrc = new JavaStreamingContext(conf, Durations.seconds(2000));

        JavaSparkContext jsc = jstrc.sparkContext();
        JavaRDD<String> testString = jsc.parallelize(Arrays.asList(testString1, testString1));

        JavaPairRDD<String, Iterable<Integer>> result = PagesCounter.countPages(testString);
        List<Tuple2<String, Iterable<Integer>>> rowList = result.collect();

        assert rowList.get(0).toString().equals("(2: 67,[2])");
        jsc.stop();
        jsc.close();
    }

    @Test
    public void testSameIdentifiers(){
        SparkConf conf = new SparkConf().setAppName("SparkSQLApplication4").setMaster("local");
        JavaStreamingContext jstrc = new JavaStreamingContext(conf, Durations.seconds(2000));

        JavaSparkContext jsc = jstrc.sparkContext();
        JavaRDD<String> testString = jsc.parallelize(Arrays.asList(testString1, testString3));

        JavaPairRDD<String, Iterable<Integer>> result = PagesCounter.countPages(testString);
        List<Tuple2<String, Iterable<Integer>>> rowList = result.collect();

        assert rowList.get(0).toString().equals("(2: 67,[2])");
        jsc.stop();
        jsc.close();
    }

    @Test
    public void testCombineIdentifiers(){
        SparkConf conf = new SparkConf().setAppName("SparkSQLApplication5").setMaster("local");
        JavaStreamingContext jstrc = new JavaStreamingContext(conf, Durations.seconds(2000));

        JavaSparkContext jsc = jstrc.sparkContext();
        JavaRDD<String> testString = jsc.parallelize(Arrays.asList(testString1, testString2, testString3));

        JavaPairRDD<String, Iterable<Integer>> result = PagesCounter.countPages(testString);
        List<Tuple2<String, Iterable<Integer>>> rowList = result.collect();

        assert rowList.get(0).toString().equals("(2: 67,[2])");
        assert rowList.get(1).toString().equals("(3: 77,[1])");
        jsc.stop();
        jsc.close();
    }
}
