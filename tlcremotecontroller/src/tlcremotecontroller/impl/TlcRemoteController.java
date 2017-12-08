package tlcremotecontroller.impl;

public class TlcRemoteController {
	
	protected TlcRemoteController_SemaphoreSubscriber subscriber  = null;
	protected TlcRemoteControllerREST restApi = null;
	protected String id = null;
	protected String restApiUrl = null;
	
	public TlcRemoteController(String id, String apiRestSemaphoreUrl, String mqttTopic) {
		this.setId(id);
		this.subscriber = new TlcRemoteController_SemaphoreSubscriber(id);
		this.subscriber.connect();
		this.subscriber.subscribe(mqttTopic);

		this.restApi = new TlcRemoteControllerREST(id);
		this.restApiUrl = apiRestSemaphoreUrl;

		// TODO
        // leggere il messaggio MQTT (esempio M4: {ctlc:ctlc123, status:"Opened"})
        // ricavando lo status e poi invocare changeSemaphoreStatus(status)
	}

	public String getId() {
		return id;
	}
	 
	public void setId(String id) {
		this.id = id;
	}

    public void changeSemaphoreStatus(String ctlcStatus) {
        if(ctlcStatus=="Closed") {
            this.restApi.changeSemaphoreStatus(this.restApiUrl,"red");
        }
        else if(ctlcStatus=="Opened") {
            this.restApi.changeSemaphoreStatus(this.restApiUrl,"init");
        }
    }
	
}
