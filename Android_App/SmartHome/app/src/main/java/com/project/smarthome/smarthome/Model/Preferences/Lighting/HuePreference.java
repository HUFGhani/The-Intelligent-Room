package com.project.smarthome.smarthome.Model.Preferences.Lighting;


import com.google.gson.annotations.SerializedName;
import com.project.smarthome.smarthome.Model.Devices.Lighting.CustomLightColour;

public class HuePreference {

    @SerializedName("colour")
    protected CustomLightColour colour;

    @SerializedName("saturation")
    protected int saturation;

    @SerializedName("brightness")
    protected int brightness;

    public HuePreference() {

    }

    public HuePreference(CustomLightColour colour, int saturation, int brightness) {
        this.colour = colour;
        this.saturation = saturation;
        this.brightness = brightness;
    }
}
