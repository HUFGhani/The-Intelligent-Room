package io.github.hufghani.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * Created by hamzaghani on 13/02/2017.
 */

/*
TODO add the config file here
 */
public class Nest_Publish {

    String topic        = "mqtt Examples";
    String houseID;
    int qos             = 2;
    String broker       = "tcp://localhost:1883";
    String clientId     = "nest";
    MemoryPersistence persistence = new MemoryPersistence();

    public Nest_Publish() {
    super();
    }


    public Nest_Publish setHouseID(String houseID) {
        this.houseID = houseID;
        return this;
    }

    public void publish(String jsonPayload){
        try {
            MqttClient client = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions conn = new MqttConnectOptions();
            conn.setCleanSession(true);
            client.connect(conn);
            MqttMessage message = new MqttMessage(jsonPayload.getBytes());
            message.setQos(qos);
            client.publish(topic,message);
            client.disconnect();
        }catch (MqttException e){
            e.printStackTrace();
        }
    }
}
