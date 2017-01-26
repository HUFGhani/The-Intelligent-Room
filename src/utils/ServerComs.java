package utils;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import org.eclipse.paho.client.mqttv3.MqttClient;

import sensors.GeneralPhidSensor;

public class ServerComs {
	private URL urlObj;
	private HttpURLConnection connection;
	private BufferedReader reader;
	private String url;
	private String fullURL = null;

	public ServerComs() {
		// Default server URL, used if user doesn't create object with URL
		url = "http://localhost:8080/AssignmentsServer/ServerController";
	}

	// Constructor for creating a new server connection, not the default used.
	public ServerComs(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	// Create a connection to the server using the created URL
	private void createConnection() {
		try {
			urlObj = new URL(this.fullURL);
			System.out.println("URLOBJ CREATED");
			connection = (HttpURLConnection) urlObj.openConnection();
			System.out.println("CONOBJ CREATED");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("CONNECTION CREATED");
	}

	// Change connection send method to get
	public String sendGet(HashMap<String, String> data) {
		createUrl(data);
		createConnection();
		try {
			connection.setRequestMethod("GET");
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return send();
	}
	
	// Change connection send method to get & use GeneralPhidSensor to build URL
	public String sendGet(GeneralPhidSensor sensor) {
		createUrl(sensor);
		createConnection();
		try {
			connection.setRequestMethod("GET");
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return send();
	}
	
	// Change connection send method to set & use HashMap to build URL
	public String sendPost(HashMap<String, String> data) {
		createUrl(data);
		createConnection();
		try {
			connection.setRequestMethod("POST");
		} catch (ProtocolException e) {
			e.printStackTrace();
		}
		return send();

	}

	// Send URL request and return string result.
	private String send() {
		StringBuffer results = new StringBuffer();
		String line = null;
		try {
			reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			while ((line = reader.readLine()) != null) {
				results.append(line);
				System.out.println("DEBUG response reader:" + line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("RESULTS ON CLIENT: " + results.toString());
		return results.toString();
	}

	// Create the URL based on the params parsed in the Hash map.
	private void createUrl(HashMap<String, String> data) {
		StringBuffer sb = new StringBuffer(this.url + "?");
		for (String key : data.keySet()) {
			System.out.println("KEY= " + key + " VAL= " + data.get(key));
			sb.append(String.format("%s=%s&", key, data.get(key)));
		}

		if (sb.charAt(sb.length() - 1) == '&') {
			sb.deleteCharAt(sb.length() - 1);
		}
		this.fullURL = sb.toString();
		System.out.println("URL CREATED: " + sb.toString());

	}
	
	// Create the URL based on the params parsed in the Hash map.
	private void createUrl(GeneralPhidSensor sensor) {
		StringBuffer sb = new StringBuffer(this.url + "?");
		sb.append(String.format("%s=%s&", "SensorId", sensor.getSensorId()));
		sb.append(String.format("%s=%s&", "SensorName", sensor.getSensorName()));
		sb.append(String.format("%s=%s&", "SensorValue", sensor.getSensorValue()));
		// Need to parse to string
		sb.append(String.format("%s=%s&", "updateTimestamp", new SimpleDateFormat("yyyy.MM.dd_HH:mm:ss").format(sensor.getSensorLastUpdate())));

	}
	

}
