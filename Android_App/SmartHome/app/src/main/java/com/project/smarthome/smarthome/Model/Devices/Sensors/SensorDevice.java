package com.project.smarthome.smarthome.Model.Devices.Sensors;


import android.util.Log;

import com.google.gson.annotations.SerializedName;
import com.project.smarthome.smarthome.Model.Devices.Device;
import com.project.smarthome.smarthome.Model.Devices.DeviceBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SensorDevice extends DeviceBase implements Device {

    public static final int SENSOR_TYPE_MOTION = 1;
    public static final int SENSOR_TYPE_LIGHT = 2;
    public static final int SENSOR_TYPE_TOUCH = 3;

    @SerializedName("sensorId")
    private int id;

    @SerializedName("sensorName")
    private String name;

    @SerializedName("updateTimestamp")
    private long lastUpdated;

    @SerializedName("sensorMethodType")
    private String sensorMethodType;

    @SerializedName("sensorPort")
    private int port;

    @SerializedName("sensorValue")
    private int sensorValue;

    public SensorDevice() {
    }

    public SensorDevice(int id, String name, String sensorMethodType, int port) {
        this.id = id;
        this.name = name;
        this.sensorMethodType = sensorMethodType;
        this.port = port;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getDeviceType() {
        return DEVICE_TYPE_SENSOR;
    }

    public String getSensorMethodType() {
        return sensorMethodType;
    }

    public void setSensorMethodType(String sensorMethodType) {
        final List<String> validSensorMethodTypes = new ArrayList<>(Arrays.asList("onChanged", "average"));
        if (!validSensorMethodTypes.contains(sensorMethodType)) {
            throw new IllegalArgumentException("sensorMethodType invalid - " + sensorMethodType);
        }
        this.sensorMethodType = sensorMethodType;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getSensorValue() {
        return sensorValue;
    }

    public int getSensorType() {
        if (name == null) {
            Log.e("SensorDevice", "getSensorType: ", new UnsupportedOperationException("Name is null when getting type"));
        }
        switch (name) {
            case "motionSensor" : return SENSOR_TYPE_MOTION;
            case "lightSensor" : return SENSOR_TYPE_LIGHT;
            case "touchSensor" : return SENSOR_TYPE_TOUCH;
            default: throw new UnsupportedOperationException("Invalid sensor type");
        }
    }

    @Override
    public String getName() {
        if (name == null) {
            return "";
        }
        switch (name) {
            case "motionSensor" : return "Motion Sensor";
            case "lightSensor" : return "Light Sensor";
            case "touchSensor" : return "Touch Sensor";
            default: throw new UnsupportedOperationException("Invalid sensor name");
        }
    }
}
