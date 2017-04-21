package com.project.smarthome.smarthome.Model.Preferences.Heating;


import com.google.gson.annotations.SerializedName;
import com.project.smarthome.smarthome.Model.Preferences.PreferenceBase;

public class HeatingPreference extends PreferenceBase {

    @SerializedName("nest")
    public NestPreference nest;

    public HeatingPreference() {

    }

    public int getTargetTemp() {
        if (nest == null) {
            nest = new NestPreference();
        }
        return nest.targetTemp;
    }

    public void setTargetTemp(int targetTemp) {
        if (nest == null) {
            nest = new NestPreference();
        }
        nest.targetTemp = targetTemp;
    }
}
