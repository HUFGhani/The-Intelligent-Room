package com.project.smarthome.smarthome.Code.MessageQueues.Subscribers.Hue;


import com.project.smarthome.smarthome.Model.Devices.Lighting.LightingDevice;

public interface HueSubscriptionListener {
    void onHueMessageReceived(LightingDevice hue);
}
