package lighting;

import java.util.HashMap;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import com.google.gson.Gson;
import sensors.GeneralPhidSensor;
import utils.DataFormatUtilities;
import utils.House;
import utils.MqttUtils;
import utils.PahoClientSub;
import utils.ServerComs;

public class LightActionMethod extends PahoClientSub {
	private LightPref light = null;
	private Light currLight = null;
	private String houseId = null;
	private HueSubscribe hue = null;
	private House house = null;
	private GeneralPhidSensor sensor = null;
	private String actionTopic = "";

	public LightActionMethod(String houseId, String clientId) {
		super(serverURI, clientId);
		this.houseId = houseId;
		addHue();
		System.out.println(">>>>LA_LOG LAM CREATED");
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		this.message = message.toString();
		System.out.println(">>>>LA_LOG: MSG ARR -  " + message.toString());

		System.out.println(">>>>LA_LOG: SENSOR CREATION START" + message.toString());
		sensor = new GeneralPhidSensor(DataFormatUtilities.jsonToList(message.toString()).get(0));
		System.out.println(">>>>LA_LOG: SENSOR CREATION END -  " + sensor.toString());

		System.out.println(">>>>LA_LOG: SENSOR MSG CHANGED TO - \n " + light.toString() + "\n");

		// setting against light pref, change if necessary.
		if (light == null || !light.getActionMethod().equals("location")) {
			lightAction(this.light, sensor);
		}
	}

	private void lightAction(LightPref newlightPref, GeneralPhidSensor sensor) {
		String topic = this.houseId + "/actuator/hue";
		// Check newLightPref against current Phillips hue status
		System.out.println(">>>>LA_LOG: LIGHT ACTION START");
		System.out.println(">>>>LA_LOG: LIGHT PREF - \n" + newlightPref.toString());

		if (newlightPref != null) {
			switch (newlightPref.getActionMethod()) {
			case "location":
				System.out.println("LOCATION");

				// if hue off turn on with updated settings
				if (currLight.isAutomated() && !newlightPref.getLight().equals(currLight)) {
					// turn hue on
					System.out.println("LOC: TURNING HUE ON");
					System.out.println(new Gson().toJson(new Hue(newlightPref.getLight())));
					MqttUtils.mqttPublish(new Gson().toJson(new Hue(newlightPref.getLight())), topic);

				} else {

					System.out.println("LOC:NO ACTION TAKEN");
				}
				break;
			case "motion":
				System.out.println("MOTION");

				// check motion if update in last min turn on.
				if (currLight.isAutomated() && !newlightPref.getLight().equals(currLight)
						&& motionDetected(3600000, sensor)) {
					// turn hue on
					System.out.println("NEW LIGHT: " + newlightPref.getLight().toString());
					System.out.println("CURR LIGHT: " + currLight.toString());

					System.out.println("MOT: TURNING HUE ON");
					MqttUtils.mqttPublish(new Gson().toJson(new Hue(newlightPref.getLight())), topic);

				} else {
					if (!motionDetected(3600000, sensor) && currLight.isAutomated()
							&& !newlightPref.getLight().equals(currLight)) {
						ServerComs.turnHueOff(houseId);
					}
					System.out.println("MOT:NO ACTION TAKEN");
				}
				break;
			case "light":
				System.out.println("LIGHT");

				// if light less than... turn on with updated settings
				if (currLight.isAutomated() && !newlightPref.getLight().equals(currLight)
						&& lightValueCheck(5, sensor)) {
					// turn hue on
					System.out.println("LIG: TURNING HUE ON");
					MqttUtils.mqttPublish(new Gson().toJson(new Hue(newlightPref.getLight())), topic);

				} else {

					if (!lightValueCheck(5, sensor) && currLight.isAutomated()
							&& !newlightPref.getLight().equals(currLight)) {
						ServerComs.turnHueOff(houseId);
					} else if (!lightValueCheck(5, sensor) && currLight.isAutomated()) {
						ServerComs.turnHueOff(houseId);
					}
					System.out.println("LIG:NO ACTION TAKEN");
				}
				break;
			default:
				System.out.println("NO CHANGED MADE");
				break;
			}
		}
		System.out.println("LIGHT ACTION END");

	}

