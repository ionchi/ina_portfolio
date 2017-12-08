package tlcremotecontroller.starter;

import tlcremotecontroller.impl.TlcRemoteController;


public class TlcRemoteControllerStarter {

	public static void main(String[] args) {

		TlcRemoteController sc1 = new TlcRemoteController("TLC.net",
                "http://localhost:8180/tlc","valencia/ctlcs/status");
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
