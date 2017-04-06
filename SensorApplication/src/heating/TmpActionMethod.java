package heating;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import com.google.gson.Gson;
import sensors.GeneralPhidSensor;
import utils.DataFormatUtilities;
import utils.House;
import utils.MqttUtils;
import utils.PahoClientSub;
import utils.ServerComs;

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
		System.out.println(">>>>TA_LOG: TAM CREATED");
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
		System.out.println(">>>>TA_LOG nest added ");

		if (nest.getMessage() != null) {
			System.out.println(">>>>TA_LOG nest message not null");
			currTemp = new Nest(DataFormatUtilities.jsonToNest(nest.getMessage()));
			System.out.println(">>>>TA_LOG initialised curr temp - \n" + currTemp.toString());

		}

	}

	public Nest getCurrTemp() {
		return currTemp;
	}

	public void setCurrTemp(Nest currTemp) {
		System.out.println(">>>>TA_LOG: START SET CURR TEMP - " + currTemp.toString());
		this.currTemp = new Nest(currTemp);
		// check against light pref and change light if needed
		if (this.temp != null && !this.temp.getNest().equals(currTemp)) {
			tempAction(this.temp, this.sensor);
		} else {
			if (this.temp == null) {
				System.out.println(">>>>TA_LOG: TEMP PREF IS NULL");

			} else {
				System.out.println(">>>>TA_LOG: TEMP IS ALREADY SET");
			}
		}
		System.out.println(">>>>TA_LOG: END SET CURR TEMP");
	}

	public void tempAction(TemperaturePref temp, GeneralPhidSensor sensor) {
		String topic = this.houseId + "/actuator/nest";
		// Check newLightPref against current Phillips hue status
		System.out.println(">>>>TA_LOG: TEMP ACTION START");
		System.out.println(">>>>TA_LOG: TEMP PREF - " + temp.toString());

		if (temp != null) {

			switch (temp.getActionMethod()) {
			case "location":
				// if hue off turn on with updated settings
				if (!temp.getNest().equals(currTemp) && currTemp.isAutomated()) {
					// turn hue on
					System.out.println("LOC: TURNING NEST ON");
					System.out.println(new Gson().toJson(new Nest(temp.getNest())));
					MqttUtils.mqttPublish(new Gson().toJson(new Nest(temp.getNest())), topic);

				} else {
					System.out.println("LOC:NO ACTION TAKEN");
				}
				break;
			case "motion":
				// check motion if update in last min turn on.
				if (!temp.getNest().equals(currTemp) && currTemp.isAutomated()
						&& motionDetected(3600000, sensor)) {
					// turn hue on
					System.out.println("MOT: TURNING NEST ON");
					MqttUtils.mqttPublish(new Gson().toJson(new Nest(temp.getNest())), topic);

				} else {
					if (!motionDetected(3600000, sensor) && currTemp.isAutomated()
							&& !temp.getNest().equals(currTemp)) {
						ServerComs.turnHueOff(houseId);
					}
					System.out.println("MOT:NO ACTION TAKEN");
				}
				break;
			default:
				System.out.println("NO CHANGED MADE");
				break;
			}
		}
		System.out.println("TEMP ACTION END");

	}

	public Boolean motionDetected(long time, GeneralPhidSensor sensor) {
		if ((sensor.getUpdateTimestamp() + time) >= System.currentTimeMillis()) {
			System.out.println(
					">>>>TA_LOG: MOTION TIME CHECK: curr=" + System.currentTimeMillis() + " sensor last update = "
							+ sensor.getUpdateTimestamp() + " condition time = " + (System.currentTimeMillis() - time));
			return true;
		}
		return false;
	}

	private void getHouseConfig() {
		if (house == null) 
			this.house = MqttUtils.getHouseConfiguration(this.houseId);
	
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		this.message = message.toString();
		System.out.println(">>>>TA_LOG: MSG ARR -  " + message.toString());

		System.out.println(">>>>TA_LOG: SENSOR CREATION START");
		sensor = new GeneralPhidSensor(DataFormatUtilities.jsonToList(message.toString()).get(0));
		System.out.println(">>>>TA_LOG: SENSOR CREATION END -  " + sensor.toString());

		// setting against temp pref, change if necessary.
		if (temp == null || !temp.getActionMethod().equals("location")) {
			tempAction(this.temp, sensor);
		}
	}

	public void setTemperaturePref(TemperaturePref tmp) {
		System.out.println(">>>>TA_LOG: SETTING TEMP PREF");
		System.out.println(">>>>TA_LOG: New Tmp Pref: " + tmp.toString());

		if (tmp.getActionMethod().equals("location")) {
			System.out.println(">>>>TA_LOG: START LOCATION TEMP ACTION");
			tempAction(tmp, new GeneralPhidSensor());

		} else if (this.temp == null || tmp.getActionMethod() != this.temp.getActionMethod()) {
			getHouseConfig();
			System.out.println(">>>>TA_LOG: motion or light action method.");

			for (GeneralPhidSensor generalPhidSensor : house.getSensors()) {
				System.out.println(">>>>TA_LOG: LOOP sensor type: " + generalPhidSensor.getSensorName()
						+ " Action method: " + tmp.getActionMethod());
				if (generalPhidSensor.getSensorName().contains(tmp.getActionMethod())) {
					setTopic(houseId + "/sensor/" + generalPhidSensor.getSensorId());
					break;
				}
			}

		} else {
			System.out.println(">>>>TA_LOG: NO PREF SET");
		}

		this.temp = new TemperaturePref(tmp);
		System.out.println(">>>>TA_LOG: TEMP PREF SET");

	}
}
