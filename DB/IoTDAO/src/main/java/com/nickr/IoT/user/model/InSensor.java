package com.nickr.IoT.user.model;

/**
 * Created by nickr on 10/04/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InSensor {
    @SerializedName("sensorName")
    @Expose
    private String sensorName;
    @SerializedName("sensorId")
    @Expose
    private String sensorId;
    @SerializedName("sensorMethodType")
    @Expose
    private String sensorMethodType;
    @SerializedName("updateTimestamp")
    @Expose
    private Long updateTimestamp;
    @SerializedName("sensorPort")
    @Expose
    private Integer sensorPort;
    @SerializedName("sensorValue")
    @Expose
    private Integer sensorValue;

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public String getSensorMethodType() {
        return sensorMethodType;
    }

    public void setSensorMethodType(String sensorMethodType) {
        this.sensorMethodType = sensorMethodType;
    }

    public Long getUpdateTimestamp() {
        return updateTimestamp;
    }

    public void setUpdateTimestamp(Long updateTimestamp) {
        this.updateTimestamp = updateTimestamp;
    }

    public Integer getSensorPort() {
        return sensorPort;
    }

    public void setSensorPort(Integer sensorPort) {
        this.sensorPort = sensorPort;
    }

    public Integer getSensorValue() {
        return sensorValue;
    }

    public void setSensorValue(Integer sensorValue) {
        this.sensorValue = sensorValue;
    }
}
