package bdtc.lab3;

/**
 * Main class
 */
public class Main {
    public static String url = "wss://ws.coincap.io/trades/binance";

    /**
     * main function, don't require args
     */
    public static void main(String[] args) {
        try {
            StartWebClient.start(url, 5400);
            System.in.read();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
