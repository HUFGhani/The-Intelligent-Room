package com.nickr.IoT.MQTT;

import com.google.gson.Gson;
import com.nickr.IoT.user.model.UserPreference;
import com.nickr.IoT.userDAO.projectDAO;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * Created by hamzaghani on 05/04/2017.
 */
public class PreferenceMQTT implements MqttCallback {

    String topic = "houseID/+/preference";
    int qos             = 2;
    String broker       = "tcp://localhost:1883";
    String clientId     = "PreferenceMQTT";
    MemoryPersistence persistence = new MemoryPersistence();
    Gson gson = new Gson();
    com.nickr.IoT.userDAO.projectDAO projectDAO = new projectDAO();


    public PreferenceMQTT() {
        super();
    }

    public void subscribe() {

        try {
            MqttClient client = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            client.setCallback(this);
            client.connect(connOpts);
            client.subscribe(topic);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void connectionLost(Throwable throwable) {

    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        String jsonData = new String(mqttMessage.getPayload());
        System.out.println(jsonData);
        UserPreference userPreference = gson.fromJson(jsonData, UserPreference.class);
        projectDAO.InsertUserPreference(userPreference);
        }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}
