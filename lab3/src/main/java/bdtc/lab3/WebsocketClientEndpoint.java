package bdtc.lab3;

import org.apache.tomcat.websocket.WsWebSocketContainer;
import java.net.URI;
import java.nio.ByteBuffer;
import javax.websocket.*;

/**
 * WebServer Client
 */
@ClientEndpoint
public class WebsocketClientEndpoint {

    Session userSession = null;
    private MessageHandler messageHandler;

    /**
     * Websocket client init
     *
     * @param endpointURI URI of ws or wss
     */
    public WebsocketClientEndpoint(URI endpointURI) {
        ClientEndpointConfig.Builder clientEndpointConfigBuilder = ClientEndpointConfig.Builder.create();
        try {
            WebSocketContainer conn = new WsWebSocketContainer();
            conn.connectToServer(this, endpointURI);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Callback hook for Connection open events.
     *
     * @param userSession the userSession which is opened.
     */
    @OnOpen
    public void onOpen(Session userSession) {
        System.out.println("opening websocket");
        this.userSession = userSession;
    }

    /**
     * Callback hook for Connection close events.
     *
     * @param userSession the userSession which is getting closed.
     * @param reason the reason for connection close
     */
    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
        System.out.println("closing websocket");
        this.userSession = null;
    }

    /**
     * Callback hook for Message Events. This method will be invoked when a client send a message.
     *
     * @param message The text message
     */
    @OnMessage
    public void onMessage(String message) {
        if (this.messageHandler != null) {
            this.messageHandler.handleMessage(message);
        }
    }

    @OnMessage
    public void onMessage(ByteBuffer bytes) {
        System.out.println("Handle byte buffer");
    }

    /**
     * register message handler
     *
     * @param msgHandler
     */
    public void addMessageHandler(MessageHandler msgHandler) {
        this.messageHandler = msgHandler;
    }

    /**
     * Send a message.
     *
     * @param message
     */
    public void sendMessage(String message) {
        this.userSession.getAsyncRemote().sendText(message);
    }

    /**
     * Message handler.
     */
    public static interface MessageHandler {
        public void handleMessage(String message);
    }
}