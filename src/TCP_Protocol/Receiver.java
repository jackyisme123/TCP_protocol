package TCP_Protocol;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;

/**
 * 
 * @author Yuan
 * Receiver Module
 *
 */
public class Receiver {

	ServerSocketChannel sscFC = null;
	SocketChannel scFC = null;
	SocketChannel scTC = null;
	Packet packet = null;
	Packet ackPacket = null;
	ArrayList<Packet> packets = new ArrayList<Packet>();
	int expected = 0;
	int num = -1;
	public Receiver(int rinPort, int routPort, int crinPort, String fileName) throws IOException{
	//Receiver from Channel
	sscFC = ServerSocketChannel.open();
	sscFC.bind(new InetSocketAddress("localhost", rinPort));
	System.out.println("SCFC is listening on port "+ rinPort);
	scFC = sscFC.accept();
	System.out.println("SCFC has accepted the request from channel");
	//Receiver to Channel;
	scTC = SocketChannel.open();
	scTC.bind(new InetSocketAddress(routPort));
	scTC.connect(new InetSocketAddress("localhost", crinPort));
	System.out.println("SCTC is connecting to channel");
	if(scTC.finishConnect()){
		System.out.println("SCTC has connected to channel");
	}
	
	while(true){
		packet = UtilFunction.getPacket(scFC);
		if(packet.getDataLen() == 0){
			ackPacket = UtilFunction.pToAck(packet);
			UtilFunction.sendPacket(scTC, ackPacket);
			break;
		}else if(packet.getMagicno()!=0x497E || packet.getType() != Packet.PTYPE_DATA || packet.getChecksum() != UtilFunction.myCheckSum(packet)){
			continue;
		}else if(packet.getSeqno() != expected){
			ackPacket = UtilFunction.pToAck(packet);
			UtilFunction.sendPacket(scTC, ackPacket);
		}else{
			System.out.println(num+","+packet.getNo());
			if(num != packet.getNo()){
				System.out.println(packet.getData().length);
				packets.add(packet);
				
				num = packet.getNo();
				ackPacket = UtilFunction.pToAck(packet);
				UtilFunction.sendPacket(scTC, ackPacket);
				expected = 1- expected;
			}
		}
	}
	if(sscFC!=null)
	sscFC.close();
	if(scFC!=null)
	scFC.close();
	if(scTC!=null)
	scTC.close();
	
	UtilFunction.psToFile(packets, fileName);
	}
	

}
