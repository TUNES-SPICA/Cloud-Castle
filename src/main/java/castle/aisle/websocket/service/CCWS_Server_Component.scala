package castle.aisle.websocket.service

import java.net.ServerSocket
import java.util.concurrent.ThreadPoolExecutor
import scala.collection.mutable

object CCWS_Server_Component {


  /**
   * socket 服务器
   */
  var cc_socket: ServerSocket = _

  /**
   * socket 服务器 连接处理线程池
   */
  var cc_socket_threadPool: ThreadPoolExecutor = _

  /**
   * socket 服务器 http响应报文集
   */
  var cc_socket_http_responseMap: mutable.HashMap[String, String] = _

  /**
   * websocket 服务器
   */
  var cc_websocket: CCWS_WebSocketServer = _


}
