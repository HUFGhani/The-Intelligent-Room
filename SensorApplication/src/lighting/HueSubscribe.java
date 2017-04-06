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
		System.out.println(">>>>>HUE_LOG: HUE CREATED..............");

	}


	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		System.out.println(">>>>>HUE_LOG: MSG ARR START..............");
		this.message = message.toString();
		if (actionMethod != null) {
			actionMethod.setCurrLight(DataFormatUtilities.jsonToLight(message.toString()));
		}
		System.out.println(">>>>>HUE_LOG: MSG ARR END..............");

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
