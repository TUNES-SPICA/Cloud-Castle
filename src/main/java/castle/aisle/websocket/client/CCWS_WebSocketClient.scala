package castle.aisle.websocket.client

import castle.CC_CoreConfig
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake

import java.net.URI

class CCWS_WebSocketClient {

  def buildLinkWebSocket(host: String, port: Int): WebSocketClient = {
    val ws: WebSocketClient = new WebSocketClient(new URI("ws://" + host + ":" + port)) {

      override def onOpen(handshakes: ServerHandshake): Unit = if (CC_CoreConfig.is_debug) println(handshakes)

      override def onMessage(message: String): Unit = {
        if (CC_CoreConfig.is_debug) println(message)
        var part: Array[String] = message.split("<<id\n")
        if (part.length > 10) {
          // TODO socket转发

        }

      }

      override def onClose(code: Int, reason: String, remote: Boolean): Unit = if (CC_CoreConfig.is_debug) println(code + reason)

      override def onError(ex: Exception): Unit = if (CC_CoreConfig.is_debug) ex.printStackTrace()

    }
    ws.connect()
    if (ws.isOpen) throw new UnsupportedOperationException(s"ws://$host:$port 连接失败")
    ws
  }

}
