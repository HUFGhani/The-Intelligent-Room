package com.nickr.IoT.user.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Sensor {

		@SerializedName("sensorId")
		@Expose
		private int sensorId;
		@SerializedName("sensorName")
		@Expose
		private String sensorName;
		@SerializedName("sensorMethodType")
		@Expose
		private String sensorMethodType;
		@SerializedName("sensorPort")
		@Expose
		private int sensorPort;
		@SerializedName("updateTimestamp")
		@Expose
		private Long updateTimestamp;
		@SerializedName("sensorValue")
		@Expose
		private int sensorValue;


	public Sensor() {
		// TODO Auto-generated constructor stub
	}

	public Sensor(int sensorId, String sensorName, String sensorMethodType, int sensorPort,
			Long updateTimestamp, int sensorValue) {
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

	@Override
	public String toString() {
		return "sensors{" +
				"sensorId=" + sensorId +
				", sensorName='" + sensorName + '\'' +
				", sensorMethodType='" + sensorMethodType + '\'' +
				", sensorPort=" + sensorPort +
				", updateTimestamp=" + updateTimestamp +
				", sensorValue=" + sensorValue +
				'}';
	}
}
