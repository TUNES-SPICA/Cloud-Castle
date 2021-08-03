package castle

/**
 * Cloud-Castle 核心配置
 */
case object CC_CoreConfig {

  /**
   * 是否为debug模式（输出细节信息）
   */
  val is_debug: Boolean = true

  /**
   * websocket模式 服务端 socket服务器 端口号
   */
  val websocket_server_socket_port = 12100

  /**
   * websocket模式 服务端 socket服务器 最大连接数
   */
  var websocket_server_socket_backlog = 1024

  /**
   * websocket模式 服务端 websocket服务器 端口号
   */
  val websocket_server_websocket_port = 12101

  /**
   * websocket模式 服务端 socket服务器 端口号
   */
  val websocket_client_socket_port = 12200

  /**
   * websocket模式 客户端 websocket连接 主机地址
   */
  val websocket_client_websocket_host = "127.0.0.1"

  /**
   * websocket模式 客户端 websocket连接 端口号
   */
  val websocket_client_websocket_port = 12101


}

