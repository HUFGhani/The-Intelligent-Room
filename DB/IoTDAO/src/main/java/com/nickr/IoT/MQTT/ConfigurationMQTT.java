package com.nickr.IoT.MQTT;

import com.google.gson.Gson;
import com.nickr.IoT.user.model.HouseConfiguration;
import com.nickr.IoT.userDAO.projectDAO;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;


public class ConfigurationMQTT implements MqttCallback {

    String topic = "houseID123/+/inHouse";
    int qos             = 2;
    String broker       = "tcp://localhost:1883";
    String clientId     = "configMQTT";
    MemoryPersistence persistence = new MemoryPersistence();
    Gson gson = new Gson();
    projectDAO projectDAO = new projectDAO();


    public ConfigurationMQTT() {
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
        HouseConfiguration houseConfiguration = gson.fromJson(jsonData, HouseConfiguration.class);
        projectDAO.insertHouseConfiguration(houseConfiguration);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}
