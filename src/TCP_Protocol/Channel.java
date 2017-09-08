package TCP_Protocol;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

/**
 * 
 * @author Yuan
 * Channel module
 *
 */
public class Channel {
	SocketChannel scTS = null;
	SocketChannel scFS = null;
	SocketChannel scTR = null;
	SocketChannel scFR = null;
	ServerSocketChannel sscFR = null;
	ServerSocketChannel sscFS = null;
	Packet packet = null;
	Packet ackPacket = null;
	Packet unknownPacket = null;
	Selector selector = null;
	ByteBuffer bfTS = null;
	ByteBuffer bfTR = null;
	Random rand = new Random();
	public Channel(int csinPort, int csoutPort, int rinPort, int croutPort, int crinPort, int sinPort, double packetLossRate)
			throws IOException {
		selector = Selector.open();
		// Channel from Sender
		sscFS = ServerSocketChannel.open();
		sscFS.bind(new InetSocketAddress("localhost", csinPort));
		System.out.println("SCFS is listening on Port " + csinPort);
		scFS = sscFS.accept();
		System.out.println("SSFS has accepted the request from sender");
		scFS.configureBlocking(false);
		scFS.register(selector, SelectionKey.OP_READ);
		// Channel to Receiver
		scTR = SocketChannel.open();
		scTR.bind(new InetSocketAddress(croutPort));
		scTR.connect(new InetSocketAddress("localhost", rinPort));
		System.out.println("SCTR is connecting to Receiver");
		if (scTR.finishConnect()) {
			System.out.println("SCTR has connected to receriver");
		}
		// Channel from Receiver
		sscFR = ServerSocketChannel.open();
		sscFR.bind(new InetSocketAddress("localhost", crinPort));
		System.out.println("sscFR is listening on Port " + crinPort);
		scFR = sscFR.accept();
		System.out.println("SCFR has accepted the request from receiver");
		scFR.configureBlocking(false);
		scFR.register(selector, SelectionKey.OP_READ);
		// Channel to Sender
		scTS = SocketChannel.open();
		scTS.bind(new InetSocketAddress(csoutPort));
		scTS.connect(new InetSocketAddress("localhost", sinPort));
		System.out.println("SCTS is connectiog to sender");
		if (scTS.finishConnect()) {
			System.out.println("SCTS has connected to sender");
		}

		while (!scFS.isConnected()||!scFR.isConnected()||scTS.isConnected()||scTR.isConnected()) {
			selector.select();
			Set<SelectionKey> selectionKeys = selector.selectedKeys();
			Iterator<SelectionKey> it = selectionKeys.iterator();
			while (it.hasNext()) {
				SelectionKey sk = it.next();
				it.remove();
				if (sk.isReadable()) {
					ByteBuffer bb = ByteBuffer.allocate(1000);
					int result = ((SocketChannel) sk.channel()).read(bb);
					if (result != 0) {
						unknownPacket = UtilFunction.bToP(bb.array());
						//data packet
						if (unknownPacket.getType() == Packet.PTYPE_DATA) {
							packet = unknownPacket;
							if(packet.getMagicno() != 0X497E || rand.nextDouble() < packetLossRate){
								break;
							}else if (rand.nextDouble() < 0.1){
								packet.setDataLen(packet.getDataLen() + rand.nextInt(10) + 1);
							}  
							int size1 = UtilFunction.sendPacket(scTR, packet);
							System.out.println("packet from channel to receiver, size is "+ size1);
	
						}else if (unknownPacket.getType() == Packet.PTYPE_ACK){
							ackPacket = unknownPacket;
							if(ackPacket.getMagicno() != 0X497E || rand.nextDouble() < packetLossRate){
								break;
							}else if (rand.nextDouble() < 0.1){
								ackPacket.setDataLen(ackPacket.getDataLen() + rand.nextInt(10) + 1);
							}
							int size2 = UtilFunction.sendPacket(scTS, ackPacket);
							System.out.println("packet from channel to sender, size is "+ size2);
						}
							 
					}

				}
			}
		}
		if(sscFR!=null)
			sscFR.close();
		if(sscFS!=null)
			sscFS.close();
		if(scFR!=null)
			scFR.close();
		if(scTR!=null)
			scTR.close();
		if(scFS!=null)
			scFS.close();
		if(scTS!=null)
			scTS.close();
		if(selector!=null)
			selector.close();
		
	}
	
	/*
	 * main function for testing
	 */

//	public static void main(String[] args) {
//		int csinPort = 7001;
//		int csoutPort = 7002;
//		int sinPort = 7006;
//		int rinPort = 7005;
//		int croutPort = 7004;
//		int crinPort = 7003;
//		int routPort = 7008;
//		int soutPort = 7007;
//		double packetLossRate = 0.1;
//		try {
//			Channel channel = new Channel(csinPort, csoutPort, rinPort, croutPort, crinPort, sinPort, packetLossRate);
//		} catch (IOException e) {
//			System.out.println("channel closed!");
//		}
//	}
}
