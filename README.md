# Cloud-Castle

- The author is `月兔回旋于空中`

- Created in 2021-7-29

- 用于构建网络通道，实现网络转发或代理。

----

## Setting

openjdk-16.0.2

scala-sdk-3.0.1

gradle-7.0

----

## 云堡

#### 内网穿透

1. WebSocket内网转发(while)
    - Server
        - 服务注册中心
        - webSocket信息通道

    - Client
        - webSocket信息通道
        - socket网络转发

    - Readme : 利用WebSocket是最简单的实现方式，服务端部署成功后，用客户端进行连接，并建立webSocket通道， 当服务器接受到消息后将消息通过建立好的通道转发给客户端(基于注册中心进行分配)
      ，随后由客户端进行socket转发。该做法仅需要维护服务端注册中心，但需要主机提前部署服务端。

2. UDP hole punching (...)
    - Readme : 通过upd打洞实现内网穿透。需要解决net穿越问题。

3. ICMP tunnel(...)
    - Readme : 由于防火墙策略，常规协议非局域网内网直接无法进行通讯。可以通过icmp协议进行混淆欺骗防火墙实现通讯隧道。切没有深度数据包检测的情况下，这种类型的流量可能会被忽略掉。 

