package lighting;

import java.util.HashMap;
import java.util.List;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import com.google.gson.Gson;
import sensors.GeneralPhidSensor;
import utils.DataFormatUtilities;
import utils.House;
import utils.MqttUtils;
import utils.PahoClientSub;

public class LightActionMethod extends PahoClientSub {
	private LightPref light = null;
	private Light currLight = null;
	private String houseId = null;
	private HueSubscribe hue = null;
	private House house = null;
	private GeneralPhidSensor sensor = null;

	public LightActionMethod(String houseId, String clientId) {
		super(serverURI, clientId);
		this.houseId = houseId;
		addHue();
		System.out.println("LA_TEST_LOG: LAM CREATED");
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		this.message = message.toString();
		sensor = new GeneralPhidSensor(DataFormatUtilities.jsonToList(message.toString()).get(0));
		// setting against light pref, change if necessary.
		lightAction(this.light, sensor);

	}

	private void lightAction(LightPref newlightPref, GeneralPhidSensor sensor) {
		String topic = this.houseId + "/actuator/hue";
		// Check newLightPref against current Phillips hue status

		if (newlightPref != null && currLight != null && currLight.isAutomated()) {
			switch (newlightPref.getActionMethod()) {
			case "location":
				// if hue off turn on with updated settings
				if (!newlightPref.getLight().equals(currLight)) {
					// turn hue on
					MqttUtils.mqttPublish(new Gson().toJson(new Hue(newlightPref.getLight())), topic);

				} 
				break;
			case "motion":
				// check motion if update in last min turn on.
				if (!newlightPref.getLight().equals(currLight) && motionDetected(3600000, sensor)) {
					// turn hue on
					MqttUtils.mqttPublish(new Gson().toJson(new Hue(newlightPref.getLight())), topic);

				}
				break;
			case "light":
				// if light less than... turn on with updated settings
				if (!newlightPref.getLight().equals(currLight) && lightValueCheck(5, sensor)) {
					// turn hue on
					MqttUtils.mqttPublish(new Gson().toJson(new Hue(newlightPref.getLight())), topic);

				}
				break;
			default:
				System.out.println("NO CHANGED MADE");
				break;
			}
		} 

	}

	public void addHue() {
		hue = new HueSubscribe(houseId, "HueSub");
		hue.setActionMethod(this);
		hue.setTopic(houseId + "/actuator/hue/status");
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (hue.getMessage() != null) {
			currLight = new Light(DataFormatUtilities.jsonToLight(hue.getMessage()));
		}

	}

	public Light getCurrLight() {
		return currLight;
	}

	public void setCurrLight(Light currLight) {
		this.currLight = new Light(currLight);
		// check against light pref and change light if needed
		if (this.light != null) {
			lightAction(this.light, this.sensor);
		} 

	}

	public Boolean motionDetected(long time, GeneralPhidSensor sensor) {
		if ((sensor.getUpdateTimestamp() + time) >= System.currentTimeMillis()) {
			return true;
		}
		return false;
	}

	public Boolean lightValueCheck(int lightval, GeneralPhidSensor sensor) {
		if (sensor.getSensorValue() < lightval) {
			return true;
		}
		return false;

	}

	public House getHouseConfig() {
		if (house == null)
			return house = MqttUtils.getHouseConfiguration(this.houseId);
		return house;
	}

	public void setLightPref(LightPref light) {

		if (this.light != null && light.getActionMethod().equals("location")) {
			// TODO unset topic if topic set
			lightAction(light, new GeneralPhidSensor());
		} else if (this.light == null || light.getActionMethod() != this.light.getActionMethod()) {
			getHouseConfig();
			for (GeneralPhidSensor generalPhidSensor : house.getSensors()) {
				if (generalPhidSensor.getSensorName().contains(light.getActionMethod())) {
					setTopic(houseId + "/sensor/" + generalPhidSensor.getSensorId());
					break;
				}
			}
		}
		this.light = new LightPref(light);
	}

	@Override
	public HashMap<String, String> getMessages() {
		return null;
	}
}
