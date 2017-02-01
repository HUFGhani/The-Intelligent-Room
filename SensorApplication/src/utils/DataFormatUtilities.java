package utils;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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

		List<GeneralPhidSensor> courses = new Gson().fromJson(jsonStr, new TypeToken<List<GeneralPhidSensor>>() {
		}.getType());
		return courses;
	}

}
