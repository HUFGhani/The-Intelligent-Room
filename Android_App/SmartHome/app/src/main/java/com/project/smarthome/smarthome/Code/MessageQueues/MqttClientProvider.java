package com.project.smarthome.smarthome.Code.MessageQueues;


import android.util.Log;

import com.project.smarthome.smarthome.Utilities.NumberUtilities;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MqttClientProvider {

    private static final String TAG = MqttClientProvider.class.getSimpleName();
    private final MemoryPersistence PERSISTENCE = new MemoryPersistence();
    private static final String MQTT_BROKER = "tcp://ec2-52-56-203-226.eu-west-2.compute.amazonaws.com";
    private static final String MQTT_CLIENT_ID = "SmartHome (Android)";

    public MqttClientProvider() {
    }

    public MqttClient getDefaultClient() {
        try {
            return new MqttClient(MQTT_BROKER, (MQTT_CLIENT_ID + String.valueOf(NumberUtilities.generateNonSecureRandom())), PERSISTENCE);
        } catch (MqttException e) {
            Log.e(TAG, "getDefaultClient: ", e);
        }
        return null;
    }
}
