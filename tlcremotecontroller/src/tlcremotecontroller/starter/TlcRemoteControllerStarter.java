package tlcremotecontroller.starter;

import tlcremotecontroller.impl.TlcRemoteController;


public class TlcRemoteControllerStarter {

	public static void main(String[] args) {

		try {
            String id = args[0];
            String restUrl = args[1];
            String mqttBroker = args[2];

            TlcRemoteController sc1 = new TlcRemoteController(id,restUrl,mqttBroker);

		} catch (ArrayIndexOutOfBoundsException e){
            System.out.println("ArrayIndexOutOfBoundsException caught");
        }
		
		
	}

}
