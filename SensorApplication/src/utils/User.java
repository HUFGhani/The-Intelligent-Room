package utils;

import heating.TemperaturePref;
import lighting.LightPref;

public class User {
	private String userId, firstName, lastName;
	private int priority;
	LightPref lightPref;
	TemperaturePref tmpPref;

	public User() {

	}

	public User(User user) {
		this.userId = user.getUserId();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.priority = user.getPriority();
		this.lightPref = new LightPref(user.getLightPref());
		this.tmpPref = new TemperaturePref(user.getTmpPref());
	}

	public User(String userId, String firstName, String lastName, int priority) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.priority = priority;
	}

	public LightPref getLightPref() {
		return lightPref;
	}

	public void setLightPref(LightPref lightPref) {
		this.lightPref = lightPref;
	}

	public TemperaturePref getTmpPref() {
		return tmpPref;
	}

	public void setTmpPref(TemperaturePref tmpPref) {
		this.tmpPref = tmpPref;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
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
		return "User [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", priority="
				+ priority + ", lightPref=" + lightPref + ", tmpPref=" + tmpPref + "]";
	}

}
