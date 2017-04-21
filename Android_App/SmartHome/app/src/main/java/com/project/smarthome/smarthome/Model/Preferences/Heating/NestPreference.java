package com.project.smarthome.smarthome.Model.Preferences.Heating;


import com.google.gson.annotations.SerializedName;

public class NestPreference {
    @SerializedName("target_temperature_c")
    public int targetTemp;

    public NestPreference() {

    }
}
