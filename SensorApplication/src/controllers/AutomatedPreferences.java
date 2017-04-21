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
import utils.ServerComs;
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
		System.out.println(">>>AP_LOG: AutomatedPreferences created");
	}

	/**
	 * Method to deal with messages received from subscription.
	 */
	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		this.messages.put(topic.toString(), message.toString());
		this.message = message.toString();

		System.out.println(">>>AP_LOG: AUTOPREF INHOUSE MSG ARR START....................");
		System.out.println(">>>AP_LOG: " + topic + ": " + message.toString());

		// Get list of users in the house
		getUsersList();

		// for test - REMOVE AFTER
		System.out.println(">>>AP_LOG: USERS IN HOUSE");
		for (int i = 0; i < users.size(); i++) {
			System.out.println(users.get(i).toString());
		}

		if (lightActionMethodClient != null && users != null && !users.isEmpty()) {
			System.out.println(">>>AP_LOG: lightActionMethodClient not null, initialising ..........");
			lightActionMethodClient.setLightPref(lightPref(users));
			System.out.println(">>>AP_LOG: lightActionMethodClient created");

		} else if (lightActionMethodClient != null) {
			System.out.println(">>>AP_LOG:  users null");
			// Turn hue off
			lightActionMethodClient.setLightPref(null);
			ServerComs.turnHueOff(houseId);

		} else {
			System.out.println(">>>AP_LOG: lightActionMethodClient or users null");

		}

		if (tmpActionMethodClient != null && users != null && !users.isEmpty()) {
			// Set heat pref
			System.out.println(">>>AP_LOG: tmpActionMethodClient not null, initialising..........");
			tmpActionMethodClient.setTemperaturePref(tmpPref(users));
			System.out.println(">>>AP_LOG: tmpActionMethodClient created");

		} else if (tmpActionMethodClient != null) {
			System.out.println(">>>AP_LOG:  users null");
			// Turn nest off
			tmpActionMethodClient.setTemperaturePref(null);
			ServerComs.turnNestOff(houseId);
	
		} else {

			System.out.println(">>>AP_LOG: tmpActionMethodClient null");
		}
		System.out.println(">>>AP_LOG: MSG ARR END....................");

	}

	private void getUsersList() {
		if (prefSub == null) {
			System.out.println(">>>AP_LOG: prefSub null, initialising ..........");
			prefSub = new PahoClientSub("tcp://localhost:1883", "userPrefs");
			System.out.println(">>>AP_LOG: prefSub created");
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
			if (prefSub.getMessage() != null) {
				users.add(DataFormatUtilities.jsonToUser(prefSub.getMessage()));
			}
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
		System.out.println(">>>AP_LOG: USERS IN HOUSE CHECK: " + users.toString());
		System.out.println(">>>AP_LOG: RETURNING IDS: " + userIds.toString());

		return userIds;
	}

	public void addLightActionMethod() {
		// create light action method
		System.out.println(">>>AP_LOG: initialising LightActionMethod ..........  ");
		lightActionMethodClient = new LightActionMethod(houseId, "lightAction");
		System.out.println(">>>AP_LOG: lightActionMethod created");

		System.out.println(">>>AP_LOG: CURR lightActionMethod MESSAGE:  " + lightActionMethodClient.getMessage());
		if (users != null && !users.isEmpty()) {
			lightActionMethodClient.setLightPref(lightPref(users));
		} else {
			System.out.println(">>>AP_LOG: USERS LIST EMPTY, LIGHT PREF NOT SET.");

		}

	}

	public void addTempActionMethod() {
		// create heat action method
		System.out.println(">>>AP_LOG: initialising TmpActionMethod ..........  ");
		tmpActionMethodClient = new TmpActionMethod(houseId, "tmpAction");
		System.out.println(">>>AP_LOG: TempActionMethod created ");
		
		System.out.println(">>>AP_LOG: TA CURR MSG: " + tmpActionMethodClient.getMessage());
		if (users != null && !users.isEmpty()){
			tmpActionMethodClient.setTemperaturePref(tmpPref(users));
		}else {
				System.out.println(">>>AP_LOG: USERS LIST EMPTY, TEMP PREF NOT SET.");

			}
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
				prevLightPriority = users.get(i).getLightPref().getActionPriority();
				lightActionMethod = users.get(i).getLightPref().getActionMethod();
				currActionPriority = users.get(i).getLightPref().getActionPriority();

			}

		}
		// FOR TEST REMOVE AFTER
		System.out.println("Setting pref: "
				+ new LightPref(new Light(name, onOff, new Colour(red, green, blue), lightBright, lightSat, automated),
						lightActionMethod, currActionPriority).toString());

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
				prevTmpPriority = users.get(i).getTmpPref().getActionPriority();
				tmpActionMethod = users.get(i).getTmpPref().getActionMethod();
				currPriority = users.get(i).getTmpPref().getActionPriority();
			}

		}

		System.out.println("Setting tmp  pref: "
				+ new TemperaturePref(new Nest(temp, true), tmpActionMethod, currPriority).toString());

		return new TemperaturePref(new Nest(temp, true), tmpActionMethod, currPriority);

	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
	}

}
