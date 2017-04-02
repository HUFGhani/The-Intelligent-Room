package lighting;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Hue implements Serializable {

	@SerializedName("light")
	@Expose
	private Light light;
	

	private final static long serialVersionUID = 1283432260452140043L;


	public Hue(Light light) {
		this.light = new Light(light);
	}

	public Light getLight() {
		return light;
	}

	public void setLight(Light light) {
		this.light = light;
	}


}