	public void setLightPref(LightPref light) {
		if (light == null ) {
			this.light = null;
			System.out.println(">>>>LA_LOG: SETTING PREF TO NULL");

			if (actionTopic != "") {
				try {
					System.out.println(">>>>LA_LOG: START ACTION METHOD UNSUBSCRIBE - " + actionTopic);

					client.unsubscribe(actionTopic);
					System.out.println(">>>>LA_LOG: END ACTION METHOD UNSUBSCRIBE - " + actionTopic);
				} catch (MqttException e) {
					e.printStackTrace();
				}
			}

		} else {

			System.out.println(">>>>LA_LOG: SETTING LIGHT PREF");
			System.out.println(">>>>LA_LOG:" + light.toString());

			// this.light != null &&
			if (light.getActionMethod().equals("location")) {
				System.out.println(">>>>LA_LOG: location light action method.");
				lightAction(light, new GeneralPhidSensor());

			} else if (this.light == null || light.getActionMethod() != this.light.getActionMethod()) {
				getHouseConfig();

				System.out.println(">>>>LA_LOG: motion or light action method.");
				if (house == null) {
					System.out.println("HOUSE NULL");
				} else {
					System.out.println("HOUSE NOT NULL");
				}
				for (GeneralPhidSensor generalPhidSensor : house.getSensors()) {
					System.out.println("LA_LOG: LOOP sensor type: " + generalPhidSensor.getSensorName()
							+ " Action method: " + light.getActionMethod());
					if (generalPhidSensor.getSensorName().contains(light.getActionMethod())) {
						if (actionTopic != "") {
							try {
								System.out.println(">>>>LA_LOG: START ACTION METHOD UNSUBSCRIBE - " + actionTopic);

								client.unsubscribe(actionTopic);
								System.out.println(">>>>LA_LOG: END ACTION METHOD UNSUBSCRIBE - " + actionTopic);
							} catch (MqttException e) {
								e.printStackTrace();
							}
						}
						actionTopic = houseId + "/sensor/" + generalPhidSensor.getSensorId(); 
						setTopic(houseId + "/sensor/" + generalPhidSensor.getSensorId());
						System.out.println(">>>>LA_LOG: END ACTION METHOD SUBSCRIBE - " + actionTopic);
						break;
					}
				}
			} else {
				System.out.println(">>>>LA_LOG: light action method already set.");
			}

			this.light = new LightPref(light);

			System.out.println(">>>>LA_LOG: LIGHT PREF SET " + this.light.toString());

		}
	}

	public void addHue() {
		hue = new HueSubscribe(houseId, "HueSub");
		hue.setActionMethod(this);
		hue.setTopic(houseId + "/actuator/hue/status");
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(">>>>LA_LOG hue added ");
		if (hue.getMessage() != null) {
			System.out.println(">>>>LA_LOG hue message not null");
			currLight = new Light(DataFormatUtilities.jsonToLight(hue.getMessage()));
			System.out.println(">>>>LA_LOG initialised curr light - \n" + currLight.toString());

		}

	}

	public Light getCurrLight() {
		return currLight;
	}

	public void setCurrLight(Light currLight) {
		System.out.println(">>>>LA_LOG START SET CURR LIGHT");
		this.currLight = new Light(currLight);
		// check against light pref and change light if needed

		if (this.light != null && !this.light.getLight().equals(currLight)) {
			System.out.println("????????????? LP " + light.getLight().toString());
			System.out.println("????????????? CL" + currLight.toString());

			lightAction(this.light, this.sensor);
		} else if (currLight.isAutomated() && this.light == null) {
			if (!currLight.isOnOff())
				ServerComs.turnHueOff(houseId);
		} else {
			if (this.light == null) {
				System.out.println(">>>>LA_LOG: LIGHT PREF IS NULL");

			} else {
				System.out.println(">>>>LA_LOG: LIGHT IS ALREADY SET");
			}
		}
		System.out.println(">>>>LA_LOG: END SET CURR LIGHT");

	}

	public Boolean motionDetected(long time, GeneralPhidSensor sensor) {
		if ((sensor.getUpdateTimestamp() + time) >= System.currentTimeMillis()) {
			System.out.println(
					">>>>LA_LOG: MOTION TIME CHECK: curr=" + System.currentTimeMillis() + " sensor last update = "
							+ sensor.getUpdateTimestamp() + " condition time = " + (System.currentTimeMillis() - time));
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

	private void getHouseConfig() {
		if (house == null)
			this.house = MqttUtils.getHouseConfiguration(this.houseId);

	}

	@Override
	public HashMap<String, String> getMessages() {
		return null;
	}
}
