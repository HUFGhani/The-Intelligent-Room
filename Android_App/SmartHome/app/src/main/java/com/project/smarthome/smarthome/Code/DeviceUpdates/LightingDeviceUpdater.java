package com.project.smarthome.smarthome.Code.DeviceUpdates;


import com.project.smarthome.smarthome.Model.Devices.Lighting.LightingDevice;

public interface LightingDeviceUpdater {
    void onUserLightingRequest(LightingDevice device);
}
