	package com.nickr.IoT.user.model;

import com.google.gson.Gson;

public class LogInRegisterResponse {
    private HouseConfiguration houseConfiguration;
    private UserPreference userPreference;
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
    
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public void setError(String error) {
        this.error = error;
    }
}
