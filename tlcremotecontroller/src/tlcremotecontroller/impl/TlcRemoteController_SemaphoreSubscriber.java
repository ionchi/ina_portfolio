package tlcremotecontroller.impl;

import org.eclipse.paho.client.mqttv3.*;
import org.json.JSONObject;

import java.util.Arrays;

public class TlcRemoteController_SemaphoreSubscriber implements MqttCallback {

	private MqttClient myClient;
	private MqttConnectOptions connOpt;

	static final String BROKER_URL = "tcp://ttmi008.iot.upv.es:1883";
//	static final String M2MIO_USERNAME = "<m2m.io username>";
//	static final String M2MIO_PASSWORD_MD5 = "<m2m.io password (MD5 sum of password)>";

	private TlcRemoteController tlcRemoteController;
	
	public TlcRemoteController_SemaphoreSubscriber(TlcRemoteController tlcRemoteController) {
		this.tlcRemoteController = tlcRemoteController;
	}

	private void _debug(String message) {
		System.out.println("(TlcRemoteController: " + this.tlcRemoteController + ") " + message);
	}

	
	@Override
	public void connectionLost(Throwable t) {
		this._debug("Connection lost!");
		// code to reconnect to the broker would go here if desired
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		//System.out.println("Pub complete" + new String(token.getMessage().getPayload()));
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
		if (msg.getString("ctlc").equals(tlcRemoteController.getId())){
			tlcRemoteController.changeSemaphoreStatus(msg.getString("status"));
		}
	}

	/**
	 * 
	 * runClient
	 * The main functionality of this simple example.
	 * Create a MQTT client, connect to broker, pub/sub, disconnect.
	 * 
	 */
	public void connect() {
		// setup MQTT Client
		String clientID = this.tlcRemoteController.getId();
		connOpt = new MqttConnectOptions();
		
		connOpt.setCleanSession(true);
		connOpt.setKeepAliveInterval(30);
		
		// Connect to Broker
		try {
			myClient = new MqttClient(BROKER_URL, clientID);
			myClient.setCallback(this);
			myClient.connect(connOpt);
		} catch (MqttException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		this._debug("Connected to " + BROKER_URL);

	}
	
	
	public void disconnect() {
		
		// disconnect
		try {
			// wait to ensure subscribed messages are delivered
			Thread.sleep(120000);

			myClient.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}

		
	}

	
	public void subscribe(String myTopic) {
		
		// subscribe to topic
		try {
			int subQoS = 0;
			myClient.subscribe(myTopic, subQoS);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	

}
