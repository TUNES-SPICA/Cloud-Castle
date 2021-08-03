package castle.aisle.websocket.client

import org.jline.utils.InputStreamReader

import java.io.{BufferedReader, ByteArrayInputStream, InputStream, PrintWriter}
import java.net.{InetSocketAddress, Socket, SocketAddress}

class CCWS_SocketClient {


  def buildServer(): Unit = {
    var socket: Socket = new Socket("127.0.0.1", 12300)
    var str = "123456789abc"
    var br: BufferedReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(str.getBytes())))
    var out: PrintWriter = new PrintWriter(socket.getOutputStream)
    while (true) {
      var s: String = br.readLine()
      out.println(s)
      out.flush()
    }
  }

}
