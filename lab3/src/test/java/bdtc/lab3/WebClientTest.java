package bdtc.lab3;

import org.junit.Test;

/**
 * Websocket client test class
 */
public class WebClientTest {

    /**
     * Websocket client test function on wrong URI
     */
    @Test
    public void testWrongURL() {
        try{
            StartWebClient.start("fdfsdfsd",1232);
            assert false;
        } catch (Exception e){
            assert true;
        }
    }

    /**
     * Websocket client test of 1 normal message
     */
    @Test
    public void testWebClientReading() {
        try{
            StartWebClient.start("wss://ws.coincap.io/trades/binance", 5400);
            assert true;
        } catch (Exception e){
            assert false;
        }
    }
}
