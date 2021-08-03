package castle.demo.socket;

/**
 * <b>核心启动</b>
 *
 * @author ran
 * @apiNote 利用socket实现请求转发功能，尚未成型
 * @date 2021年7月8日22:10:07
 */
public class CoreStartMain {

    /**
     * <b style='color:#66CCFF'>Socket转发demo</b>
     * <p>利用介于应用层与传输控制之间的socket协议，对请求实现转发
     * <p>此处已完成，但是目前使用的是BIO的形式，以及目前只能实现内网转发到内网，待处理
     *
     * @param args no
     * @create 2021年7月8日22:06:51
     * @apiNote 未成形
     */
    public static void main(String[] args) {
        // 构建虚拟主机
        new VirtualServe().start();
        // 构建socket转发网络
//        new TranslatePort().start();
    }
}
