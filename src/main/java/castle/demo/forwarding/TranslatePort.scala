package castle.demo.forwarding

import java.net.{ServerSocket, Socket}


/**
 * Socket转发服务器<p>
 * 用于转发接受到的请求
 */
class TranslatePort {

  def buildCastle(): Unit = new Thread(() => {
    System.out.println(".....开始构架转发网关")
    buildCastle()
    val args = Array[String]("12102", "127.0.0.1", "12300")
    //获取本地监听端口、远程IP和远程端口
    val localPort = args(0).trim.toInt
    val remoteIp = args(1).trim
    val remotePort = args(2).trim.toInt
    //启动本地监听端口
    val serverSocket = new ServerSocket(localPort)
    System.out.println("localPort=" + localPort + ";remoteIp=" + remoteIp + ";remotePort=" + remotePort + ";启动本地监听端口" + localPort + "成功！")
    while ( {
      true
    }) try {
      val clientSocket = serverSocket.accept
      val remoteServerSocket = new Socket(remoteIp, remotePort)
      //获取客户端连接
      System.out.println("accept one client\ncreate remoteip and port success")
      //启动数据转换
      new TransPortData(remoteServerSocket, clientSocket, "2").start()
      new TransPortData(clientSocket, remoteServerSocket, "1").start()
    } catch {
      case ex: Exception =>
        ex.printStackTrace()
    }
  }).start()

}