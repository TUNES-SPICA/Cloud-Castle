package castle.demo.mina;

import org.apache.mina.core.buffer.IoBuffer;

/**
 * 请求对象解析类
 * 
 * @author Chen.Hui
 * 
 */
public class InfoReqContainer {
	private byte tag;
	private short headlen;
	private String filename;
	private int filelen;
	private int offset;
	private String temp;
	private String checksum;
	private IoBuffer data;

	public byte getTag() {
		return tag;
	}

	public void setTag(byte tag) {
		this.tag = tag;
	}

	public short getHeadlen() {
		return headlen;
	}

	public void setHeadlen(short headlen) {
		this.headlen = headlen;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public int getFilelen() {
		return filelen;
	}

	public void setFilelen(int filelen) {
		this.filelen = filelen;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}

	public String getChecksum() {
		return checksum;
	}

	public void setChecksum(String checksum) {
		this.checksum = checksum;
	}

	public IoBuffer getData() {
		return data;
	}

	public void setData(IoBuffer data) {
		this.data = data;
	}

}