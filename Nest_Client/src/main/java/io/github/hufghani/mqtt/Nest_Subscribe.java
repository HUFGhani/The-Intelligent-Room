package io.github.hufghani.mqtt;

import org.codehaus.jettison.json.JSONObject;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;


/**
 * Created by hamzaghani on 13/02/2017.
 */

/*
TODO add the config file here
 */
public class Nest_Subscribe implements MqttCallback{
    String topic        = "mqtt Examples";
    int qos             = 2;
    String broker       = "tcp://localhost:1883";
    String clientId     = "nest";
    MemoryPersistence persistence = new MemoryPersistence();

    private double temperature;

    public double getTemperature() {
        return temperature;
    }

    public Nest_Subscribe() {
    super();
    }

    public void subscribe(){
        try {
            MqttClient client = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions conn = new MqttConnectOptions();
            conn.setCleanSession(true);
            client.connect(conn);
            client.subscribe(topic,qos);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void connectionLost(Throwable throwable) {
        System.out.println("connection has be lost");
    }

    /*
    TODO add JSON parser here when payload arrives and pass it ot the set temperature method.
     */

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        String jsonData = new String(mqttMessage.getPayload());
        JSONObject obj = new JSONObject(jsonData);
        temperature = obj.getDouble("target_temperature_c");
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}
