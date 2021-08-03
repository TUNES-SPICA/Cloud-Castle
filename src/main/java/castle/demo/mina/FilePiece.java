package castle.demo.mina;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.UUID;

import org.apache.mina.core.buffer.IoBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 分片文件操作类<p>
 * 业务对象代码，RandomAccessFile可用于随机读写，用于文件的切片，这里还用了管道，主要目的是为了加快读写速度：
 *
 * @author Chen.Hui
 */
public class FilePiece {

    Logger logger = LoggerFactory.getLogger(FilePiece.class);

    private ByteBuffer[] dsts;

    private IoBuffer buf;

    private String filename;

    private FileChannel fc;

    private RandomAccessFile raf;

    private int offset;

    private String checksum;

    /**
     * 构建文件的基本信息
     */
    public FilePiece(String path, int offset) throws Exception {

        raf = new RandomAccessFile(new File(path), "rw");
        fc = raf.getChannel();

        this.offset = offset;

        dsts = new ByteBuffer[1024];

        for (int i = 0; i < dsts.length; i++) {
            dsts[i] = ByteBuffer.allocate(1024);
        }

        fc.read(dsts, offset, 1024);


        buf = IoBuffer.allocate(1024);

        filename = UUID.randomUUID().toString();
        logger.info("has built:" + filename + " filename size"
                + filename.length());

    }

    /**
     * 这个方法还有点儿问题，数据取的不对
     */
    public IoBuffer getBuf() {
        dsts[0].flip();
        while (dsts[0].hasRemaining()) {
            buf.putChar(dsts[0].getChar());
        }
        buf.flip();
        return buf;
    }

    public String getFilename() {
        return filename;
    }

    public FileChannel getFc() {
        return fc;
    }

    public RandomAccessFile getRaf() {
        return raf;
    }

    public int getOffset() {
        return offset;
    }

    public String getChecksum() {
        // TODO checksum algorithems
        return "aaaa";
    }
}