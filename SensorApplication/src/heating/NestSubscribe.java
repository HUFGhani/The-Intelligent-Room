package heating;

import java.util.HashMap;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import utils.DataFormatUtilities;
import utils.PahoClientSub;

public class NestSubscribe extends PahoClientSub {
	public TmpActionMethod getActionMethod() {
		return actionMethod;
	}

	public void setActionMethod(TmpActionMethod actionMethod) {
		this.actionMethod = actionMethod;
	}

	private TmpActionMethod actionMethod = null;
	

	public NestSubscribe(String houseId, String clientId) {
		super(serverURI, clientId);
		messages = new HashMap<>();
		System.out.println(">>>>>NEST_LOG: CREATED..............");

	}
	
	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		System.out.println(">>>>>NEST_LOG: MSG ARR START..............");
		this.message = message.toString();
		if (actionMethod != null) {
			actionMethod.setCurrTemp(DataFormatUtilities.jsonToNest(message.toString()));
		}
		System.out.println(">>>>>NEST_LOG: MSG ARR END..............");

	}
}
