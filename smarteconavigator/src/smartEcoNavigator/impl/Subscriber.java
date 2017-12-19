package smartEcoNavigator.impl;

import org.eclipse.paho.client.mqttv3.*;
import smartEcoNavigator.impl.SmartEcoNavigator;

public abstract class Subscriber implements MqttCallback{
    private MqttClient myClient;
    private MqttConnectOptions connOpt;

    private String brokerUrl;

    protected SmartEcoNavigator smartEcoNavigator;

    public Subscriber(SmartEcoNavigator smartEcoNavigator, String brokerUrl) {
        this.smartEcoNavigator = smartEcoNavigator;
        this.brokerUrl=brokerUrl;
    }

    private void _debug(String message) {
        System.out.println("(SmartEcoNavigator: " + this.smartEcoNavigator + ") " + message);
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

    /**
     *
     * runClient
     * The main functionality of this simple example.
     * Create a MQTT client, connect to broker, pub/sub, disconnect.
     *
     */
    public void connect() {
        // setup MQTT Client
        String clientID = this.smartEcoNavigator.getCltcId();
        connOpt = new MqttConnectOptions();

        connOpt.setCleanSession(true);
        connOpt.setKeepAliveInterval(30);

        // Connect to Broker
        try {
            myClient = new MqttClient(this.brokerUrl, clientID);
            myClient.setCallback(this);
            myClient.connect(connOpt);
        } catch (MqttException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        this._debug("Connected to " + this.brokerUrl);

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
