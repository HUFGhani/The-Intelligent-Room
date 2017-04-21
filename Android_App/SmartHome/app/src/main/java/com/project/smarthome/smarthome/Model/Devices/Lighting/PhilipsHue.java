package com.project.smarthome.smarthome.Model.Devices.Lighting;


import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

// This class is only used to publish and subscribe to the message queues.
// In use the LightingDevice object is used by itself.

public class PhilipsHue {

    @SerializedName("light")
    private LightingDevice light;

    public PhilipsHue(LightingDevice device) {
        this.light = device;
    }

    public LightingDevice getLight() {
        return light;
    }

    public void setLight(LightingDevice light) {
        this.light = light;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

}
