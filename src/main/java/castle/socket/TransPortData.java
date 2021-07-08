package castle.socket;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * <b>Socket双端口数据交互</b>
 *
 * @author ran
 * @date 2021年7月8日22:15:05
 * @apiNote ...
 */
@SuppressWarnings("jol")
public class TransPortData extends Thread {

    Socket getDataSocket;
    Socket putDataSocket;

    String type;

    public TransPortData(Socket getDataSocket, Socket putDataSocket, String type) {
        this.getDataSocket = getDataSocket;
        this.putDataSocket = putDataSocket;
        this.type = type;
    }

    @Override
    public void run() {
        try {
            while (true) {
                InputStream in = getDataSocket.getInputStream();
                OutputStream out = putDataSocket.getOutputStream();
                //读入数据
                byte[] data = new byte[1024];
                int readLen = in.read(data);
                //如果没有数据，则暂停
                if (readLen > 0) {
                    out.write(data, 0, readLen);
                    out.flush();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (putDataSocket != null) {
                    putDataSocket.close();
                }
                if (getDataSocket != null) {
                    getDataSocket.close();
                }
            } catch (Exception exx) {
                exx.printStackTrace();
            }
        }
    }

}