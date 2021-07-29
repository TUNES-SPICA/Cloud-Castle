package castle.mina

object StartMain extends Build {

  /**
   * 基于mina的网络通讯demo
   *
   * @param args unimportant
   */
  def main(args: Array[String]): Unit = {
    print(buildServeHost)
    var client: MinaClient = new MinaClient;
    client.start();
  }

}

class Build {

  private final var HOST: String = "127.0.0.1";
  private final var PORT: Integer = 8001;

  /**
   * @return 服务端URL
   */
  protected def buildServeHost: String = {
    HOST + ":" + PORT;
  }

}