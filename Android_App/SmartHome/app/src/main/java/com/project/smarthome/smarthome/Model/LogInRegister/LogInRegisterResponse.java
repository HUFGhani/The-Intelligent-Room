package com.project.smarthome.smarthome.Model.LogInRegister;


import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.project.smarthome.smarthome.Model.HouseConfiguration;
import com.project.smarthome.smarthome.Model.Preferences.UserPreference;

public class LogInRegisterResponse {
    @SerializedName("houseConfiguration")
    private HouseConfiguration houseConfiguration;

    @SerializedName("sensuserPreferenceorId")
    private UserPreference userPreference;

    @SerializedName("error")
    private String error;

    public LogInRegisterResponse() {

    }

    public LogInRegisterResponse(HouseConfiguration houseConfiguration, UserPreference userPreference) {
        this.houseConfiguration = houseConfiguration;
        this.userPreference = userPreference;
    }

    public HouseConfiguration getHouseConfiguration() {
        return houseConfiguration;
    }

    public void setHouseConfiguration(HouseConfiguration houseConfiguration) {
        this.houseConfiguration = houseConfiguration;
    }

    public UserPreference getUserPreference() {
        return userPreference;
    }

    public void setUserPreference(UserPreference userPreference) {
        this.userPreference = userPreference;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
