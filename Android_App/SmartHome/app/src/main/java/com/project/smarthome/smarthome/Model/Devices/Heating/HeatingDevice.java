package com.project.smarthome.smarthome.Model.Devices.Heating;


import com.google.gson.annotations.SerializedName;
import com.project.smarthome.smarthome.Model.Devices.Device;
import com.project.smarthome.smarthome.Model.Devices.DeviceBase;

public class HeatingDevice extends DeviceBase implements Device {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("currentTemperature")
    private int currentTemperature;

    @SerializedName("target_temperature_c")
    private int targetTemperature;

    @SerializedName("automated")
    private boolean automated;

    public HeatingDevice(int id, String name, int currentTemperature, int targetTemperature) {
        this.id = id;
        this.name = name;
        this.currentTemperature = currentTemperature;
        this.targetTemperature = targetTemperature;
        this.automated = false;
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
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getDeviceType() {
        return DeviceBase.DEVICE_TYPE_HEATING;
    }

    public int getCurrentTemperature() {
        return currentTemperature;
    }

    public void setCurrentTemperature(int currentTemperature) {
        this.currentTemperature = currentTemperature;
    }

    public int getTargetTemperature() {
        return targetTemperature;
    }

    public void setTargetTemperature(int targetTemperature) {
        this.targetTemperature = targetTemperature;
    }

    public boolean isAutomated() {
        return automated;
    }

    public void setAutomated(boolean automated) {
        this.automated = automated;
    }

    public static HeatingDevice getDefault() {
        return new HeatingDevice(0, "Heating", 18, 21);
    }
}
