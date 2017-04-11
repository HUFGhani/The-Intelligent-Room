package io.github.hufghani.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * Created by hamzaghani on 16/02/2017.
 */

/*
TODO need to talk about which topic we would like to use
 */
public class PhilipsHue_Publish {
    private String topic;
    private int qos             = 2;
    private String broker       = "tcp://localhost:1883";
    private String clientId     = "hue_status";
    private MemoryPersistence persistence = new MemoryPersistence();

    public PhilipsHue_Publish() {
        super();
    }


    public String getTopic() {
        return topic;
    }

    public PhilipsHue_Publish setTopic(String topic) {
        this.topic = topic;
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
            message.setRetained(true);
            client.publish(getTopic()+"/actuator/hue/status",message);
            System.out.println("publish"+getTopic()+"/actuator/hue");
            System.out.println("publish" + message);
//            client.disconnect();
        }catch (MqttException e){
            e.printStackTrace();
        }
    }
}
