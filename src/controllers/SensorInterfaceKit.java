package controllers;

import utils.DataFormatUtilities;
import utils.House;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import sensors.GeneralPhidSensor;
import sensors.PhidSensor;

import com.google.gson.Gson;
import com.phidgets.InterfaceKitPhidget;
import com.phidgets.PhidgetException;
import com.phidgets.event.AttachEvent;
import com.phidgets.event.AttachListener;
import com.phidgets.event.DetachEvent;
import com.phidgets.event.DetachListener;
import com.phidgets.event.ErrorEvent;
import com.phidgets.event.ErrorListener;
import com.phidgets.event.InputChangeEvent;
import com.phidgets.event.InputChangeListener;
import com.phidgets.event.OutputChangeEvent;
import com.phidgets.event.OutputChangeListener;
import com.phidgets.event.SensorChangeEvent;
import com.phidgets.event.SensorChangeListener;

/**
 * This class is the Main controller for sensors connected to the Phidgets
 * Interface Kit. Sensor results are either averaged and sent to a localhost mosquito broker
 * or sent upon a sensor changed event.
 * 
 * @author Amanda Bamford
 *
 */
public class SensorInterfaceKit implements SensorChangeListener, InputChangeListener, AttachListener, DetachListener,
		ErrorListener, OutputChangeListener {
	InterfaceKitPhidget phidget = new InterfaceKitPhidget();

	private int prevLightVal = 0;
	private ArrayList<PhidSensor> sensors;
	private HashMap<String, Integer> sensorValues;
	private String houseId;

	/**
	 * Constructor initialises Phidgets listeners, checks for config setup.
	 * @throws PhidgetException
	 */
	public SensorInterfaceKit() throws PhidgetException {
		// Initialise sensor ArrayList
		sensors = getSensors();

		try {
			// Add listeners for sensor Interface Kit
			phidget.addAttachListener(this);
			phidget.addDetachListener(this);
			phidget.addSensorChangeListener(this);
			phidget.addInputChangeListener(this);
			phidget.openAny();

			// Wait until InterfaceKit is attached
			phidget.waitForAttachment();

			// Print debug information
			System.out.println(phidget.getDeviceType());
			System.out.println("Serial Number " + phidget.getSerialNumber());
			System.out.println("Device Version " + phidget.getDeviceVersion());

			// Initialise counter and HasMap for gathering sensor data
			int secondCounter = 0;
			sensorValues = new HashMap();

			// Get information from config file, if not found then do
			// nothing
			houseId = getHouseId();
			// Continuous loop, gather sensor data for 60 seconds then send
			// to server
			while (true) {
				// Constant loop, pause for one second
				Thread.sleep(1000);
				if (houseId == null) {
					System.out.println("CONFIG FILE NOT FOUND");

				} else if (secondCounter < 60) {
					System.out.println(secondCounter);
					updateSensorValues(secondCounter);
					secondCounter++;

				} else {
					sendToServer();
					secondCounter = 0;
					sensorValues = new HashMap();
					// TODO SEND IP ADDRESS
				}
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			phidget.close();
			System.out.println("Closed and exiting...");
		}

	}

	/**
	 * 
	 * @return
	 */
	public String getHouseId() {
		// File dir: '/etc/home-automation/ha.config'
		String fileDirPath = "/etc/home-automation/ha.config";
		String houseId = null;

		if (new File(fileDirPath).exists()) {
			Gson gson = new Gson();
			try (BufferedReader br = new BufferedReader(new FileReader(fileDirPath))) {
				StringBuilder sb = new StringBuilder();
				String line = br.readLine();

				while (line != null) {
					sb.append(line);
					sb.append(System.lineSeparator());
					line = br.readLine();
				}
				String houseInfo = sb.toString();
				House house = gson.fromJson(houseInfo, House.class);
				houseId = house.getHouseId();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return houseId;

	}

	/**
	 * 
	 * @return
	 */
	public ArrayList<PhidSensor> getSensors() {
		ArrayList<PhidSensor> sensors = new ArrayList<PhidSensor>();
		// Will get all sensors from database, using the JSON string it creates
		// an object for each and add to an ArrayList

		// Just for test
		sensors.add(new GeneralPhidSensor("motionSensor", 0, "id1", new Timestamp(System.currentTimeMillis()), 0,
				"onChanged"));
		sensors.add(new GeneralPhidSensor("lightSensor", 0, "id2", new Timestamp(System.currentTimeMillis()), 1,
				"average"));
		sensors.add(new GeneralPhidSensor("touchSensor", 0, "id3", new Timestamp(System.currentTimeMillis()), 2,
				"onChanged"));

		return sensors;
	}

	/**
	 * 
	 * @param secondCounter
	 */
	public void updateSensorValues(int secondCounter) {
		// Update array list of sensor data for each sensor
		try {
			// FOR TEST 0=Motion 1=Light Sensor 2=Touch
			for (int i = 0; i < sensors.size(); i++) {
				if (sensors.get(i).getSensorType().equals("average")) {
					System.out.println(sensorValues.toString());
					sensorValues.put(sensors.get(i).getSensorName() + secondCounter, phidget.getSensorRawValue(i));
				}
			}
		} catch (PhidgetException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param sensorName
	 * @return
	 */
	public int averageValues(String sensorName) {
		int returnedAverage = 0;
		System.out.println("AVERAGING RESULTS: " + sensorValues.toString());
		for (int i = 0; i < 60; i++) {
			System.out.println(sensorValues.get(sensorName + i));
			returnedAverage += sensorValues.get(sensorName + i);
		}
		return returnedAverage / 60;
	}

	/**
	 * 
	 */
	public void sendToServer() {
		for (int i = 0; i < sensors.size(); i++) {
			if (sensors.get(i).getSensorType().equals("average")) {
				// Create sensor object, generate json and send to server
				GeneralPhidSensor sensor = new GeneralPhidSensor(sensors.get(i).getSensorName(),
						averageValues(sensors.get(i).getSensorName()), sensors.get(i).getSensorId(),
						sensors.get(i).getSensorLastUpdate(), sensors.get(i).getSensorPort(),
						sensors.get(i).getSensorType());
				// Generate JSON String
				List<GeneralPhidSensor> list = Arrays.asList(sensor);
				String output = DataFormatUtilities.generateJSON(list);

				// Create Mqtt Client to send to server, each will need to be
				// published to a different topic.
				mqttPublish(output, houseId, sensors.get(i).getSensorName());
				System.out.println("Sending average results to server: " + output);
			}
		}
	}

	/**
	 * 
	 * @param pubMessage
	 * @param houseId
	 * @param sensorName
	 */
	public void mqttPublish(String pubMessage, String houseId, String sensorName) {

		try {
			MqttClient client = new MqttClient("tcp://localhost:1883", houseId);
			client.connect();
			MqttMessage message = new MqttMessage();
			message.setPayload(pubMessage.getBytes());

			// houseID/room/sensor
			client.publish(houseId + "/" + sensorName, message);
			client.disconnect();

		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param sensor
	 */
	public void addSensor(PhidSensor sensor) {
		this.sensors.add(sensor);

	}

	@Override
	public void outputChanged(OutputChangeEvent arg0) {
	}

	@Override
	public void error(ErrorEvent arg0) {
	}

	@Override
	public void detached(DetachEvent arg0) {

	}

	@Override
	public void attached(AttachEvent arg0) {
	}

	@Override
	public void inputChanged(InputChangeEvent arg0) {
	}

	/**
	 * 
	 */
	@Override
	public void sensorChanged(SensorChangeEvent arg0) {
		// For every sensor, if the type is on changed + the port value matches
		// the index, then send to server
		for (int i = 0; i < sensors.size(); i++) {
			if (sensors.get(i).getSensorType().equals("onChanged") && arg0.getIndex() == sensors.get(i).getSensorPort()
					&& arg0.getValue() != 0) {
				// Create sensor object and add new value
				PhidSensor sensor = new GeneralPhidSensor(sensors.get(i).getSensorName(), arg0.getValue(),
						sensors.get(i).getSensorId(), new Timestamp(System.currentTimeMillis()),
						sensors.get(i).getSensorPort(), sensors.get(i).getSensorType());
				List<PhidSensor> list = Arrays.asList(sensor);
				// Generate JSON String
				String output = DataFormatUtilities.generateJSON(list);

				if (sensors.get(i).getSensorName().equals("motionSensor")) {
					if (arg0.getValue() < 300 || arg0.getValue() > 600) {
						// Send to mqtt broker!
						System.out.println("ON SENSOR CHANGE: " + output);
						mqttPublish(output, houseId, sensors.get(i).getSensorName());
					}

				} else {
					// Send to mqtt broker!
					System.out.println("ON SENSOR CHANGE: " + output);
					mqttPublish(output, houseId, sensors.get(i).getSensorName());

				}
			}

		}
	}

	public static void main(String[] args) throws PhidgetException {
		new SensorInterfaceKit();
	}

}
