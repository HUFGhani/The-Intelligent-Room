package model;

import com.google.gson.Gson;

public class RegistrationRequest {

    private String homeId;
    private String homePassword;
    private String firstName;
    private String lastName;
    private String email;
    private String userPassword;
    private String userPriority;

    public String getUserPriority() {
		return userPriority;
	}

	public void setUserPriority(String userPriority) {
		this.userPriority = userPriority;
	}

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
