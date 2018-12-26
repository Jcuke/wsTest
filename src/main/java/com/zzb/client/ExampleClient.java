package com.zzb.client;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;

/**
 * This example demonstrates how to create a websocket connection to a server. Only the most important callbacks are overloaded.
 */
public class ExampleClient extends WebSocketClient {

    private String userId;

    public ExampleClient(URI serverUri, Draft draft) {
        super(serverUri, draft);
    }

    public ExampleClient(URI serverURI, String userId) {
        super(serverURI);
        this.userId = userId;
    }

    public ExampleClient(URI serverURI) {
        super(serverURI);
    }

    public ExampleClient(URI serverUri, Map<String, String> httpHeaders) {
        super(serverUri, httpHeaders);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        //send("Hello, it is me. Mario :)");
        System.out.println("opened connection");
        // if you plan to refuse connection based on ip or httpfields overload: onWebsocketHandshakeReceivedAsClient

        String enterMessage = "{\n" +
                "  \"businessData\" : {\n" +
                "    \"isReconnect\" : \"0\",\n" +
                "    \"liveId\" : \"24321165\",\n" +
                "    \"userId\" : \""+ userId +"\",\n" +
                "    \"enterType\" : \"2\",\n" +
                "    \"expGrade\" : \"1\"\n" +
                "  },\n" +
                "  \"messageType\" : \"entry\"\n" +
                "}";





        send(enterMessage);

        while(true){
            try {
                Thread.sleep(500);
                send("ping");

                Thread.sleep(500);

                String chatMessage = "{\n" +
                        "  \"businessData\" : {\n" +
                        "    \"userId\" : \""+ userId +"\",\n" +
                        "    \"content\" : \""+ genRandomString(8) +  getCurrentTime() +"\",\n" +
                        "    \"userName\" : \""+ userId +"\",\n" +
                        "    \"liveId\" : \""+ 24321165 +"\",\n" +
                        "    \"role\" : \"2\",\n" +
                        "    \"avatar\" : \"http:\\/\\/f.hiphotos.baidu.com\\/zhidao\\/pic\\/item\\/b03533fa828ba61e652e1c764234970a314e59a3.jpg\",\n" +
                        "    \"expGrade\" : \"1\",\n" +
                        "    \"sex\" : \"0\"\n" +
                        "  },\n" +
                        "  \"messageType\" : \"chat\"\n" +
                        "}";

                send(chatMessage);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onMessage(String message) {
        System.out.println("userId["+ userId +"]received: " + message);
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        // The codecodes are documented in class org.java_websocket.framing.CloseFrame
        System.out.println("Connection closed by " + (remote ? "remote peer" : "us") + " Code: " + code + " Reason: " + reason);
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
        // if the error is fatal then onClose will be called additionally
    }

    public static void main(String[] args) throws URISyntaxException {
        ExampleClient client = new ExampleClient(new URI("ws://192.168.88.35:15247/websocket/24321165&49999&2/an7q2onzwr6cj89zux3pyjsz6j54mw4o")); // more about drafts here: http://github.com/TooTallNate/Java-WebSocket/wiki/Drafts
        client.connect();
    }


    public static String genRandomString(int length) { //length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    public String getCurrentTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss");
        return simpleDateFormat.format(new Date());
    }

}