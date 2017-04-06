package com.nickr.IoT.MQTT;

import com.google.gson.Gson;
import com.nickr.IoT.user.model.Hue;
import com.nickr.IoT.userDAO.projectDAO;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;



public class HueMQTTStatus implements MqttCallback {

    String topic = "houseID123/actuator/hue/status";
    int qos             = 2;
    String broker       = "tcp://localhost:1883";
    String clientId     = "hue";
    MemoryPersistence persistence = new MemoryPersistence();
    Gson gson = new Gson();
    com.nickr.IoT.userDAO.projectDAO projectDAO = new projectDAO();


    public HueMQTTStatus() {
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
        Hue hue = gson.fromJson(jsonData, Hue.class);
        projectDAO.InsertPhilipsHue(hue);

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}

