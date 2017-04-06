package lighting;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Light implements Serializable {

	@SerializedName("name")
	private String name = null;
	@SerializedName("on/off")
	private boolean onOff = true;
	@SerializedName("colour")
	private Colour colour = null;
	@SerializedName("brightness")
	private int brightness = 254;
	@SerializedName("saturation")
	private int saturation = 254;
	private boolean automated = true;

	private final static long serialVersionUID = 4897633775422404008L;

	public Light() {
	}

	public Light(Light light) {
		this.name = light.getName();
		this.onOff = light.isOnOff();
		this.brightness = light.getBrightness();
		this.saturation = light.getSaturation();
		this.colour = new Colour(light.getColour());
		this.automated = light.isAutomated();
	}

	public Light(String name, boolean onOff, Colour colour, int brightness, int saturation, Boolean automated) {
		super();
		this.name = name;
		this.onOff = onOff;
		this.colour = new Colour(colour);
		this.brightness = brightness;
		this.saturation = saturation;
		this.automated = automated;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isOnOff() {
		return onOff;
	}

	public void setOnOff(boolean onOff) {
		this.onOff = onOff;
	}

	public Colour getColour() {
		return colour;
	}

	public void setColour(Colour colour) {
		this.colour = colour;
	}

	public int getBrightness() {
		return brightness;
	}

	public void setBrightness(int brighness) {
		this.brightness = brighness;
	}

	public int getSaturation() {
		return saturation;
	}

	public void setSaturation(int saturation) {
		this.saturation = saturation;
	}

	public boolean isAutomated() {
		return automated;
	}

	public void setAutomated(boolean automated) {
		this.automated = automated;
	}

	@Override
	public String toString() {
		return "Light [name=" + name + ", onOff=" + onOff + ", colour=" + colour + ", brightness=" + brightness
				+ ", saturation=" + saturation + ", automated=" + automated + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (automated ? 1231 : 1237);
		result = prime * result + brightness;
		result = prime * result + ((colour == null) ? 0 : colour.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (onOff ? 1231 : 1237);
		result = prime * result + saturation;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Light other = (Light) obj;
		if (automated != other.automated)
			return false;
		if (brightness != other.brightness)
			return false;
		if (colour == null) {
			if (other.colour != null)
				return false;
		} else if (!colour.equals(other.colour))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (onOff != other.onOff)
			return false;
		if (saturation != other.saturation)
			return false;
		return true;
	}

}
