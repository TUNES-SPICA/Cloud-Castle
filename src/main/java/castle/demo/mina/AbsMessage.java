package castle.demo.mina;

import org.apache.mina.core.buffer.IoBuffer;

/**
 * 通信协议
 * <p>协议格式：</p>
 * <p>tag | header length | Filename | File length | offset | checksum | temps | data</p>
 *
 * @author Chen.Hui
 */
public abstract class AbsMessage {

    /**
     * 请求或访问类型 请求Tag：0x00 返回Tag:0x01 共 8 bit
     */
    public abstract byte getTag();

    /**
     * 头文件长度 共 2^16 可表示 65535
     */
    public abstract short getHeaderlen();

    /**
     * 根据UUID生成文件唯一标识，共 8*36=288 bit
     */
    public abstract byte[] getFilename();//需要設計一個算法

    /**
     * 获取文件长度 2^32=4GB 共 32 bit
     */
    public abstract int getFileLen();

    /**
     * 获取文件的偏移量offset 共 32 bit
     */
    public abstract int getOffset();

    /**
     * 获取文件的MD5校验码 共 32 bit
     */
    public abstract byte[] getChecksum();

    /**
     * 预留字段 长度不超过 128 bit
     */
    public abstract byte[] getTmp();

    /**
     * data 方式传输内容 不超过1024bit
     */
    public abstract IoBuffer getData();

}