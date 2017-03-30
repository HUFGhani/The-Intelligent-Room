package heating;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import com.google.gson.Gson;
import sensors.GeneralPhidSensor;
import utils.DataFormatUtilities;
import utils.House;
import utils.MqttUtils;
import utils.PahoClientSub;

public class TmpActionMethod extends PahoClientSub {
	private GeneralPhidSensor sensor = null;
	private House house = null;
	private NestSubscribe nest = null;
	private TemperaturePref temp;
	private Nest currTemp;
	private String houseId = null;

	public TmpActionMethod(String houseId, String clientId) {
		super(serverURI, clientId);
		this.houseId = houseId;
		addNest();
	}

	public void addNest() {
		nest = new NestSubscribe(houseId, "NestSub");
		nest.setActionMethod(this);
		nest.setTopic(houseId + "/actuator/nest/status");
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (nest.getMessage() != null) {
			currTemp = new Nest(DataFormatUtilities.jsonToNest(nest.getMessage()));
		}

	}

	public Nest getCurrTemp() {
		return currTemp;
	}

	public void setCurrTemp(Nest currTemp) {
		this.currTemp = new Nest(currTemp);
		// check against light pref and change light if needed
		if (this.temp != null) {
			tempAction(this.temp, this.sensor);
		}
	}

	public void tempAction(TemperaturePref temp, GeneralPhidSensor sensor) {
		String topic = this.houseId + "/actuator/nest";
		// Check newLightPref against current Phillips hue status

		if (temp != null && currTemp != null && currTemp.isAutomated()) {
			switch (temp.getActionMethod()) {
			case "location":
				// if hue off turn on with updated settings
				if (!temp.getNest().equals(currTemp)) {
					// turn hue on
					MqttUtils.mqttPublish(new Gson().toJson(new Nest(temp.getNest())), topic);
				} 
				break;
			case "motion":
				// check motion if update in last min turn on.
				if (!temp.getNest().equals(currTemp) && motionDetected(3600000, sensor)) {
					// turn hue on
					MqttUtils.mqttPublish(new Gson().toJson(new Nest(temp.getNest())), topic);
				} 
				break;
			default:
				System.out.println("NO CHANGED MADE");
				break;
			}
		} 

	}

	public Boolean motionDetected(long time, GeneralPhidSensor sensor) {
		if ((sensor.getUpdateTimestamp() + time) >= System.currentTimeMillis()) {
			return true;
		}
		return false;
	}

	public House getHouseConfig() {
		if (house == null)
			return house = MqttUtils.getHouseConfiguration(this.houseId);
		return house;
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		this.message = message.toString();
		sensor = new GeneralPhidSensor(DataFormatUtilities.jsonToList(message.toString()).get(0));
		// setting against light pref, change if necessary.
		tempAction(this.temp, sensor);

	}

	public void setTemperaturePref(TemperaturePref tmp) {
		if (tmp.getActionMethod().equals("location")) {
			tempAction(tmp, new GeneralPhidSensor());

		} else if (this.temp == null || tmp.getActionMethod() != this.temp.getActionMethod()) {
			getHouseConfig();
			for (GeneralPhidSensor generalPhidSensor : house.getSensors()) {
				if (generalPhidSensor.getSensorName().contains(tmp.getActionMethod())) {
					setTopic(houseId + "/sensor/" + generalPhidSensor.getSensorId());
					break;
				}
			}

		} 
		this.temp = new TemperaturePref(tmp);
	}
}
