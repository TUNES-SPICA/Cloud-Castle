package castle.aisle.websocket.service

import castle.CC_CoreConfig
import castle.util.timing.UtilTime
import com.sun.net.httpserver.{HttpExchange, HttpHandler, HttpServer}
import org.jline.utils.InputStreamReader

import java.io._
import java.net.{InetSocketAddress, ServerSocket}
import java.util.concurrent.{LinkedBlockingQueue, ThreadPoolExecutor, TimeUnit}

class CC_SocketServer extends Method {


  /**
   * 建造 socket 服务器，用于接受请求并转发
   *
   */
  def buildSocketServer(): Unit = {

    init()

    start()

  }

  /**
   * main -> 参数初始化
   */
  def init(): Unit = {

    CCWS_Server_Component.cc_socket_threadPool = buildThreadPool()

    // socket 服务器对象
    CCWS_Server_Component.cc_socket = new ServerSocket(CC_CoreConfig.websocket_server_socket_port, CC_CoreConfig.websocket_server_socket_backlog)
    //    buildHttpServer(CC_CoreConfig.websocket_server_socket_port)

    // web socket 连接对象
    CCWS_Server_Component.cc_websocket = new CCWS_WebSocketServer().buildWebSocket(CC_CoreConfig.websocket_server_websocket_port)

  }

  /**
   * main -> ...run
   */
  def start(): Unit = {
    // go ！
    while (true) {
      val socket = CCWS_Server_Component.cc_socket.accept()
      CCWS_Server_Component.cc_socket_threadPool.execute(() => {
        UtilTime.getExecutionTime(id => {
          response(keyId = id, outputStream = socket.getOutputStream, request =
            request(keyId = id, inputStream = socket.getInputStream))
        })
      })
    }
  }


}

class Method {

  /**
   * 构建线程池，用于处理 socket 连接
   *
   * @return thread pool
   */
  protected def buildThreadPool(): ThreadPoolExecutor =
    new ThreadPoolExecutor(10, 10, 1, TimeUnit.HOURS, new LinkedBlockingQueue[Runnable](5))

  /**
   * 构建 http 响应报文
   *
   * @return http response
   */
  protected def buildResponse: String = {
    val body = "<h1>Hello World</h1>"
    "HTTP/1.1 200 OK\r\nconnection: Close\r\ncontent-type: text/html\r\ncontent-length: " + body.length + "\r\n\r\n" + body
  }

  /**
   * 读取 http 请求报文
   *
   * @param keyId       事件id
   * @param inputStream socket 输入流
   * @return http request
   */
  protected def request(keyId: String, inputStream: InputStream): String = {
    val bufferedReader: BufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"))
    var c: Int = bufferedReader.read()
    val sb: StringBuilder = new StringBuilder(keyId + "<<id|")
    while (c != -1) {
      sb.append(c).append("|")
      c = bufferedReader.read()
    }
    if (CC_CoreConfig.is_debug) println(s"http request {$sb}")
    sb.toString()
  }

  /**
   * 处理 http 报文，并且响应返回
   *
   * @param keyId        事件id
   * @param outputStream socket 输出流
   * @param request      请求信息
   */
  protected def response(keyId: String, outputStream: OutputStream, request: String): Unit = {
    val bufferedWriter: BufferedWriter = new BufferedWriter(new PrintWriter(outputStream))
    CCWS_Server_Component.cc_websocket.broadcast(request)
    bufferedWriter.write(buildResponse)
    bufferedWriter.flush()
  }

  /**
   * 构建HTTP服务
   *
   * @param port 端口号
   */
  protected def buildHttpServer(port: Int): Unit = {
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
