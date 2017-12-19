package smartEcoNavigator.impl;

public class SmartEcoNavigator {

    private String distintivoAmbiental;
    private int matricula;
	private SmartEcoNavigatorREST restApi;
    private String cltcId;
    private String restApiUrl;

	private boolean zoneClosed=false;
    private int pollutionAlertLevel=3;

	public SmartEcoNavigator(String distintivoAmbiental, int matricula, String cltcId, String apiRestCarUrl, String mqttBroker) {
		this.distintivoAmbiental=distintivoAmbiental;
	    this.matricula=matricula;
	    this.cltcId = cltcId;
		SmartEcoNavigator_EnvironmentSubscriber environmentSubscriber = new SmartEcoNavigator_EnvironmentSubscriber(this,mqttBroker);
		SmartEcoNavigator_StatusSubscriber statusSubscriber= new SmartEcoNavigator_StatusSubscriber(this,mqttBroker);
        environmentSubscriber.connect();
        statusSubscriber.connect();
        environmentSubscriber.subscribe("valencia/policies/environment");
        statusSubscriber.subscribe("valencia/ctlcs/status");

		this.restApi = new SmartEcoNavigatorREST();
		this.restApiUrl = apiRestCarUrl;

	}

    public String getCltcId() {
        return cltcId;
    }

	public void changeZoneStatus(boolean zoneClosed){
	    this.zoneClosed=zoneClosed;
	    this.restApi.changeCarAction(this.restApiUrl,canMove());
    }

    public void changePollutionLevel(int pollutionAlertLevel){
        this.pollutionAlertLevel=pollutionAlertLevel;
        this.restApi.changeCarAction(this.restApiUrl,canMove());
    }

    /*
	    - Si PAL == 3, puede circular (no le indicará nada al vehículo)
        - Si PAL == 2, detendrá el vehículo: si es de tipo A, o si es de tipo B y su matrícula es par/impar (elegid una)
        - Si PAL == 1, detendrá el vehículo si es de tipo A o B
        - Si PAL == 0, detendrá el vehículo si es de tipo A, B o C
	 */
    private String canMove(){
	    if (!zoneClosed)
	        return "forward";
	    switch (this.pollutionAlertLevel) {
            case 3:
                return "forward";
            case 2:
                if (this.distintivoAmbiental.equals("A"))
                    return "brake";
                if (this.distintivoAmbiental.equals("B") && matricula%2==0)
                    return "brake";
                return "forward";
            case 1:
                if (this.distintivoAmbiental.equals("A") || this.distintivoAmbiental.equals("B"))
                    return "brake";
                return "forward";
            case 0:
                if (this.distintivoAmbiental.equals("A") || this.distintivoAmbiental.equals("B")
                        || this.distintivoAmbiental.equals("C"))
                    return "brake";
                return "forward";
            default:
                return "brake";
        }
    }
	
}
