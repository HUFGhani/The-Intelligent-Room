package com.project.smarthome.smarthome.Code.DeviceUpdates;


import com.project.smarthome.smarthome.Model.Devices.Sensors.SensorDevice;

public interface SensorUpdateListener {
    void onSensorUpdated(SensorDevice sensor);
}
