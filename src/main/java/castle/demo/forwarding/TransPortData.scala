package castle.demo.forwarding

import java.net.Socket

/**
 * Socket双端口数据交互
 *
 * @author ran
 */
class TransPortData(var getDataSocket: Socket, var putDataSocket: Socket, var `type`: String) extends Thread {
  override def run(): Unit = try {
    val in = getDataSocket.getInputStream
    val out = putDataSocket.getOutputStream
    try while ( {
      true
    }) { //读入数据
      val data = new Array[Byte](1024)
      val readLen = in.read(data)
      //如果没有数据，则暂停
      if (readLen > 0) {
        out.write(data, 0, readLen)
        out.flush()
      }
    }
    catch {
      case e: Exception =>
        e.printStackTrace()
    } finally {
      if (in != null) in.close()
      if (out != null) out.close()
    }
  }
}