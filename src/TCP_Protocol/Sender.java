package TCP_Protocol;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * 
 * @author Yuan
 * Sender module
 */
public class Sender {
	public static final int DATASIZE = 512;
	public static final int TIMEOUT = 200;
	static Scanner scan = new Scanner(System.in);
	int next = 0;
	public Sender(File file, int sinPort, int soutPort, int csinPort) throws IOException{
		ServerSocketChannel sscFC = null;
		SocketChannel scFC = null;
		SocketChannel scTC = null;
		Packet[] packets = null;
		Packet packet = null;
		Packet acknowledgementPacket = null;
		Selector selector = null;
		selector = Selector.open();
		int packetNum = 0;
		int totalPacketSendNo = 0;
		boolean exitFlag = true;
		packets = splitFile(file);
		sscFC = ServerSocketChannel.open();
		sscFC.bind(new InetSocketAddress("localhost", sinPort));
		System.out.println("SCFC is listening on Port " + sinPort);
		scTC = SocketChannel.open();
		scTC.bind(new InetSocketAddress(soutPort));
		scTC.connect(new InetSocketAddress("localhost", csinPort));
		System.out.println("SCTC is connecting to channel");
		if(scTC.finishConnect()){
			System.out.println("SCTC has connected to channel");
		}

		
		scFC = sscFC.accept();
		System.out.println("SCFC has accepted the request from channel");
		scFC.configureBlocking(false);
		scFC.register(selector, SelectionKey.OP_READ);
		
		while(exitFlag){
			selector.select(TIMEOUT);
			Set<SelectionKey> selectionKeys = selector.selectedKeys();
			Iterator<SelectionKey> it = selectionKeys.iterator();
			while(it.hasNext()){
				SelectionKey sk = it.next();
				it.remove();
				if(sk.isReadable()){
					ByteBuffer bb = ByteBuffer.allocate(1000);
					int result = scFC.read(bb);
					if(result != 0){
							
					acknowledgementPacket = UtilFunction.bToP(bb.array());
					if(checkResponse(packet, acknowledgementPacket)){
						packetNum++;
						
						if(packetNum == packets.length){
							exitFlag = false;
							break;
						}
						next = 1-next;
						packets[packetNum].setSeqno(next);
					
					}
					
				}
			}

			}
			if(exitFlag){
			packet = packets[packetNum];
			packet.setChecksum(UtilFunction.myCheckSum(packet));
			int i = UtilFunction.sendPacket(scTC, packet);
			totalPacketSendNo++;
			System.out.println(totalPacketSendNo);
			}
		}
		if(selector!=null)
		selector.close();
		if(sscFC!=null)
		sscFC.close();
		if(scFC!=null)
		scFC.close();
		if(scTC!=null)
		scTC.close();
		System.out.println("total sending packet number is " + totalPacketSendNo);
	}


	public Packet[] splitFile(File file) throws IOException {
		long fileLength = file.length();
		int count = 0;
		int numOfPackets = (int) Math.ceil((double) fileLength / DATASIZE);
		Packet[] packets = new Packet[numOfPackets + 1];
		RandomAccessFile raf = new RandomAccessFile(file, "r");
		
		int result = 0;
		while (true) {
			byte[] b = new byte[DATASIZE];
			if((count+1) * DATASIZE > fileLength && count*DATASIZE != fileLength){
				b = new byte[(int) (fileLength%DATASIZE)];
			}
			result = raf.read(b);
			if(result == -1){
				break;
			}
			packets[count] = new Packet(Packet.PTYPE_DATA, 0, DATASIZE);
			packets[count].setDataLen(result);
			packets[count].setData(b);
			packets[count].setNo(count);
			System.out.println(packets[count]);
			count++;
			
		}
		packets[numOfPackets] = new Packet(Packet.PTYPE_DATA, 0, 0);
		packets[numOfPackets].setNo(numOfPackets);
		if(raf!=null)
		raf.close();
		return packets;
		
	}
	
	public boolean checkResponse(Packet packet, Packet acknowledgementPacket) {
		if(acknowledgementPacket.getMagicno() == 0x497E && acknowledgementPacket.getType()== Packet.PTYPE_ACK && acknowledgementPacket.getDataLen() == 0 && acknowledgementPacket.getSeqno() == next){
			return true;
		}
		return false;
	}
	
}
