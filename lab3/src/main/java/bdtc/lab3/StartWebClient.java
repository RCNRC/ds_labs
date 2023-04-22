package bdtc.lab3;

import java.io.*;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Websocket client start class
 */
public class StartWebClient {
    /**
     * Websocket client start function
     *
     * @param url URI of ws or wss
     * @param port port of logstash inner tcp localhost port
     */
    public static void start(String url, Integer port) throws URISyntaxException, IOException {
        final Integer[] ID = {0};
        final WebsocketClientEndpoint clientEndPoint = new WebsocketClientEndpoint(new URI(url));
        clientEndPoint.addMessageHandler(new WebsocketClientEndpoint.MessageHandler() {
            public void handleMessage(String message) {
                try {
                    String aggregatedMessage = Parser.parseString(message);
                    if(aggregatedMessage.length()>0){
                        Socket socket = new Socket("localhost", port);
                        DataOutputStream os = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                        os.writeBytes(aggregatedMessage);
                        os.flush();
                        socket.close();

                        File myObj = new File("/input_data/"+ String.valueOf(ID[0]) + ".json");
                        if (myObj.createNewFile()) {
                            FileWriter myWriter = new FileWriter("/input_data/"+ String.valueOf(ID[0]) + ".json");
                            myWriter.write(message);
                            myWriter.close();
                            ID[0]++;
                        } else {
                            System.out.println("Funny error: file wasn't created.");
                        }
                    }
                } catch (Exception e){
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
