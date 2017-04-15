package io.github.hufghani;

import org.eclipse.paho.client.mqttv3.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by hamzaghani on 15/04/2017.
 */
public class NestMQTTTest implements MqttCallback{
    private String topic;
    private int qos             = 2;
    private String broker       = "tcp://localhost:1883";
    MqttClient clientMQTT1;
    MqttClient clientMQTT2;
    private String clientId1     = "nest_TEST";
    private String clientId2     = "nest_status_Test";

    private volatile boolean expectConnectionFailure;

    String json = "{ \"target_temperature_c\":30.0,\"automated\":true}";
    String jsonData;

    @Test
    public void publish() throws MqttException, IOException {
        clientMQTT1 = new MqttClient(broker, clientId1, null);
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true);
        expectConnectionFailure = false;
        clientMQTT1.connect(connOpts);
        MqttMessage message = new MqttMessage(json.getBytes());
        message.setQos(qos);
        clientMQTT1.publish("test/actuator/nest/status",message);
        clientMQTT1.disconnect();

        clientMQTT2 = new MqttClient(broker, clientId2, null);
        MqttConnectOptions connOpts1 = new MqttConnectOptions();
        clientMQTT2.setCallback(this);
        clientMQTT2.connect(connOpts1);
        clientMQTT2.subscribe("test/actuator/nest/status");

        clientMQTT2.disconnect();

        Assert.assertEquals(json,json);
    }


    @Override
    public void connectionLost(Throwable throwable) {

    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        jsonData = new String(mqttMessage.getPayload());

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}
