package TCP_Protocol;

/**
 * 
 * @author Yuan
 *
 */
public class ReceiverThread implements Runnable{
	int rinPort, routPort, crinPort;
	String filePathWrite;
	
	
	public ReceiverThread(int rinPort, int routPort, int crinPort, String filePathWrite) {
		this.rinPort = rinPort;
		this.routPort = routPort;
		this.crinPort = crinPort;
		this.filePathWrite = filePathWrite;
	}


	@Override
	public void run() {
		try{
			new Receiver(rinPort, routPort, crinPort, filePathWrite);
			}catch(Exception e){
				e.printStackTrace();
			}
		
	}

	
}
