package com.zzb.client;

import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class TestApp {

    public static void main(String[] args) {

        try {
            String url = "ws://192.168.88.35:15247/websocket/24321165&49999&2/an7q2onzwr6cj89zux3pyjsz6j54mw4o";

            URI uri = new URI(url);

            WebSocketClient client = new WebSocketClient(uri) {

                @Override
                public void onOpen(ServerHandshake serverHandshake) {
                    System.out.println("onOpen");
                }

                @Override
                public void onMessage(String s) {
                    System.out.println(s);
                }

                @Override
                public void onClose(int i, String s, boolean b) {
                    System.out.println("onClose");
                }

                @Override
                public void onError(Exception e) {
                    e.printStackTrace();
                }

            };
            client.connect();

            while (client.getReadyState().equals(WebSocket.READYSTATE.OPEN)) {
                System.out.println("connected success");
            }

            String enterMessage = "{\n" +
                    "  \"businessData\" : {\n" +
                    "    \"isReconnect\" : \"0\",\n" +
                    "    \"liveId\" : \"24321165\",\n" +
                    "    \"userId\" : \"515\",\n" +
                    "    \"enterType\" : \"2\",\n" +
                    "    \"expGrade\" : \"1\"\n" +
                    "  },\n" +
                    "  \"messageType\" : \"entry\"\n" +
                    "}";

            client.send(enterMessage);
            client.send("ping");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}