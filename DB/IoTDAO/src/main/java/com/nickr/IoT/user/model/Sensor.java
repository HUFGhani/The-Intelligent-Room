package com.nickr.IoT.user.model;

import java.util.Date;

public class Sensor {

		private String sensorId;
		private String sensorName;
		private String sensorMethodType; 
		private int sensorPort; 
		private Long updateTimestamp;
		private int sensorValue; 
		
			
	public Sensor() {
		// TODO Auto-generated constructor stub
	}

	public Sensor(String sensorId, String sensorName, String sensorMethodType, int sensorPort,
			Long updateTimestamp, int sensorValue) {
		super();
		this.sensorId = sensorId;
		this.sensorName = sensorName;
		this.sensorMethodType = sensorMethodType;
		this.sensorPort = sensorPort;
		this.updateTimestamp = updateTimestamp;
		this.sensorValue = sensorValue;
	}

	public String getSensorId() {
		return sensorId;
	}

	public void setSensorId(String sensorId) {
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


	public Long getUpdateTimestamp() {
		return updateTimestamp;
	}

	public void setUpdateTimestamp(Long updateTimestamp) {
		this.updateTimestamp = updateTimestamp;
	}

	public int getSensorValue() {
		return sensorValue;
	}

	public void setSensorValue(int sensorValue) {
		this.sensorValue = sensorValue;
	}

}
