package castle.aisle.websocket.client

import castle.util.UtilStrTo
import org.jline.utils.InputStreamReader

import java.io.{BufferedOutputStream, BufferedReader, BufferedWriter, ByteArrayInputStream, DataInputStream, InputStream, PrintStream, PrintWriter}
import java.net.{InetSocketAddress, Socket, SocketAddress}
import java.nio.charset.StandardCharsets
import java.util.Scanner
import scala.util.control.Breaks.breakable

class CCWS_SocketClient {


  def buildServer(): Unit = {
    var socket: Socket = new Socket("127.0.0.1", 8080)
    var sc: Scanner = new Scanner(System.in)
    var b = true;
    while (true) {
      var out = new PrintWriter(socket.getOutputStream)
      val data: String = sc.nextLine()
      if ("exit".equals(data)) return
      out.print("GET http://127.0.0.1:12300/ HTTP/1.1\r\nHost: 127.0.0.1:12300\r\nConnection: keep-alive\r\nsec-ch-ua: \"Chromium\";v=\"92\", \" Not A;Brand\";v=\"99\", \"Google Chrome\";v=\"92\"\r\nsec-ch-ua-mobile: ?0\r\nUpgrade-Insecure-Requests: 1\r\nUser-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.107 Safari/537.36\r\nAccept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9\r\nSec-Fetch-Site: none\r\nSec-Fetch-Mode: navigate\r\nSec-Fetch-User: ?1\r\nSec-Fetch-Dest: document\r\nAccept-Encoding: gzip, deflate, br\r\nAccept-Language: en-GB,en-US;q=0.9,en;q=0.8,zh-TW;q=0.7,zh-CN;q=0.6,zh;q=0.5\r\n")

      out.flush()

      val byte = new Array[Byte](1)

      val inputStream = socket.getInputStream
      var b: Int = inputStream.read
      val info = new StringBuilder
      while (b != -1) {
        info.append(b.asInstanceOf[Char])
        try b = inputStream.read()
        catch {
          case e: Exception => b= -1
        }
      }
      println(info.toString())
      //      while (b) {
      //
      //        if (inputStream.available() > 0) {
      //          inputStream.read(byte)
      //          info.append(UtilStrTo.HexStrToStr(UtilStrTo.ByteArrayToHexStr(byte)))
      //          if (inputStream.available() == 0) {
      //            b = false
      //            println(info.toString())
      //          }
      //        }
      //      }
    }
  }

}
