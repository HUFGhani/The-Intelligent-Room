package com.project.smarthome.smarthome.Code.MessageQueues.Subscribers.Hue;


import android.util.Log;

import com.google.gson.Gson;
import com.project.smarthome.smarthome.Code.MessageQueues.MqttClientProvider;
import com.project.smarthome.smarthome.Code.MessageQueues.Subscribers.Subscriber;
import com.project.smarthome.smarthome.Code.MessageQueues.Subscribers.SubscriberBase;
import com.project.smarthome.smarthome.Model.Devices.Lighting.PhilipsHue;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class HueSubscriber extends SubscriberBase implements MqttCallback, Subscriber {

    private static final String TAG = HueSubscriber.class.getSimpleName();

    private HueSubscriptionListener listener;

    public HueSubscriber(HueSubscriptionListener listener, String homeId) {
        this.listener = listener;
        this.topic = homeId + "/actuator/hue/status";
        this.tag = HueSubscriber.class.getSimpleName();

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
        PhilipsHue hue = new Gson().fromJson(json, PhilipsHue.class);
        listener.onHueMessageReceived(hue.getLight());
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
    }
}
