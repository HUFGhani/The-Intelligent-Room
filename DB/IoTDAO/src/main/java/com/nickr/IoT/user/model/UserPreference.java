package com.nickr.IoT.user.model;


import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class UserPreference {
    private int userId;
    private String firstName;
    private String lastName;
    private int priority;

    public UserPreference(int userId, String firstName, String lastName, int priority,
			LightingPreference lightingPreference, HeatingPreference heatingPreference) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.priority = priority;
		this.lightingPreference = lightingPreference;
		this.heatingPreference = heatingPreference;
	}

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
