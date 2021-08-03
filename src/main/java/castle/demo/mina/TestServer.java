package castle.demo.mina;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSessionConfig;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LogLevel;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;



public class TestServer{

	public static void main(String[] args) {
		IoAcceptor acceptor = null;
		try {
			// 创建一个非阻塞的server端的Socket
			acceptor = new NioSocketAcceptor();

			// 设置过滤器（添加自带的编解码器）
			acceptor.getFilterChain().addLast(
					"codec",
					new ProtocolCodecFilter(new InfoCodecFactory(
							new InfoDecoder(StandardCharsets.UTF_8),
							new InfoEncoder(StandardCharsets.UTF_8))));
			// 设置日志过滤器
			LoggingFilter lf = new LoggingFilter();
			lf.setMessageReceivedLogLevel(LogLevel.DEBUG);
			acceptor.getFilterChain().addLast("logger", lf);
			// 获得IoSessionConfig对象
			IoSessionConfig cfg = acceptor.getSessionConfig();
			// 读写通道10秒内无操作进入空闲状态
			cfg.setIdleTime(IdleStatus.BOTH_IDLE, 100);

			// 绑定逻辑处理器
			acceptor.setHandler(new ServerHandler());
			// 绑定端口
			int PORT = 8082;
			acceptor.bind(new InetSocketAddress(PORT));
			System.out.println("成功开启服务器端...");
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
}  