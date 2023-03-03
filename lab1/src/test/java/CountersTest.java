import bdtc.lab1.KeyParser;
import eu.bitwalker.useragentutils.UserAgent;
import bdtc.lab1.CounterType;
import bdtc.lab1.HW1Mapper;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Класс тестирования. Предназначен для тестирования ситуаций обработки недопустимых строк.
 */
public class CountersTest {

    private MapDriver<LongWritable, Text, Text, IntWritable> mapDriver;


    private final String testSysLog = "<28>Aug 08 21:23:04 186.65.81.83 here i am";
    private final String testInvalidSysLog = "<230>Jul +3 22:83:23 1693.613.10.445 walking";
    @Before
    public void setUp() {
        HW1Mapper mapper = new HW1Mapper();
        mapDriver = MapDriver.newMapDriver(mapper);
    }

    @Test
    public void testMapperCounterOne() throws IOException  {
        mapDriver
                .withInput(new LongWritable(), new Text(testInvalidSysLog))
                .runTest();
        assertEquals("Expected 1 counter increment", 1, mapDriver.getCounters()
                .findCounter(CounterType.INVALID).getValue());
    }

    @Test
    public void testMapperCounterZero() throws IOException {
        mapDriver
                .withInput(new LongWritable(), new Text(testSysLog))
                .withOutput(new Text(KeyParser.parseKey(testSysLog)), new IntWritable(1))
                .runTest();
        assertEquals("Expected 1 counter increment", 0, mapDriver.getCounters()
                .findCounter(CounterType.INVALID).getValue());
    }

    @Test
    public void testMapperCounters() throws IOException {
        mapDriver
                .withInput(new LongWritable(), new Text(testSysLog))
                .withInput(new LongWritable(), new Text(testInvalidSysLog))
                .withInput(new LongWritable(), new Text(testInvalidSysLog))
                .withOutput(new Text(KeyParser.parseKey(testSysLog)), new IntWritable(1))
                .runTest();

        assertEquals("Expected 2 counter increment", 2, mapDriver.getCounters()
                .findCounter(CounterType.INVALID).getValue());
    }
}

