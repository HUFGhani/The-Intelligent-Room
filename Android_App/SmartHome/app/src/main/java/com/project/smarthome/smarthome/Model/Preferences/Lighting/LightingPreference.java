package com.project.smarthome.smarthome.Model.Preferences.Lighting;


import com.google.gson.annotations.SerializedName;
import com.project.smarthome.smarthome.Model.Devices.Lighting.CustomLightColour;
import com.project.smarthome.smarthome.Model.Preferences.PreferenceBase;

public class LightingPreference extends PreferenceBase {

    @SerializedName("light")
    private HuePreference light;

    public LightingPreference() {

    }

    public CustomLightColour getColour() {
        checkCreated();
        return light.colour;
    }

    public void setColour(CustomLightColour colour) {
        checkCreated();
        light.colour = colour;
    }

    public int getSaturation() {
        checkCreated();
        return light.saturation;
    }

    public void setSaturation(int saturation) {
        checkCreated();
        light.saturation = saturation;
    }

    public int getBrightness() {
        checkCreated();
        return light.brightness;
    }

    public void setBrightness(int brightness) {
        checkCreated();
        light.brightness = brightness;
    }

    private void checkCreated() {
        if (light == null) {
            light = new HuePreference(new CustomLightColour(33, 150, 243), 50, 50);
        }
    }
}
