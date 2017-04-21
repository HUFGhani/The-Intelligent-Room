package com.project.smarthome.smarthome.Code.MessageQueues;


import android.util.Log;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class CustomMqttClient {

    private static final String TAG = CustomMqttClient.class.getSimpleName();

    private String topic;

    public CustomMqttClient(String topic) {
        this.topic = topic;
    }

    public void sendMessage(String content) {
        try {
            MqttClient client = new MqttClientProvider().getDefaultClient();
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            client.connect(connOpts);
            Log.i(TAG, "Connected to topic: " + topic);

            Log.i(TAG, "Publishing message: " + content);
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setRetained(true);
            message.setQos(2);
            client.publish(topic, message);
            Log.i(TAG, "Message published");

            client.disconnect();
            Log.i(TAG, "Disconnected");
        } catch(MqttException me) {
            Log.e(TAG, "Error", me);
        }
    }

}
