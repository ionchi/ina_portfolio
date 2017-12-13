package tlcremotecontroller.impl;

public class TlcRemoteController {

	private TlcRemoteControllerREST restApi;
	private String id;
	private String restApiUrl;
	
	public TlcRemoteController(String id, String apiRestSemaphoreUrl, String mqttTopic) {
		this.id = id;
		TlcRemoteController_SemaphoreSubscriber subscriber = new TlcRemoteController_SemaphoreSubscriber(this);
		subscriber.connect();
		subscriber.subscribe(mqttTopic);

		this.restApi = new TlcRemoteControllerREST(id);
		this.restApiUrl = apiRestSemaphoreUrl;

	}

	public String getId() {
		return id;
	}

    public void changeSemaphoreStatus(String ctlcStatus) {
        if(ctlcStatus.equals("Closed")) {
            this.restApi.changeSemaphoreStatus(this.restApiUrl,"red");
        }
        else if(ctlcStatus.equals("Opened")) {
            this.restApi.changeSemaphoreStatus(this.restApiUrl,"init");
        }
    }
	
}
