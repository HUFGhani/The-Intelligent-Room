package com.nickr.IoT.user.model;

public class LightingPreference extends PreferenceBase {

    private int red;
    private int green;
    private int blue;
    private int saturation;
    private int brightness;

    public LightingPreference() {

    }

    public LightingPreference(int red, int green, int blue, int saturation, int brightness, String automationType, int actionPriority) {
		super();
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.saturation = saturation;
		this.brightness = brightness;
		this.automationType = automationType;
    	this.actionPriority = actionPriority;
	}

	public int getSaturation() {
        return saturation;
    }

    public void setSaturation(int saturation) {
        this.saturation = saturation;
    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

	public int getRed() {
		return red;
	}

	public void setRed(int red) {
		this.red = red;
	}

	public int getGreen() {
		return green;
	}

	public void setGreen(int green) {
		this.green = green;
	}

	public int getBlue() {
		return blue;
	}

	public void setBlue(int blue) {
		this.blue = blue;
	}
}
