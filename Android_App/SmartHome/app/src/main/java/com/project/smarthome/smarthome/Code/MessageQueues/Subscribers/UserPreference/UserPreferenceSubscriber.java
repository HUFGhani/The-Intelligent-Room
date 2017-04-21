package com.project.smarthome.smarthome.Code.MessageQueues.Subscribers.UserPreference;


import android.util.Log;

import com.google.gson.Gson;
import com.project.smarthome.smarthome.Code.MessageQueues.MqttClientProvider;
import com.project.smarthome.smarthome.Code.MessageQueues.Subscribers.Subscriber;
import com.project.smarthome.smarthome.Code.MessageQueues.Subscribers.SubscriberBase;
import com.project.smarthome.smarthome.Model.Preferences.UserPreference;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class UserPreferenceSubscriber extends SubscriberBase implements MqttCallback, Subscriber {

    private static final String TAG = UserPreferenceSubscriber.class.getSimpleName();

    private UserPreferenceSubscriptionListener listener;

    public UserPreferenceSubscriber(UserPreferenceSubscriptionListener listener, String homeId, int userId) {
        this.listener = listener;
        this.topic = String.valueOf(homeId) + "/" + String.valueOf(userId) + "/preference";
        this.tag = UserPreferenceSubscriber.class.getSimpleName();

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
        UserPreference userPreference = new Gson().fromJson(json, UserPreference.class);
        listener.onUserPreferenceMessageReceived(userPreference);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
    }
}
