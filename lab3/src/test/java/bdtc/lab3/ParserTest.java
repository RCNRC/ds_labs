package bdtc.lab3;

import org.junit.Test;
import java.util.Objects;

/**
 * Tester class for parser
 */
public class ParserTest {
    final String[] testStrings = {
            "{\"exchange\":\"binance\",\"base\":\"coti\",\"quote\":\"tether\",\"direction\":\"buy\",\"price\":0.0815,\"volume\":122,\"timestamp\":1682151537334,\"priceUsd\":0.0817317943007538}",
            "{\"exchange\":\"binance\",\"base\":\"zilliqa\",\"quote\":\"tether\",\"direction\":\"sell\",\"price\":0.03244,\"volume\":770.9,\"timestamp\":1682151537118,\"priceUsd\":0.0325322626640056}",
            "{\"exchange\":\"binance\",\"base\":\"bitcoin-cash\",\"quote\":\"tether\",\"direction\":\"buy\",\"price\":120.8,\"volume\":0.441,\"timestamp\":1682151537193,\"priceUsd\":121.14356750344861}",
            "{\"exchange\":\"binance\",\"base\":\"bitcoin\",\"quote\":\"australian-dollar\",\"direction\":\"sell\",\"price\":40930.43,\"volume\":0.03671,\"timestamp\":1682151537093,\"priceUsd\":27390.544372148805}",
            "{\"exchange\":\"binance\",\"base\":\"dogecoin\",\"quote\":\"tether\",\"direction\":\"sell\",\"price\":0.07891,\"volume\":1073,\"timestamp\":1682151536729}",
            "n hssx hf ghbffth s",
    };
    final String[] resStrings = {
            "{\"target\":\"buy_1\",\"timestamp\":1682151537334}",
            "{\"target\":\"sell_1\",\"timestamp\":1682151537118}",
            "{\"target\":\"buy_3\",\"timestamp\":1682151537193}",
            "{\"target\":\"sell_5\",\"timestamp\":1682151537093}",
            "",
            "",
    };

    /**
     * Test function with test sets to test parser function in every variant
     */
    @Test
    public void testRightLow() {
        for(int i=0; i<testStrings.length; i++){
            assert Objects.equals(resStrings[i], Parser.parseString(testStrings[i]));
        }
    }
}
