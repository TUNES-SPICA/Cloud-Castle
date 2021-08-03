package castle.aisle.websocket;

import castle.aisle.websocket.service.CCWS_WebSocketServer;

public class Main {

    public static void main(String[] args) {
        castle.aisle.websocket.service.CCWS_WebSocketServer p2pServer = new CCWS_WebSocketServer();
//        Client p2pClient = new Client();
        int p2pPort = 8080;
        // 启动p2p服务端

        p2pServer.buildWebSocket(p2pPort);
        System.out.println("yes");
        try {
            Thread.sleep(3000);
            p2pServer.write(p2pServer.getSockets().get(0), "nihao");
            p2pServer.write(p2pServer.getSockets().get(0), "guangbo");
        } catch (InterruptedException e) {

            e.printStackTrace();
        }
    }
}