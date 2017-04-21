package com.project.smarthome.smarthome.Code.MessageQueues.Subscribers.Nest;


import android.util.Log;

import com.google.gson.Gson;
import com.project.smarthome.smarthome.Code.MessageQueues.MqttClientProvider;
import com.project.smarthome.smarthome.Code.MessageQueues.Subscribers.Subscriber;
import com.project.smarthome.smarthome.Code.MessageQueues.Subscribers.SubscriberBase;
import com.project.smarthome.smarthome.Model.Devices.Heating.HeatingDevice;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class NestSubscriber extends SubscriberBase implements MqttCallback, Subscriber {

    private static final String TAG = NestSubscriber.class.getSimpleName();

    private NestSubscriptionListener listener;

    public NestSubscriber(NestSubscriptionListener listener, String homeId) {
        this.listener = listener;
        this.topic = String.valueOf(homeId) + "/actuator/nest/status";
        this.tag = NestSubscriber.class.getSimpleName();

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
        HeatingDevice device = new Gson().fromJson(json, HeatingDevice.class);
        listener.onNestMessageReceived(device);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
    }
}
