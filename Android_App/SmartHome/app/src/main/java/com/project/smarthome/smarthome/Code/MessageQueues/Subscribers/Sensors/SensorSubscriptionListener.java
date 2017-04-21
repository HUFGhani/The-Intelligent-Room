package com.project.smarthome.smarthome.Code.MessageQueues.Subscribers.Sensors;


import com.project.smarthome.smarthome.Model.Devices.Sensors.SensorDevice;

public interface SensorSubscriptionListener {
    void onSensorMessageReceived(SensorDevice sensor);
}
