package castle.demo.mina;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class TestClient {

    public static void main(String[] args) {
        // 创建一个非阻塞的客户端程序
        IoConnector connector = new NioSocketConnector();
        // 设置链接超时时间
        connector.setConnectTimeout(30000);
        // 添加过滤器
        connector.getFilterChain().addLast(
                "codec",
                new ProtocolCodecFilter(new InfoCodecFactory(
                        new InfoDecoder(StandardCharsets.UTF_8),
                        new InfoEncoder(StandardCharsets.UTF_8))));
        // 添加业务逻辑处理器类
        connector.setHandler(new ClientHandler());
        IoSession session = null;
        try {
            int PORT = 8082;
            String HOST = "127.0.0.1";
            ConnectFuture future = connector.connect(new InetSocketAddress(
                    HOST, PORT));// 创建连接
            future.awaitUninterruptibly();// 等待连接创建完成
            session = future.getSession();// 获得session

            FilePiece piece = new FilePiece(
                    "C:\\Users\\PC\\Desktop\\新建文本文档.txt", 0);

            InfoRequest ir = new InfoRequest(piece);

            session.write(ir);// 发送消息


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("客户端链接异常...");
        }

        session.getCloseFuture().awaitUninterruptibly();// 等待连接断开
        connector.dispose();
    }

}