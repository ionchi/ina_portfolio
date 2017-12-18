package smartEcoNavigator.impl;

import org.eclipse.paho.client.mqttv3.*;
import org.json.JSONObject;
import smartEcoNavigator.Subscriber;

import java.util.Arrays;

public class SmartEcoNavigator_EnvironmentSubscriber extends Subscriber {

    public SmartEcoNavigator_EnvironmentSubscriber(SmartEcoNavigator smartEcoNavigator) {
        super(smartEcoNavigator);
    }

    @Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		System.out.println("-------------------------------------------------");
		System.out.println("| Topic:" + topic);
		System.out.println("| Message: " + new String(message.getPayload()));
		System.out.println("-------------------------------------------------");
		
		// DO SOME MAGIC HERE!
        message.getPayload();
        JSONObject msg = new JSONObject(Arrays.toString(message.getPayload()));
        if (msg.getString("ctlc").equals(smartEcoNavigator.getCltcId())){
            smartEcoNavigator.changePollutionLevel(Integer.parseInt(msg.getString("level")));
        }
	}

}
