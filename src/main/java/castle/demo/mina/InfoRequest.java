package castle.demo.mina;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 请求对象<p>
 * 这里的业务对象主要功能是将文件切片
 *
 * @author Chen.Hui
 */
public class InfoRequest extends AbsMessage {

    Logger logger = LoggerFactory.getLogger(InfoRequest.class);

    FilePiece piece;

    Charset charset;

    public InfoRequest(FilePiece piece) {
        this.piece = piece;
    }

    public InfoRequest() {
        //empty
    }

    @Override
    public byte getTag() {// 0x01 请求包
        return (byte) 0x01;
    }

    @Override
    public short getHeaderlen() {
        if (getTmp() == null) {
            short len = (short) (1 + 2 + 36 + 4 + 4 + 4);
            return len;
        } else {
            short len = (short) (1 + 2 + 36 + 4 + 4 + 4 + (short) getTmp().length);
            return len;
        }
    }

    @Override
    public int getFileLen() {// 文件总长度

        try {
            return (int) piece.getFc().size();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int getOffset() {// 传输 偏移量

        return piece.getOffset();

    }

    @Override
    public byte[] getFilename() {// 文件名称

        /** check the bits of name */
        byte[] name = new byte[36];
        name = piece.getFilename().getBytes();

        return name;

    }

    @Override
    public byte[] getChecksum() {// checksum

        byte[] checksum = new byte[4];
        checksum = piece.getChecksum().getBytes();
        return checksum;
    }

    @Override
    public byte[] getTmp() {
        byte[] b = new byte[5];
        return b;
    }

    @Override
    public IoBuffer getData() {
        return piece.getBuf();
    }
}