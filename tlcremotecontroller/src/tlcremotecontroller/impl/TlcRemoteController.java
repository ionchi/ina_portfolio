package tlcremotecontroller.impl;

public class TlcRemoteController {
	
	protected TlcRemoteController_SemaphoreSubscriber subscriber  = null;
	protected String id = null;
	
	public TlcRemoteController(String id) {
		this.setId(id);
		this.subscriber = new TlcRemoteController_SemaphoreSubscriber(id);
		this.subscriber.connect();
		this.subscriber.subscribe("valencia/ctlcs/status");
	}

	 public String getId() {
		return id;
	}
	 
	 public void setId(String id) {
		this.id = id;
	}
	
}
