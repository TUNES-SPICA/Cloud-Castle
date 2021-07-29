package castle.mina

object MinaServe extends Runnable {
  def main(args: Array[String]): Unit = System.out.println()

  override def run(): Unit = buildServer();

  def buildServer(): Unit = buildServer("127.0.0.1", 8001);

  def buildServer(host: String, port: Int): Unit = {

  }
}