package castle.socket;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * <b>Socket转发服务器</b>
 * <p>用于转发接受到的请求<p/>
 *
 * @author ran
 * @date 2021年7月8日22:16:23
 * @apiNote 接收到请求之后会转发至创建时的目标ip与端口上
 */
@SuppressWarnings("jol")
@Slf4j
public class TranslatePort extends Thread {

    public void buildCastle() throws IOException {
        String[] args = new String[]{"8081", "127.0.0.1", "8001"};
        //获取本地监听端口、远程IP和远程端口
        int localPort = Integer.parseInt(args[0].trim());
        String remoteIp = args[1].trim();
        int remotePort = Integer.parseInt(args[2].trim());
        //启动本地监听端口
        ServerSocket serverSocket = new ServerSocket(localPort);
        System.out.println("localPort=" + localPort + ";remoteIp=" + remoteIp + ";remotePort=" + remotePort + ";启动本地监听端口" + localPort + "成功！");
        while (true) {
            try  {
                Socket clientSocket = serverSocket.accept();
                Socket remoteServerSocket = new Socket(remoteIp, remotePort);
                //获取客户端连接
                System.out.println("accept one client\ncreate remoteip and port success");
                //启动数据转换
                (new TransPortData(remoteServerSocket, clientSocket, "2")).start();
                (new TransPortData(clientSocket, remoteServerSocket, "1")).start();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        System.out.println(".....开始构架转发网关");
        try {
            buildCastle();
        } catch (IOException e) {
            System.out.println(".....转发网关构建失败");
            e.printStackTrace();
        }
    }

}