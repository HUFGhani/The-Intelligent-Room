package com.project.smarthome.smarthome.Model.Devices;


import com.google.gson.Gson;

public class DeviceBase {

    public static final int DEVICE_TYPE_UNKNOWN = 0;
    public static final int DEVICE_TYPE_LIGHTING = 1;
    public static final int DEVICE_TYPE_HEATING = 2;
    public static final int DEVICE_TYPE_SENSOR = 3;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
