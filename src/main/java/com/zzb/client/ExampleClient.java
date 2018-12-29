package com.zzb.client;

import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;

/**
 * This example demonstrates how to create a websocket connection to a server. Only the most important callbacks are overloaded.
 */
public class ExampleClient extends WebSocketClient {

    private String userId;
    private String liveId;

    public ExampleClient(URI serverUri, Draft draft) {
        super(serverUri, draft);
    }

    public ExampleClient(URI serverURI, String userId, String liveId) {
        super(serverURI);
        this.userId = userId;
        this.liveId = liveId;
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
                "    \"liveId\" : \""+ liveId +"\",\n" +
                "    \"userId\" : \""+ userId +"\",\n" +
                "    \"enterType\" : \"2\",\n" +
                "    \"expGrade\" : \"1\"\n" +
                "  },\n" +
                "  \"messageType\" : \"entry\"\n" +
                "}";


        System.out.println(enterMessage);
        send(enterMessage);

        while(true){
            try {
                //Thread.sleep(new Random().nextInt(5) * 1000);
                send("ping");

                Thread.sleep(2000);

                //Thread.sleep(new Random().nextInt(1000) * 1000);


                //Thread.sleep(new Random().nextInt(10) * 1000);

                String chatMessage = "{\n" +
                        "  \"businessData\" : {\n" +
                        "    \"userId\" : \""+ userId +"\",\n" +
                        "    \"content\" : \""+ genRandomString(8) +  getCurrentTime() +"\",\n" +
                        "    \"userName\" : \""+ userId +"\",\n" +
                        "    \"liveId\" : \""+ liveId +"\",\n" +
                        "    \"role\" : \"2\",\n" +
                        "    \"avatar\" : \"http:\\/\\/f.hiphotos.baidu.com\\/zhidao\\/pic\\/item\\/b03533fa828ba61e652e1c764234970a314e59a3.jpg\",\n" +
                        "    \"expGrade\" : \"1\",\n" +
                        "    \"sex\" : \"0\"\n" +
                        "  },\n" +
                        "  \"messageType\" : \"chat\"\n" +
                        "}";

                //if(new Random().nextInt(150) % 25 == 0){ //100里面1个数，小于等于10的概率就是10%
                    send(chatMessage);
                System.out.println(chatMessage);
                //}


            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onMessage(String message) {
        System.out.println("onMessage");
    }

    @Override
    public void onMessage(ByteBuffer bytes) {
        super.onMessage(bytes);

        System.out.println(bytes.toString());
    }

    @Override
    public void onWebsocketMessageFragment(WebSocket conn, Framedata frame) {
        System.out.println(frame.getPayloadData().toString());
    }

    //public void onWebsocketMessage(String message) {
    //    System.out.println("userId["+ userId +"]received: " + message);
    //}

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
        //ExampleClient client = new ExampleClient(new URI("ws://192.168.88.35:15247/websocket/24321165&49999&2/an7q2onzwr6cj89zux3pyjsz6j54mw4o")); // more about drafts here: http://github.com/TooTallNate/Java-WebSocket/wiki/Drafts
        //ExampleClient client = new ExampleClient(new URI("ws://120.79.193.155:15247/websocket/31797277&516&2/lif7c5brjpb19pzi2pmd5cb71b0f5xfn"), "516", "31797277");
        //client.connect();


        new Thread(() -> {
            try {

                //new ExampleClient(new URI("ws://120.79.193.155:15247/websocket/07403030&1111&2/wk5i4ngoouqyw3ipdc3nqpmokye2okcd"), "1111", "07403030").connect();
                ExampleClient client = new ExampleClient(new URI("ws://192.168.88.210:15247/websocket/24321165&EA86EF&2/9zeht7acggogq0oaml27o6q7o3s8bmnv"), "EA86EF", "24321165");
                client.setConnectionLostTimeout(300);
                client.connect();

            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }).start();


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