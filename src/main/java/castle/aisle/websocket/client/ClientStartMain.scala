package castle.aisle.websocket.client

import castle.CC_CoreConfig
import castle.aisle.websocket.vm.VirtualVM
import castle.demo.forwarding.TranslatePort
import castle.demo.socket.VirtualServe
import org.java_websocket.client.WebSocketClient

object ClientStartMain {

  def main(args: Array[String]): Unit = {
    starVM()
//    new TranslatePort().buildCastle()


//    val ws: WebSocketClient = new CCWS_WebSocketClient().buildLinkWebSocket(CC_CoreConfig.websocket_client_websocket_host, CC_CoreConfig.websocket_client_websocket_port)
//    println(ws.isOpen)
//    ws.onOpen(null)
//    ws.send("s")

//    new VirtualServe().start()
//    Thread.sleep(1000)
//    new CCWS_SocketClient().buildServer()
  }

  def starVM(): Unit = {
    new VirtualVM().webVM(12300)
  }

}
