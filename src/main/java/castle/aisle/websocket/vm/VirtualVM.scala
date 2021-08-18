package castle.aisle.websocket.vm

import com.sun.net.httpserver.{HttpExchange, HttpHandler, HttpServer}

import java.io.{BufferedWriter, PrintWriter}
import java.net.{InetSocketAddress, ServerSocket}

/**
 * 虚拟主机<p>
 * 无实际意义，用于模拟应用
 */
class VirtualVM {

  /**
   * 虚拟 web服务
   *
   * @param port 启动端口号
   */
  def webVM(port: Int): Unit = {
    //    new Thread(() => buildHttpServer(port)).start()

    val serverSocket = new ServerSocket(12100, 1024)
    while (true) {
      val socket = serverSocket.accept()
      var writer = new BufferedWriter(new PrintWriter(socket.getOutputStream))
      var body = "<h1>Hello World</h1>"
      body = "HTTP/1.1 200 OK\r\nconnection: Close\r\ncontent-type: text/html\r\ncontent-length: " + body.length + "\r\n\r\n" + body
      writer.write(body)
      writer.flush()
    }
  }

  /**
   * 构建HTTP服务
   *
   * @param port 端口号
   */
  private def buildHttpServer(port: Int): Unit = {
    val server: HttpServer = HttpServer.create(new InetSocketAddress(port), 1024)
    server.createContext("/", new HttpHandler {
      override def handle(exchange: HttpExchange): Unit = {
        val response = "<h1>Hello World</h1>"
        exchange.sendResponseHeaders(200, 0)
        println(exchange.getRequestURI)
        println(exchange.getRequestBody)
        val os = exchange.getResponseBody
        os.write(response.getBytes)
        os.close()
      }
    })
    server.start()
  }

}
