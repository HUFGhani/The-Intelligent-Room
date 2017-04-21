package com.project.smarthome.smarthome.Code.MessageQueues.Subscribers.HomeConfiguration;


import com.project.smarthome.smarthome.Model.HouseConfiguration;

public interface HouseConfigurationSubscriptionListener {
    void onHouseConfigurationMessageReceived(HouseConfiguration houseConfiguration);
}
