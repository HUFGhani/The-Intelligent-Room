package com.nickr.IoT.user.model;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Light implements Serializable
{

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("on/off")
    @Expose
    private boolean onOff;
    @SerializedName("colour")
    @Expose
    private Colour colour;
    @SerializedName("brightness")
    @Expose
    private String brightness;
    @SerializedName("Saturation")
    @Expose
    private String saturation;
    
    public boolean isAutomaticStatus() {
		return automaticStatus;
	}

	public void setAutomaticStatus(boolean automaticStatus) {
		this.automaticStatus = automaticStatus;
	}

	@SerializedName("AutomaticStatus")
    @Expose
    private boolean automaticStatus;
    
    public Light(String name, boolean onOff, Colour colour, String brightness, String saturation,
			boolean automaticStatus) {
		super();
		this.name = name;
		this.onOff = onOff;
		this.colour = colour;
		this.brightness = brightness;
		this.saturation = saturation;
		this.automaticStatus = automaticStatus;
	}

	private final static long serialVersionUID = 4897633775422404008L;

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

    public String getBrightness() {
        return brightness;
    }

    public void setBrightness(String brightness) {
        this.brightness = brightness;
    }

    public String getSaturation() {
        return saturation;
    }

    public void setSaturation(String saturation) {
        this.saturation = saturation;
    }

}
