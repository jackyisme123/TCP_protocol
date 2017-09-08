package TCP_Protocol;

/**
 * 
 * @author Yuan
 *
 */

public class ChannelThread implements Runnable{
	
	int csinPort;
	int csoutPort;
	int rinPort;
	int croutPort;
	int crinPort;
	int sinPort;
	double packetLossRate;
	
	
	public ChannelThread(int csinPort, int csoutPort, int rinPort, int croutPort, int crinPort, int sinPort,
			double packetLossRate) {
		this.csinPort = csinPort;
		this.csoutPort = csoutPort;
		this.rinPort = rinPort;
		this.croutPort = croutPort;
		this.crinPort = crinPort;
		this.sinPort = sinPort;
		this.packetLossRate = packetLossRate;
	}


	@Override
	public void run() {
		try{
			new Channel(csinPort, csoutPort, rinPort, croutPort, crinPort, sinPort, packetLossRate);
			}catch(Exception e){
				System.out.println("channel lose connectiong and closing!");
				
			}
		
	}
	
}
