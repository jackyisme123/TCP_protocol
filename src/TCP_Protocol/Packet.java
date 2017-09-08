package TCP_Protocol;

import java.io.Serializable;

/**
 * 
 * @author Yuan
 *
 */

public class Packet implements Serializable{
	
	private static final long serialVersionUID = -7996935087676307689L;
	private int magicno;
	private int type;
	private int seqno;
	private int dataLen;
	private byte[] data;
	private int no = 0;
	private int checksum = 0;
	public static final int PTYPE_DATA = 0;
	public static final int PTYPE_ACK = 1;
	
	public int getMagicno() {
		return magicno;
	}
	public void setMagicno(int magicno) {
		this.magicno = magicno;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getSeqno() {
		return seqno;
	}
	public void setSeqno(int seqno) {
		this.seqno = seqno;
	}
	public int getDataLen() {
		return dataLen;
	}
	public void setDataLen(int dataLen) {
		this.dataLen = dataLen;
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	
	public int getChecksum() {
		return checksum;
	}
	public void setChecksum(int checksum) {
		this.checksum = checksum;
	}
	
	public Packet(int type, int seqno, int dataLen) {
		setMagicno(0x497E);
		this.type = type;
		this.seqno = seqno;
		//if data length < 0 or data length >512, drop packet
		if(dataLen>=0 && dataLen<=512){
			this.dataLen = dataLen;
		}else{
			System.out.println("data length must between 0 and 512");
		}
		data = new byte[dataLen];
		
	}
	@Override
	public String toString() {
		return "Packet [magicno=" + magicno + ", type=" + type + ", seqno=" + seqno + ", dataLen=" + dataLen + ", no="
				+ no + ", checksum=" + checksum + "]";
	}
	


}

