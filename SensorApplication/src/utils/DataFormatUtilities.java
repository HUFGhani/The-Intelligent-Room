package utils;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import heating.Nest;
import lighting.Light;
import sensors.GeneralPhidSensor;

public class DataFormatUtilities {
	/**
	 * Generates and returns a JSON string of the objects provided in a list.
	 * 
	 * @param sensors
	 *            a list of POJOs
	 * @return
	 */
	public static String generateJSON(List sensors) {
		return new Gson().toJson(sensors);
	}

	/**
	 * Generates and returns a List of GeneralPhidSensor objects using the json
	 * string provided.
	 * 
	 * @param jsonStr
	 *            a string of json data
	 * @return
	 */
	public static List<GeneralPhidSensor> jsonToList(String jsonStr) {
		System.out.println(jsonStr);
		List<GeneralPhidSensor> sensors = new Gson().fromJson(jsonStr, new TypeToken<List<GeneralPhidSensor>>() {
		}.getType());
		return sensors;
	}

	/**
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static House jsonToHouse(String jsonStr) {
		House house = new Gson().fromJson(jsonStr, House.class);
		return house;

	}

	/**
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static Nest jsonToNest(String jsonStr) {
		Nest nest = new Gson().fromJson(jsonStr, Nest.class);
		return nest;

	}

	/**
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static User jsonToUser(String jsonStr) {
		User user = new Gson().fromJson(jsonStr, User.class);
		return user;

	}

	/**
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static GeneralPhidSensor jsonToSensor(String jsonStr) {
		System.out.println(jsonStr);
		GeneralPhidSensor sensor = new Gson().fromJson(jsonStr, GeneralPhidSensor.class);
		return sensor;

	}

	/**
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static Light jsonToLight(String jsonStr) {
		JsonElement jsonElem = new JsonParser().parse(jsonStr);
		JsonObject jsonObj = jsonElem.getAsJsonObject();
		jsonObj = jsonObj.getAsJsonObject("light");

		return new Gson().fromJson(jsonObj.toString(), Light.class);
	}

}
