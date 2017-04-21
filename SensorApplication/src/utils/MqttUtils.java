package utils;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttUtils {

	/**
	 * 
	 * @param houseId
	 * @return
	 */
	public static House getHouseConfiguration(String houseId) {
		House house = null;
		// Create Sub Client
		PahoClientSub sub = new PahoClientSub("tcp://localhost:1883", "houseConfig");
		sub.setTopic(houseId + "/configuration");

		try {
			Thread.sleep(100);

			house = DataFormatUtilities.jsonToHouse(sub.getMessage());
			sub.destroy();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(house.toString());
		return house;
	}

	/**
	 * 
	 * @param pubMessage
	 * @param topic
	 */
	public static void mqttPublish(String pubMessage, String topic) {
		System.out.println("PUBLISH: topic- " + topic + " msg- " + pubMessage);
		try {
			MqttClient client = new MqttClient("tcp://localhost:1883", topic);
			client.connect();
			MqttMessage message = new MqttMessage();
			message.setPayload(pubMessage.getBytes());
			message.setRetained(true);

			// houseID/room/sensor
			client.publish(topic, message);
			client.disconnect();

		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

}
