package smartEcoNavigator.starter;

import smartEcoNavigator.impl.SmartEcoNavigator;

public class SmartEcoNavigatorStarter {

	public static void main(String[] args) {

		SmartEcoNavigator sc1 = new SmartEcoNavigator(args[0],Integer.parseInt(args[1]),args[2],args[3],args[4]);
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
