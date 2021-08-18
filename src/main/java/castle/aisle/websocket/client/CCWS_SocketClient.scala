package castle.aisle.websocket.client

import org.jline.utils.InputStreamReader

import java.io.{BufferedReader, PrintWriter, Reader}
import java.net.{InetAddress, Socket}

class CCWS_SocketClient {


  def buildServer(): Unit = {
    val socket = new Socket("127.0.0.1", 80)
    val out = new PrintWriter(socket.getOutputStream, true)

    val in = new BufferedReader(new InputStreamReader(socket.getInputStream))
    // send an HTTP request to the web server
    out.println("GET / HTTP/1.1")
    //        out.println("Host: 127.0.0.1:8080");
    out.println("Host: www.baidu.com:80")
    out.println("Connection: Close")
    out.println()

    // read the response
    var loop = true
    val sb = new StringBuilder(8096)
    val sb2 = new StringBuilder(8096)
    while ( {
      loop
    }) if (in.ready) {
      var i = in.read
      while ( {
        i != -1
      }) {
        sb.append(i.toChar)
        sb2.append(i).append("|")
        i = in.read
      }
      loop = false
    }
    System.out.println(" -> " + sb.toString)
    System.out.println(" -> " + sb2.toString)
    socket.close()
  }

}
