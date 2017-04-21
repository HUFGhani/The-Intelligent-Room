package com.project.smarthome.smarthome.Model.Preferences;


import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.project.smarthome.smarthome.Model.Preferences.Heating.HeatingPreference;
import com.project.smarthome.smarthome.Model.Preferences.Lighting.LightingPreference;

public class UserPreference {
    private int userId;
    private String firstName;
    private String lastName;
    private int priority;

    @SerializedName("lightPref")
    public LightingPreference lightingPreference;

    @SerializedName("tmpPref")
    public HeatingPreference heatingPreference;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
