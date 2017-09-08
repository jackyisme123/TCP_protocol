package TCP_Protocol;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;

/**
 * 
 * @author Yuan
 *
 */

public class UtilFunction {
	

	public static byte[] pToB (Packet packet) throws IOException{
		byte[] bytes = null;
		ByteArrayOutputStream bo = new ByteArrayOutputStream();  
        ObjectOutputStream oo = new ObjectOutputStream(bo);  
        oo.writeObject(packet);  
        oo.flush();
        bytes = bo.toByteArray();
		bo.close();
		oo.close();
		return bytes;
		
	}
	
	public static Packet bToP (byte[] bytes) throws IOException{
		
		ByteArrayInputStream bis = new ByteArrayInputStream (bytes);       
        ObjectInputStream ois = new ObjectInputStream (bis);       
        Packet packet = null;
		try {
			packet = (Packet) ois.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}     
        ois.close();  
        bis.close();
		return packet;
		
	}
	
	public static int sendPacket(SocketChannel sc, Packet packet) throws IOException{
		byte[] bytes = pToB(packet);
		ByteBuffer bb = ByteBuffer.wrap(bytes);
		int result = sc.write(bb);
		return result;
	}
	
	public static Packet getPacket(SocketChannel sc) throws IOException{
		ByteBuffer bb = ByteBuffer.allocate(1000);
		int result = sc.read(bb);
//		System.out.println("read "+result+" bytes");
		Packet packet = bToP(bb.array());
		return packet;
	}
	
	public static Packet pToAck(Packet packet){
		Packet ackPacket = new Packet(Packet.PTYPE_ACK, packet.getSeqno(), 0);		
		return ackPacket;
		
	}
	
	public static void psToFile(ArrayList<Packet> packets, String fileName) throws IOException{
		byte[] bytes;
		File file = new File(fileName);
		RandomAccessFile raf = new RandomAccessFile(file, "rw");
		for(int i = 0; i<packets.size(); i++){
			bytes = packets.get(i).getData();
			raf.write(bytes);
		}
		if(raf!=null)
		raf.close();
	}
	
	public static int myCheckSum(Packet packet){
		return packet.getData().length+packet.getDataLen()+packet.getMagicno()+packet.getNo()+packet.getSeqno()+packet.getType();
	}	
}
