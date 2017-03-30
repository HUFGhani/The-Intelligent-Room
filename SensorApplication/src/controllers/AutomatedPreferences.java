package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import heating.Nest;
import heating.TemperaturePref;
import heating.TmpActionMethod;
import lighting.Colour;
import lighting.Light;
import lighting.LightActionMethod;
import lighting.LightPref;
import utils.DataFormatUtilities;
import utils.PahoClientSub;
import utils.User;

public class AutomatedPreferences extends PahoClientSub {
	private static String serverURI = "tcp://localhost:1883";
	private MqttClient client = null;
	private ArrayList<String> usersInHouse = null;
	private ArrayList<User> users = null;
	private String houseId = null;
	private TmpActionMethod tmpActionMethodClient = null;
	private LightActionMethod lightActionMethodClient = null;
	private PahoClientSub sub = null;
	private PahoClientSub prefSub = null;


	public AutomatedPreferences(String houseId) {
		super(serverURI, "InHouseSub");
		this.houseId = houseId;
		setTopic(houseId + "/+/inHouse");
		System.out.println("TEST: AutomatedPreferences created");
	}

	/**
	 * Method to deal with messages received from subscription.
	 */
	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		this.messages.put(topic.toString(), message.toString());
		this.message = message.toString();

		// Get list of users in the house
		getUsersList();

		if (!users.isEmpty()) {
			lightPref(users);
		}

		if (lightActionMethodClient != null && !users.isEmpty()) {
			lightActionMethodClient.setLightPref(lightPref(users));
		}

		if (tmpActionMethodClient != null && !users.isEmpty()) {
			// Set heat pref
			tmpActionMethodClient.setTemperaturePref(tmpPref(users));
		}

	}

	private void getUsersList() {
		if (prefSub == null) {
			System.out.println("prefSub null");
			prefSub = new PahoClientSub("tcp://localhost:1883", "userPrefs");
			System.out.println("TEST: PrefSub created");

		}

		usersInHouse = usersInHouse(houseId);
		users = new ArrayList<>();
		for (int i = 0; i < usersInHouse.size(); i++) {
			prefSub.setTopic(houseId + "/" + usersInHouse.get(i) + "/preference");
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			users.add(DataFormatUtilities.jsonToUser(prefSub.getMessage()));

		}

	}

	/**
	 * 
	 * @param houseId
	 * @return
	 */
	public ArrayList<String> usersInHouse(String houseId) {
		ArrayList<String> userIds = new ArrayList<>();
		HashMap<String, String> users = null;
		try {
			Thread.sleep(100);
			users = getMessages();

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		for (Map.Entry<String, String> entry : users.entrySet()) {
			if (entry.getValue().equals("true")) {
				Matcher m = Pattern.compile(houseId + "\\/(.*?)\\/inHouse").matcher(entry.getKey());
				if (m.find()) {
					userIds.add(m.group(1));
				}
			}
		}
		return userIds;
	}

	public void addLightActionMethod() {
		// create light action method
		lightActionMethodClient = new LightActionMethod(houseId, "lightAction");
		if (users != null)
			lightActionMethodClient.setLightPref(lightPref(users));
	}

	public void addTempActionMethod() {
		// create heat action method
		tmpActionMethodClient = new TmpActionMethod(houseId, "tmpAction");
		if (users != null)
			tmpActionMethodClient.setTemperaturePref(tmpPref(users));
	}

	/**
	 * 
	 * @param topic
	 */
	public void setTopic(String topic) {
		try {
			client = new MqttClient(serverURI, "AutoPrefs");
			client.connect();
			client.setCallback(this);
			client.subscribe(topic);
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Will destroy the client subscription connection.
	 */
	public void destroy() {
		try {
			if (client != null)
				client.disconnect();

			if (sub != null)
				sub.destroy();
		} catch (MqttException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void connectionLost(Throwable cause) {

	}

	private LightPref lightPref(ArrayList<User> users) {

		int lightSat = 0, lightBright = 0, red = 0, blue = 0, green = 0;
		String lightActionMethod = null, name = null;
		Boolean automated = true, onOff = null;
		int prevLightPriority = 0;
		int prevUserPriority = 0;
		int currActionPriority = 0;

		// Get heating of highest priority
		for (int i = 0; i < users.size(); i++) {
			int userPriority = users.get(i).getPriority();
			int lightPriority = users.get(i).getLightPref().getActionPriority();

			if (prevUserPriority == 0 || prevUserPriority > userPriority) {
				prevUserPriority = userPriority;
				name = users.get(i).getLightPref().getLight().getName();
				onOff = users.get(i).getLightPref().getLight().isOnOff();
				lightSat = users.get(i).getLightPref().getLight().getSaturation();
				lightBright = users.get(i).getLightPref().getLight().getBrightness();
				red = users.get(i).getLightPref().getLight().getColour().getRed();
				blue = users.get(i).getLightPref().getLight().getColour().getBlue();
				green = users.get(i).getLightPref().getLight().getColour().getGreen();
				
			}

			// determine which method has highest priority

			if (prevLightPriority == 0 || prevLightPriority > lightPriority) {
				lightActionMethod = users.get(i).getLightPref().getActionMethod();
				currActionPriority = users.get(i).getLightPref().getActionPriority();

			}

		}
		return new LightPref(new Light(name, onOff, new Colour(red, green, blue), lightBright, lightSat, automated),
				lightActionMethod, currActionPriority);

	}

	private TemperaturePref tmpPref(ArrayList<User> users) {
		double temp = 0;
		String tmpActionMethod = null;
		int prevTmpPriority = 0;
		int prevUserPriority = 0;
		int currPriority = 0;

		// Get heating of highest priority
		for (int i = 0; i < users.size(); i++) {
			int userPriority = users.get(i).getPriority();
			int tempPriority = users.get(i).getTmpPref().getActionPriority();

			if (prevUserPriority == 0 || prevUserPriority > userPriority) {
				prevUserPriority = userPriority;
				temp = users.get(i).getTmpPref().getNest().getTarget_temperature_c();

			}

			// determine which method has highest priority
			if (prevTmpPriority == 0 || prevTmpPriority > tempPriority) {
				tmpActionMethod = users.get(i).getTmpPref().getActionMethod();
				currPriority = users.get(i).getTmpPref().getActionPriority();
			}

		}

		return new TemperaturePref(new Nest(temp, true), tmpActionMethod, currPriority);

	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
	}

}
