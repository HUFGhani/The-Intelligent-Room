package com.project.smarthome.smarthome.Code.MessageQueues.Subscribers;


import android.util.Log;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

public abstract class SubscriberBase {

    protected MqttClient client;
    protected String topic;
    protected String tag;

    public void subscribe(){
        try {
            client.connect();
            client.subscribe(topic);
            Log.i("Subscribing to", topic);
        }catch (Exception e){
            Log.e(tag, "subscribe: ", e);
        }
    }

    public void unsubscribe() {
        try {
            if (client.isConnected()) {
                Log.i("Unsubscribing to", topic);
                client.unsubscribe(topic);
                client.disconnect();
            }
        } catch (MqttException e) {
            Log.e(tag, "unsubscribe: ", e);
        }
    }

}
