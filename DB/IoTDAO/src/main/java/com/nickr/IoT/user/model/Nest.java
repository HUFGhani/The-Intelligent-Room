package com.nickr.IoT.user.model;

import java.sql.Date;

public class Nest {
	
	private int temperature;
	private Date lastUpdated;
	private Boolean automaticStatus;
	private int HomeID;
	
	
	public int getHomeID() {
		return HomeID;
	}

	public void setHomeID(int homeID) {
		HomeID = homeID;
	}

	public Nest(int temperature, Date lastUpdated, Boolean automaticStatus) {
		super();
		this.temperature = temperature;
		this.lastUpdated = lastUpdated;
		this.automaticStatus = automaticStatus;
	}
	
	public int getTemperature() {
		return temperature;
	}
	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}
	public Date getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	public Boolean getAutomaticStatus() {
		return automaticStatus;
	}
	public void setAutomaticStatus(Boolean automaticStatus) {
		this.automaticStatus = automaticStatus;
	}
}
