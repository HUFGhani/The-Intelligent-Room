package com.project.smarthome.smarthome.Model.LogInRegister;


import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class RegistrationRequest {

    @SerializedName("homeId")
    private String homeId;

    @SerializedName("homePassword")
    private String homePassword;

    @SerializedName("firstName")
    private String firstName;

    @SerializedName("lastName")
    private String lastName;

    @SerializedName("email")
    private String email;

    @SerializedName("userPassword")
    private String userPassword;

    public RegistrationRequest() {

    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public String getHomeId() {
        return homeId;
    }

    public void setHomeId(String homeId) {
        this.homeId = homeId;
    }

    public String getHomePassword() {
        return homePassword;
    }

    public void setHomePassword(String homePassword) {
        this.homePassword = homePassword;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

}
