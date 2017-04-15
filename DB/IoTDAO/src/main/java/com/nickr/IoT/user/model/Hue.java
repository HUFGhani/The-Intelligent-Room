package com.nickr.IoT.user.model;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Hue implements Serializable
{
    private final static long serialVersionUID = 1283432260452140043L;

    @SerializedName("light")
    @Expose
    private Light light;

    private String HomeId;

    @SerializedName("on/off")
    @Expose
    private boolean isOnOff;

    @SerializedName("automated")
    @Expose
    private boolean automaticStatus;



    public Boolean isOnOff() {
        return isOnOff;
    }

    public void setOnOff(Boolean onOff) {
        isOnOff = onOff;
    }

    public Boolean getAutomaticStatus() {
        return automaticStatus;
    }

    public void setAutomaticStatus(Boolean automaticStatus) {
        this.automaticStatus = automaticStatus;
    }

    public Light getLight() {
        return light;
    }

    public void setLight(Light light) {
        this.light = light;
    }

	public String getHomeId() {
		return HomeId;
	}

	public void setHomeId(String homeId) {
		HomeId = homeId;
	}

    public Hue getHue(String jsonStr) {
        return new Gson().fromJson(jsonStr, Hue.class);
    }
}
