package controllers;

import utils.DataFormatUtilities;
import utils.House;
import utils.MqttUtils;
import utils.ServerComs;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
 * Interface Kit. Sensor results are either averaged and sent to a localhost
 * mosquito broker or sent upon a sensor changed event.
 * 
 * @author Amanda Bamford
 *
 */
public class SensorInterfaceKit implements SensorChangeListener, InputChangeListener, AttachListener, DetachListener,
		ErrorListener, OutputChangeListener {
	InterfaceKitPhidget phidget = new InterfaceKitPhidget();
	private ArrayList<GeneralPhidSensor> sensors = null;
	private HashMap<String, Integer> sensorValues;
	private String houseId;
	private AutomatedPreferences prefs = null;
	private long time = 0;

	/**
	 * Constructor initialises Phidgets listeners, checks for config setup.
	 * 
	 * @throws PhidgetException
	 */
	public SensorInterfaceKit() throws PhidgetException {
		time = System.currentTimeMillis();
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
				} else {
					if (sensors == null) {
						// Initialise sensor ArrayList
						sensors = getSensors(houseId);
					} 

					 if (prefs == null) {
					 prefs = new AutomatedPreferences("testID123");
					 try {
					 Thread.sleep(1000);
					 } catch (InterruptedException e) {
					 e.printStackTrace();
					 }
					 //prefs.addLightActionMethod();
					 prefs.addTempActionMethod();					
					 }

					if (secondCounter < 60) {
						updateSensorValues(secondCounter);
						secondCounter++;

					} else {
						ServerComs.sendToServer(sensors, sensorValues, houseId);
						secondCounter = 0;
						sensorValues = new HashMap();
						// Initialise sensor ArrayList
						sensors = getSensors(houseId);
					}

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
	public ArrayList<GeneralPhidSensor> getSensors(String houseId) {
		ArrayList<GeneralPhidSensor> sensors = MqttUtils.getHouseConfiguration(houseId).getSensors();
		return sensors;
	}

	/**
	 * 
	 * @param secondCounter
	 */
	public void updateSensorValues(int secondCounter) {
		// Update array list of sensor data for each sensor
		try {
			// 0=Motion 1=Light Sensor 2=Touch
			for (int i = 0; i < sensors.size(); i++) {
				if (sensors.get(i).getSensorType().equals("average")) {
					sensorValues.put(sensors.get(i).getSensorName() + secondCounter, phidget.getSensorRawValue(i));
				}
			}
		} catch (PhidgetException e) {
			e.printStackTrace();
		}
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

				// Create sensor object
				PhidSensor sensor = new GeneralPhidSensor(sensors.get(i).getSensorName(), arg0.getValue(),
						sensors.get(i).getSensorId(), System.currentTimeMillis(), sensors.get(i).getSensorPort(),
						sensors.get(i).getSensorType());
				// Determine if motion sensor value represent movement
				if (sensors.get(i).getSensorName().equals("motionSensor")) {
					if (arg0.getValue() < 300 || arg0.getValue() > 600) {
						// Send to MQTT broker!
						long checkTime = time + 5000;
						if (System.currentTimeMillis() > checkTime) {
							MqttUtils.mqttPublish(DataFormatUtilities.generateJSON(Arrays.asList(sensor)),
									houseId + "/sensor/" + sensors.get(i).getSensorId());
							time = System.currentTimeMillis();
						}
					}
				} else {
					// Send to MQTT broker!
					MqttUtils.mqttPublish(DataFormatUtilities.generateJSON(Arrays.asList(sensor)),
							houseId + "/sensor/" + sensors.get(i).getSensorId());
				}
			}
		}
	}

	public static void main(String[] args) throws PhidgetException {
		new SensorInterfaceKit();
	}

}
