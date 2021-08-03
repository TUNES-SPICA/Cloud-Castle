package castle.aisle.websocket.service

import castle.CC_CoreConfig
import org.java_websocket.WebSocket
import org.java_websocket.handshake.ClientHandshake
import org.java_websocket.server.WebSocketServer

import java.net.InetSocketAddress
import java.util

class CCWS_WebSocketServer {

  private val sockets = new util.ArrayList[WebSocket]

  /**
   * 初始化<p>
   * 创建web socket连接
   *
   * @param port web socket 服务端口
   */
  def buildWebSocket(port: Int): CCWS_WebSocketServer = {
    new Thread(() => {
      val socketServer: WebSocketServer = new WebSocketServer(new InetSocketAddress(port)) {
        override def onOpen(webSocket: WebSocket, clientHandshake: ClientHandshake): Unit = {
          write(webSocket, "服务端连接成功")
          sockets.add(webSocket)
        }

        override def onClose(webSocket: WebSocket, i: Int, s: String, b: Boolean): Unit = {
          if (CC_CoreConfig.is_debug) println(s"case connection failed to peer {" + webSocket.getRemoteSocketAddress + "}")
          sockets.remove(webSocket)
        }

        override def onMessage(webSocket: WebSocket, msg: String): Unit = {
          if (CC_CoreConfig.is_debug) println(s"case websocket onMsg {$msg}")
          write(webSocket, "服务器收到消息")
        }

        override def onError(webSocket: WebSocket, e: Exception): Unit = sockets.remove(webSocket)

        override def onStart(): Unit = {}
      }
      socketServer.start()
      System.out.println("web socket complete -> " + port)
    }).start()
    this
  }

  /**
   * 获取所有的 socket 连接
   *
   * @return list[socket]
   */
  def getSockets: util.ArrayList[WebSocket] = sockets

  /**
   * 消息发送
   *
   * @param ws      连接对象
   * @param message 消息内容
   */
  def write(ws: WebSocket, message: String): Unit = ws.send(message)

  /**
   * 消息广播
   *
   * @param message 消息内容
   */
  def broadcast(message: String): Unit = sockets.forEach(socket => this.write(socket, message))

}

