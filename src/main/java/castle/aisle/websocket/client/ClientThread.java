package castle.aisle.websocket.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * 作用：一直接收服务端转发过来的信息
 */
public class ClientThread extends Thread {

    private Socket socket;

    public ClientThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            Socket socket=new Socket();
            InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
            BufferedReader br = new BufferedReader(inputStreamReader);
            try {
                // 信息的格式：(login||logout||say),发送人,收发人,信息体
                while (true) {
                    String msg = br.readLine();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}