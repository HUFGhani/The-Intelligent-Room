package com.project.smarthome.smarthome.Code.MessageQueues.Subscribers.HomeConfiguration;


import android.util.Log;

import com.google.gson.Gson;
import com.project.smarthome.smarthome.Code.MessageQueues.MqttClientProvider;
import com.project.smarthome.smarthome.Code.MessageQueues.Subscribers.Subscriber;
import com.project.smarthome.smarthome.Code.MessageQueues.Subscribers.SubscriberBase;
import com.project.smarthome.smarthome.Model.HouseConfiguration;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class HouseConfigurationSubscriber extends SubscriberBase implements MqttCallback, Subscriber {

    private static final String TAG = HouseConfigurationSubscriber.class.getSimpleName();

    private HouseConfigurationSubscriptionListener listener;

    public HouseConfigurationSubscriber(HouseConfigurationSubscriptionListener listener, String homeId) {
        this.listener = listener;
        this.topic = String.valueOf(homeId) + "/configuration";
        this.tag = HouseConfigurationSubscriber.class.getSimpleName();

        client = new MqttClientProvider().getDefaultClient();
        client.setCallback(this);
    }

    @Override
    public void connectionLost(Throwable cause) {
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        Log.i(TAG, "messageArrived");
        String json = new String(message.getPayload());
        HouseConfiguration houseConfiguration = new Gson().fromJson(json, HouseConfiguration.class);
        listener.onHouseConfigurationMessageReceived(houseConfiguration);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
    }
}
