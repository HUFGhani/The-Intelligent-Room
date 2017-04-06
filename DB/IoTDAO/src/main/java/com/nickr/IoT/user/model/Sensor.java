package com.nickr.IoT.user.model;

import java.util.Date;

public class Sensor {

		private int sensorId;
		private String sensorName;
		private String sensorMethodType; 
		private int sensorPort; 
		private Date updateTimestamp;
		private int sensorValue; 
		
			
	public Sensor() {
		// TODO Auto-generated constructor stub
	}

	public Sensor(int sensorId, String sensorName, String sensorMethodType, int sensorPort,
			Date updateTimestamp, int sensorValue) {
		super();
		this.sensorId = sensorId;
		this.sensorName = sensorName;
		this.sensorMethodType = sensorMethodType;
		this.sensorPort = sensorPort;
		this.updateTimestamp = updateTimestamp;
		this.sensorValue = sensorValue;
	}

	public int getSensorId() {
		return sensorId;
	}

	public void setSensorId(int sensorId) {
		this.sensorId = sensorId;
	}

	public String getSensorName() {
		return sensorName;
	}

	public void setSensorName(String sensorName) {
		this.sensorName = sensorName;
	}

	public String getSensorMethodType() {
		return sensorMethodType;
	}

	public void setSensorMethodType(String sensorMethodType) {
		this.sensorMethodType = sensorMethodType;
	}

	public int getSensorPort() {
		return sensorPort;
	}

	public void setSensorPort(int sensorPort) {
		this.sensorPort = sensorPort;
	}


	public Date getUpdateTimestamp() {
		return updateTimestamp;
	}

	public void setUpdateTimestamp(Date updateTimestamp) {
		this.updateTimestamp = updateTimestamp;
	}

	public int getSensorValue() {
		return sensorValue;
	}

	public void setSensorValue(int sensorValue) {
		this.sensorValue = sensorValue;
	}

}