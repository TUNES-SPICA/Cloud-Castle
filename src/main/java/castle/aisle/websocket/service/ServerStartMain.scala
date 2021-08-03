package castle.aisle.websocket.service

object ServerStartMain {

  def main(args: Array[String]): Unit = {

    new CC_SocketServer().buildSocketServer(8080)
  }

}
