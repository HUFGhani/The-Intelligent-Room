
package com.nickr.IoT.user.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserPreference implements Serializable {
    public UserPreference(int userId, String firstName, String lastName, int priority, LightPref lightPref, TmpPref tmpPref) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.priority = priority;
        this.lightPref = lightPref;
        this.tmpPref = tmpPref;
    }

    @SerializedName("userId")
    @Expose
    private int userId;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("priority")
    @Expose
    private int priority;
    @SerializedName("lightPref")
    @Expose
    private LightPref lightPref;
    @SerializedName("tmpPref")
    @Expose
    private TmpPref tmpPref;

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

    public LightPref getLightPref() {
        return lightPref;
    }

    public void setLightPref(LightPref lightPref) {
        this.lightPref = lightPref;
    }

    public TmpPref getTmpPref() {
        return tmpPref;
    }

    public void setTmpPref(TmpPref tmpPref) {
        this.tmpPref = tmpPref;
    }

}
