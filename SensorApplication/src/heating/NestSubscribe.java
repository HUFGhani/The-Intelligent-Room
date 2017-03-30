package heating;

import java.util.HashMap;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import utils.DataFormatUtilities;
import utils.PahoClientSub;

public class NestSubscribe extends PahoClientSub {
	private TmpActionMethod actionMethod = null;

	public TmpActionMethod getActionMethod() {
		return actionMethod;
	}

	public void setActionMethod(TmpActionMethod actionMethod) {
		this.actionMethod = actionMethod;
	}

	public NestSubscribe(String houseId, String clientId) {
		super(serverURI, clientId);
		messages = new HashMap<>();

	}
	
	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		this.message = message.toString();
		if (actionMethod != null) {
			actionMethod.setCurrTemp(DataFormatUtilities.jsonToNest(message.toString()));
		}

	}
}
