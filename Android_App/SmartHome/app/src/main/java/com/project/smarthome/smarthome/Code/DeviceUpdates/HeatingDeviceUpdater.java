package com.project.smarthome.smarthome.Code.DeviceUpdates;


import com.project.smarthome.smarthome.Model.Devices.Heating.HeatingDevice;

public interface HeatingDeviceUpdater {
    void onUserHeatingRequest(HeatingDevice device);
}
