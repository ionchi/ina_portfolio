package smartEcoNavigator.impl;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;

import java.util.Arrays;

public class SmartEcoNavigator_StatusSubscriber extends Subscriber {

	public SmartEcoNavigator_StatusSubscriber(SmartEcoNavigator smartEcoNavigator, String brokerUrl) {
		super(smartEcoNavigator, brokerUrl);
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
            smartEcoNavigator.changeZoneStatus(msg.getString("status").equals("Closed"));
        }

	}

}
