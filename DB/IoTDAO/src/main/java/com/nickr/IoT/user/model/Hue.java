package com.nickr.IoT.user.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Hue implements Serializable
{

    @SerializedName("light")
    @Expose
    private Light light;
    private final static long serialVersionUID = 1283432260452140043L;
    private int HomeId;


    public Light getLight() {
        return light;
    }

    public void setLight(Light light) {
        this.light = light;
    }

	public int getHomeId() {
		return HomeId;
	}

	public void setHomeId(int homeId) {
		HomeId = homeId;
	}

}
