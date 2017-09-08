package TCP_Protocol;

import java.io.File;

/**
 * 
 * @author Yuan
 *
 */

public class SenderThread implements Runnable{
	File fileRead;
	int sinPort, soutPort, csinPort;
	
	public SenderThread(File fileRead, int sinPort, int soutPort, int csinPort) {
		super();
		this.fileRead = fileRead;
		this.sinPort = sinPort;
		this.soutPort = soutPort;
		this.csinPort = csinPort;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(500);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try{
			new Sender(fileRead, sinPort, soutPort, csinPort);
			}catch(Exception e){
				e.printStackTrace();
			}
	}
	
}
