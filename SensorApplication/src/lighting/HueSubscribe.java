package lighting;

import java.util.HashMap;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import utils.DataFormatUtilities;
import utils.PahoClientSub;


public class HueSubscribe extends PahoClientSub {
	private LightActionMethod actionMethod = null;



	public HueSubscribe(String houseId, String clientId) {
		super(serverURI, clientId);
		messages = new HashMap<>();
	}


	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		this.message = message.toString();
		if (actionMethod != null) {
			actionMethod.setCurrLight(DataFormatUtilities.jsonToLight(message.toString()));
		}
	}


	@Override
	public void connectionLost(Throwable arg0) {

	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken arg0) {
	}
	
	public LightActionMethod getActionMethod() {
		return actionMethod;
	}

	public void setActionMethod(LightActionMethod actionMethod) {
		this.actionMethod = actionMethod;
	}

}
