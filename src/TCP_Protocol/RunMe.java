package TCP_Protocol;

import java.io.File;
import java.util.Scanner;

/**
 * 
 * @author Yuan
 *
 */
public class RunMe {
	public static void main(String[] args) {
		int sinPort = 0;
		int soutPort = 0;
		int csinPort = 0;
		int crinPort = 0;
		int csoutPort = 0;
		int croutPort = 0;
		int rinPort = 0;
		int routPort = 0;
		double packetLossRate = 0.0;
		String filePathRead = null;
		String filePathWrite = null;
		File fileRead = null;
		
/******************************************/
/*
 * code for testing
 */
//		sinPort = 7001;
//		soutPort = 7002;
//		csinPort = 7003;
//		crinPort = 7004;
//		csoutPort = 7005;
//		croutPort = 7006;
//		rinPort = 7007;
//		routPort = 7008;
//		packetLossRate = 0.3;
//		filePathRead = "null.txt";
//		filePathWrite = "1.txt";
//		fileRead = new File(filePathRead);
/******************************************/
		
		Scanner scan = new Scanner(System.in);
		System.out.println("please enter the sender in from channel port No. from 1024 to 64000");
		String sinPortStr = scan.next();
		while(true){
			try{
				sinPort = Integer.parseInt(sinPortStr);
				if(sinPort<1024 || sinPort>64000){
					throw new RuntimeException("Port number must be an integer and its range between 1024 and 64000!");
				}
				break;
			}catch(Exception e){
				System.out.println("Wrong Port Number! please enter the sender in from channel port No. from 1024 to 64000");
				sinPortStr = scan.next();
			}
			
		}
		
		
		System.out.println("please enter the sender out to channel port No. from 1024 to 64000");
		String soutPortStr = scan.next();
		while(true){
			try{
			soutPort = Integer.parseInt(soutPortStr);
			if(soutPort<1024 || soutPort>64000){
				throw new RuntimeException("Port number must be an integer and its range between 1024 and 64000!");
				}
			break;
				}catch(Exception e){
					System.out.println("Wrong Port Number! please enter the sender out to channel port No. from 1024 to 64000");
					soutPortStr = scan.next();
				}
		
		}
		System.out.println("please enter the channel in from sender port No. from 1024 to 64000");
		String csinPortStr = scan.next();
		while(true){
			try{
				csinPort = Integer.parseInt(csinPortStr);
				if(csinPort<1024 || csinPort>64000){
					throw new RuntimeException("Port number must be an integer and its range between 1024 and 64000!");
			}
				break;
			}catch(Exception e){
				System.out.println("Wrong Port Number! please enter the channel in from sender port No. from 1024 to 64000");
				csinPortStr = scan.next();
			}
		}

		System.out.println("please enter the channel in from receiver port No. from 1024 to 64000");
		String crinPortStr = scan.next();
		while(true){
			try{
				crinPort = Integer.parseInt(crinPortStr);
				if(crinPort<1024 || crinPort>64000){
					throw new RuntimeException("Port number must be an integer and its range between 1024 and 64000!");
				}
				break;
			}catch(Exception e){
				System.out.println("Wrong Port Number! please enter the channel in from receiver port No. from 1024 to 64000");
				crinPortStr = scan.next();
			}
		}
		System.out.println("please enter the channel out to sender port No. from 1024 to 64000");
		String csoutPortStr = scan.next();
		while(true){
			try{
				csoutPort = Integer.parseInt(csoutPortStr);
				if(csoutPort<1024 || csoutPort>64000){
					throw new RuntimeException("Port number must be an integer and its range between 1024 and 64000!");
				}
				break;
			}catch(Exception e){
				System.out.println("Wrong Port Number! please enter the channel out to sender port No. from 1024 to 64000");
				csoutPortStr = scan.next();
			}
		}
		
		
		System.out.println("please enter the channel out to receiver port No. from 1024 to 64000");
		String croutPortStr = scan.next();
		while(true){
			try{
				croutPort = Integer.parseInt(croutPortStr);
				if(croutPort<1024 || croutPort>64000){
					throw new RuntimeException("Port number must be an integer and its range between 1024 and 64000!");
				}
				break;
			}catch(Exception e){
				System.out.println("Wrong Port Number! please enter the channel out to receiver port No. from 1024 to 64000");
				croutPortStr = scan.next();
			}
		}
		
		System.out.println("please enter the reciever in from channel port No. from 1024 to 64000");
		String rinPortStr = scan.next();
		while(true){
			try{
				rinPort = Integer.parseInt(rinPortStr);
				if(rinPort<1024 || rinPort>64000){
					throw new RuntimeException("Port number must be an integer and its range between 1024 and 64000!");
				}
				break;
			}catch(Exception e){
				System.out.println("Wrong Port Number! please enter the reciever in from channel port No. from 1024 to 64000");
				rinPortStr = scan.next();
			}
		}
		
		System.out.println("please enter the reciever out to channel port No. from 1024 to 64000");
		String routPortStr = scan.next();
		while(true){
			try{
				routPort = Integer.parseInt(routPortStr);
				if(routPort<1024 || routPort>64000){
					throw new RuntimeException("Port number must be an integer and its range between 1024 and 64000!");
				}
				break;
			}catch(Exception e){
				System.out.println("Wrong Port Number! please enter the reciever out to channel port No. from 1024 to 64000");
				routPortStr = scan.next();
			}
		}
		
		System.out.println("please enter packet loss rate, it will be a float which between 0 and 1 and exclude 1");
		String pLRStr = scan.next();
		while(true){
			try{
				packetLossRate = Double.parseDouble(pLRStr);
				if(packetLossRate<0.0 ||packetLossRate>=1.0){
					throw new RuntimeException("Packet loss rate must be a float and between 0 and 1");
				}
				break;
			}catch(Exception e){
				System.out.println("please enter packet loss rate, it will be a float which between 0 and 1 and exclude 1");
				pLRStr = scan.next();
			}
		}
		
		System.out.println("please enter file path for sending");
		filePathRead = scan.next();
		while(true){
			try{
			fileRead = new File(filePathRead);
			if(!fileRead.exists()){
				throw new RuntimeException("File not existed");
			}
			break;
		}catch(Exception e){
			System.out.println("File not existed, please enter correct file path for sending");
			filePathRead = scan.next();
		}
			
		}
		
		System.out.println("please enter file path for receiving");
		filePathWrite = scan.next();
		
		
		Thread receiverThread = new Thread(new ReceiverThread(rinPort, routPort, crinPort, filePathWrite));
		Thread channelThread = new Thread(new ChannelThread(csinPort, csoutPort, rinPort, croutPort, crinPort, sinPort, packetLossRate));
		Thread senderThread = new Thread(new SenderThread(fileRead, sinPort, soutPort, csinPort));
		receiverThread.start();
		channelThread.start();
		senderThread.start();
	}
}
	
