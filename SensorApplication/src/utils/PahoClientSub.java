package utils;

import java.util.HashMap;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;

public class PahoClientSub implements MqttCallback {
	protected static String serverURI = "tcp://localhost:1883";
	protected MqttClient client = null;
	protected String message = null;
	protected HashMap<String, String> messages = null;

	/**
	 * 
	 * @param serverURI
	 * @param clientID
	 */
	public PahoClientSub(String serverURI, String clientID) {
		messages = new HashMap<>();
		try {
			client = new MqttClient(serverURI, clientID);
			client.connect();

		} catch (MqttException e) {
			e.printStackTrace();
		}
		client.setCallback(this);

	}

	/**
	 * 
	 * @param topic
	 */
	public void setTopic(String topic) {
		try {
			client.subscribe(topic);

		} catch (MqttException e) {
			e.printStackTrace();
		}
		System.out.println(
				".............. PAHOSUB: TOPIC SET " + topic + "  CID: " + client.getClientId() + "..................");
	}

	/**
	 * 
	 * @param topic
	 */
	public void unsetTopic(String topic) {
		if(!client.isConnected()){
			try {
				client.connect();
			} catch (MqttSecurityException e) {
				e.printStackTrace();
			} catch (MqttException e) {
				e.printStackTrace();
			}
		}
		try {
			client.unsubscribe(topic);

		} catch (MqttException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Will destroy the client subscription connection.
	 */
	public void destroy() {
		try {
			client.disconnect();
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method to deal with messages received from subscription.
	 */
	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		this.messages.put(topic.toString(), message.toString());
		this.message = message.toString();
	}

	/**
	 * Returns a single message for the topic used for instantiation.
	 * 
	 * @return
	 */
	public String getMessage() {
		return this.message;

	}

	/**
	 * Will return a hash map containing the topic and message for multiple
	 * topics. Will be used when instantiation topics use wildcards e.g:
	 * /topic/topic/# OR /test/+/test/
	 * 
	 * @return HashMap <string msg, String topic>
	 */
	public HashMap<String, String> getMessages() {
		return this.messages;

	}

	/**
	 * 
	 * @return
	 */
	public Boolean isValid() {
		if (client != null)
			return true;
		return false;

	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
	}

	@Override
	public void connectionLost(Throwable cause) {
	}
}