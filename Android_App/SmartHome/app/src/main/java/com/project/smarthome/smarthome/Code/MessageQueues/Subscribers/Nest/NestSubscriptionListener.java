package com.project.smarthome.smarthome.Code.MessageQueues.Subscribers.Nest;


import com.project.smarthome.smarthome.Model.Devices.Heating.HeatingDevice;

public interface NestSubscriptionListener {
    void onNestMessageReceived(HeatingDevice nest);
}
