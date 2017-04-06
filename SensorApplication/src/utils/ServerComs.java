package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;

import heating.Nest;
import lighting.Colour;
import lighting.Hue;
import lighting.Light;
import sensors.GeneralPhidSensor;

public class ServerComs {

	/**
	 * 
	 * @param sensors
	 * @param sensorValues
	 * @param houseId
	 */
	public static void sendToServer(ArrayList<GeneralPhidSensor> sensors, HashMap<String, Integer> sensorValues,
			String houseId) {
		for (int i = 0; i < sensors.size(); i++) {
			if (sensors.get(i).getSensorType().equals("average")) {
				// Create sensor object, generate json and send to server
				GeneralPhidSensor sensor = new GeneralPhidSensor(sensors.get(i).getSensorName(),
						averageValues(sensors.get(i).getSensorName(), sensorValues), sensors.get(i).getSensorId(),
						sensors.get(i).getSensorLastUpdate(), sensors.get(i).getSensorPort(),
						sensors.get(i).getSensorType());
				// Generate JSON String
				List<GeneralPhidSensor> list = Arrays.asList(sensor);
				String output = DataFormatUtilities.generateJSON(list);

				// Create Mqtt Client to send to server, each will need to be
				// published to a different topic.
				MqttUtils.mqttPublish(output, houseId + "/sensor/" + sensors.get(i).getSensorId());
			}
		}
	}
	
	
	public static void sendToServer(GeneralPhidSensor sensor, int sensorValue,
			String houseId) {
			if (sensor.getSensorType().equals("average")) {
				// Create sensor object, generate json and send to server
				sensor.setSensorValue(sensorValue);
				// Generate JSON String
				List<GeneralPhidSensor> list = Arrays.asList(sensor);
				String output = DataFormatUtilities.generateJSON(list);

				// Create Mqtt Client to send to server, each will need to be
				// published to a different topic.
				MqttUtils.mqttPublish(output, houseId + "/sensor/" + sensor.getSensorId());
			}
		
	}
	
	public static void turnNestOff(String houseId){
		Nest nest = new Nest();
		MqttUtils.mqttPublish(new Gson().toJson(nest), houseId + "/actuator/nest");
	}
	
	public static void turnHueOff(String houseId){

		Light light = new Light();
		light.setColour(new Colour());
		Hue hue = new Hue(light);

		MqttUtils.mqttPublish(new Gson().toJson(hue), houseId + "/actuator/hue");
	}

	/**
	 * 
	 * @param sensorName
	 * @param sensorValues
	 * @return
	 */
	public static int averageValues(String sensorName, HashMap<String, Integer> sensorValues) {
		int returnedAverage = 0;
		for (int i = 0; i < 60; i++) {
			returnedAverage += sensorValues.get(sensorName + i);
		}
		return returnedAverage / 60;
	}

}
