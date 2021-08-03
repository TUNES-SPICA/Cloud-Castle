package castle.aisle.websocket.client

import castle.CC_CoreConfig
import castle.demo.forwarding.TranslatePort
import org.java_websocket.client.WebSocketClient

object ClientStartMain {

  def main(args: Array[String]): Unit = {
    new TranslatePort().buildCastle()

    val ws: WebSocketClient = new CCWS_WebSocketClient().buildLinkWebSocket(CC_CoreConfig.websocket_client_websocket_host, CC_CoreConfig.websocket_client_websocket_port)
    ws.connect()
    println(ws.isOpen)
    ws.onOpen(null)
    ws.send("s")
  }

}
