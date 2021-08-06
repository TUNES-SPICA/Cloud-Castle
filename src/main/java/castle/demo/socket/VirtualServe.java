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
            buildCli();
//            buildHttpCli();
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
                int b = 0;
                StringBuilder s = new StringBuilder();
                while (true) {
                    try {
                        if (((b = socket.getInputStream().read()) != -1)) break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    s.append((char) b);
                }
                System.out.println(s.toString());
                try {
                    BufferedWriter bufferedWriter = new BufferedWriter(new PrintWriter(socket.getOutputStream()));
//                    PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
                    String body = "                            HTTP/1.1 200 O\r\n" +
                            "                            Server: Tengine\r\n" +
                            "                            Date: Sun, 06 May 2018 08:22:10 GMT\r\n" +
                            "                            Content-Type: application/json;charset=UTF-8\r\n" +
                            "                            Content-Length: 10\r\n" +
                            "                            Connection: close\r\n" +
                            "                            X-Powered-By: ring/1.0.0\r\n" +
                            "                            gsid: 010185222147152559493030300162313551811\r\n" +
                            "                            sc: 0.013\r\n" +
                            "                            Access-Control-Allow-Origin: *\r\n" +
                            "                            Access-Control-Allow-Methods: *\r\n" +
                            "                            Access-Control-Allow-Headers: DNT,X-CustomHeader,Keep-Alive,User-Agent,X-Requested-With,If-Modified-Since,Cache-Control,Content-Type,key,x-biz,x-info,platinfo,encr,enginever,gzipped,poiid";
                    bufferedWriter.write(body);
                    bufferedWriter.close();
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
            String response = """
                    HTTP/1.1 200 OK
                    Server: Tengine
                    Date: Sun, 06 May 2018 08:22:10 GMT
                    Content-Type: application/json;charset=UTF-8
                    Content-Length: 445
                    Connection: close
                    X-Powered-By: ring/1.0.0
                    gsid: 010185222147152559493030300162313551811
                    sc: 0.013
                    Access-Control-Allow-Origin: *
                    Access-Control-Allow-Methods: *
                    Access-Control-Allow-Headers: DNT,X-CustomHeader,Keep-Alive,User-Agent,X-Requested-With,If-Modified-Since,Cache-Control,Content-Type,key,x-biz,x-info,platinfo,encr,enginever,gzipped,poiid                    
                    """;
            exchange.sendResponseHeaders(200, 0);
            System.out.println(exchange.getRequestURI());
            System.out.println(exchange.getRequestBody());
            System.out.println(exchange.getRequestMethod());
            System.out.println(exchange.getRequestHeaders());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.flush();
//            os.close();
        }
    }


}
