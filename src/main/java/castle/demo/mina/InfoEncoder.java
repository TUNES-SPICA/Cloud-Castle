package castle.demo.mina;

import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.apache.mina.filter.codec.demux.MessageEncoder;

/**
 * 编码器<p>
 * 编码器的作用就是讲数据装换成用于传输的流，在Mina中这种流就是IoBuffer：
 *
 * @author Chen.Hui
 */
public class InfoEncoder implements MessageEncoder<AbsMessage> {

    private Charset charset;

    public InfoEncoder(Charset charset) {
        this.charset = charset;
    }

    @Override
    public void encode(IoSession session, AbsMessage message,
                       ProtocolEncoderOutput out) throws Exception {

        IoBuffer buf = IoBuffer.allocate(1024).setAutoExpand(true);

        if (message instanceof InfoRequest) {

            InfoRequest req = (InfoRequest) message;
            buf.put(req.getTag());
            buf.putShort((short) req.getHeaderlen());
            buf.put(req.getFilename());
            buf.putInt(req.getFileLen());
            buf.putInt(req.getOffset());
            buf.put(req.getChecksum());
            buf.put(req.getTmp());
            buf.put(req.getData());

        }

        buf.flip();

        out.write(buf);
    }
}