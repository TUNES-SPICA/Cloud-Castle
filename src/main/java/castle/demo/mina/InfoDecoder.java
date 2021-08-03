package castle.demo.mina;

import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.demux.MessageDecoder;
import org.apache.mina.filter.codec.demux.MessageDecoderResult;

/**
 * 解码器<p>
 * 解码器与之类似，解码器在这里的作用主要用户服务器端解码：
 *
 * @author ChenHui
 */
public class InfoDecoder implements MessageDecoder {

    private Charset charset;

    public InfoDecoder(Charset charset) {
        this.charset = charset;
    }

    @Override
    public MessageDecoderResult decodable(IoSession session, IoBuffer in) {

        //System.out.println("package size:"+in.remaining());
        // 报头长度<56
        if (in.remaining() < 56) {
            return MessageDecoderResult.NEED_DATA;
        }

        byte tag = in.get();
        short head_len = in.getShort();

        if (tag == (short) 0x01) {
            System.out.println("请求标识符：" + tag + " head length:" + head_len);
        } else {
            //System.out.println("未知标识符...");
            return MessageDecoderResult.NOT_OK;
        }

        return MessageDecoderResult.OK;
    }

    @Override
    public MessageDecoderResult decode(IoSession session, IoBuffer in,
                                       ProtocolDecoderOutput out) throws Exception {
        byte tag = in.get();

        if (tag == 0x01) {
            InfoReqContainer irc = new InfoReqContainer();
            irc.setTag(tag);
            irc.setHeadlen(in.getShort());
            irc.setFilename(in.getString(36, charset.newDecoder()));
            irc.setFilelen(in.getInt());
            irc.setOffset(in.getInt());
            irc.setChecksum(in.getString(4, charset.newDecoder()));
            irc.setTemp(in.getString(5, charset.newDecoder()));//应该用head len-53
            irc.setData(in);

            out.write(irc);
        }

        return MessageDecoderResult.OK;
    }

    @Override
    public void finishDecode(IoSession session, ProtocolDecoderOutput out)
            throws Exception {
        // TODO Auto-generated method stub

    }

}