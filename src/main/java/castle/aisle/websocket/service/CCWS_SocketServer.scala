package castle.aisle.websocket.service

import castle.CC_CoreConfig
import castle.util.timing.UtilTime
import org.jline.utils.InputStreamReader

import java.io.{BufferedReader, BufferedWriter, InputStream, OutputStream, PrintWriter}
import java.net.ServerSocket
import java.util.concurrent.{LinkedBlockingQueue, ThreadPoolExecutor, TimeUnit}

class CC_SocketServer extends Method {


  /**
   * 建造 socket 服务器，用于接受请求并转发
   *
   * @param port 端口号
   *
   */
  def buildSocketServer(port: Int): Unit = {

    init()

    start()

  }

  /**
   * main -> 参数初始化
   */
  def init(): Unit = {
    CCWS_Server_Component.cc_socket_threadPool = buildThreadPool()
    CCWS_Server_Component.cc_socket = new ServerSocket(CC_CoreConfig.websocket_server_socket_port, CC_CoreConfig.websocket_server_socket_backlog)
    CCWS_Server_Component.cc_websocket = new CCWS_WebSocketServer().buildWebSocket(CC_CoreConfig.websocket_server_websocket_port)
  }

  /**
   * main -> ...run
   */
  def start(): Unit = {
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
  protected def buildResponse: String =
    "HTTP/1.1 200 OK\nDate: Sat, 31 Dec 2005 23:59:59 GMT\nContent-Type: text/html;charset=ISO-8859-1\nContent-Length: 122\n\n＜html＞\n＜head＞\n＜title＞Wrox Homepage＜/title＞\n＜/head＞\n＜body＞\n＜!-- body goes here --＞\n＜/body＞\n＜/html＞"

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
    val sb: StringBuilder = new StringBuilder(keyId + "<<id\n" + c)
    while (c != -1) {
      sb.append(c.toChar)
      c = bufferedReader.read()
    }
    if (CC_CoreConfig.is_debug) println(sb.toString())
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

}
