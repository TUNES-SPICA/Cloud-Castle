package castle.demo.socket;


import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <b>虚拟主机</b>
 *
 * @author ran
 * @apiNote 构建一个虚拟主机，用于模拟真实服务端
 * @date 2021年7月8日22:13:50
 */
@SuppressWarnings("jol")
public class VirtualServe extends Thread {

    @Override
    public void run() {
        try {
//            buildCli();
            buildHttpCli();
        } catch (IOException e) {
            System.out.println(".....服务器构建失败");
            e.printStackTrace();
        }
    }

    public void buildCli() throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080, 1024, null);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10, 1, TimeUnit.HOURS, new LinkedBlockingQueue<>(5));
        System.out.println(".....虚拟主机构建完毕");
        while (true) {
            Socket socket = serverSocket.accept();
            threadPoolExecutor.execute(() -> {
                System.out.println(socket.getRemoteSocketAddress());

                try {
                    BufferedWriter bufferedWriter = new BufferedWriter(new PrintWriter(socket.getOutputStream()));
                    String body = """
                            HTTP/1.1 200 OK
                            Date: Sat, 31 Dec 2005 23:59:59 GMT
                            Content-Type: text/html; charset=UTF-8
                            Content-Length: 122
                                                        
                            ＜html＞
                            ＜head＞
                            ＜title＞Wrox Homepage＜/title＞
                            ＜/head＞
                            ＜body＞
                            ＜!-- body goes here --＞
                            ＜/body＞
                            ＜/html＞
                            """;
                    bufferedWriter.write(body);
                    bufferedWriter.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public void buildHttpCli() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8001), 0);
        server.createContext("/test", new TestHandler());
        server.start();
    }

    static class TestHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String response = "<h1>Hello World</h1>";
            exchange.sendResponseHeaders(200, 0);
            System.out.println(exchange.getRequestURI());
            System.out.println(exchange.getRequestBody());
            System.out.println(exchange.getRequestMethod());
            System.out.println(exchange.getRequestHeaders());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }


}
