package com.project.smarthome.smarthome.Code.MessageQueues.Subscribers.Sensors;


import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.project.smarthome.smarthome.Code.MessageQueues.MqttClientProvider;
import com.project.smarthome.smarthome.Code.MessageQueues.Subscribers.Subscriber;
import com.project.smarthome.smarthome.Code.MessageQueues.Subscribers.SubscriberBase;
import com.project.smarthome.smarthome.Model.Devices.Sensors.SensorDevice;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SensorSubscriber extends SubscriberBase implements MqttCallback, Subscriber {

    private static final String TAG = SensorSubscriber.class.getSimpleName();

    private SensorSubscriptionListener listener;

    public SensorSubscriber(SensorSubscriptionListener listener, String homeId, int sensorId) {
        this.listener = listener;
        this.topic = String.valueOf(homeId) + "/sensor/" + String.valueOf(sensorId);
        this.tag = SensorSubscriber.class.getSimpleName();

        client = new MqttClientProvider().getDefaultClient();
        client.setCallback(this);
    }

    @Override
    public void connectionLost(Throwable cause) {
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        Log.i(TAG, "messageArrived : topic = " + topic);
        String json = new String(message.getPayload());

        Type listType = new TypeToken<List<SensorDevice>>() {}.getType();

        ArrayList<SensorDevice> sensors = new Gson().fromJson(json, listType);

        SensorDevice sensor = sensors.get(0);
        listener.onSensorMessageReceived(sensor);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
    }
}
