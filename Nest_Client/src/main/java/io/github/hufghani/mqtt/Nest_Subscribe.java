package io.github.hufghani.mqtt;

import org.codehaus.jettison.json.JSONObject;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;


/**
 * @author Hamza Ghani on 13/02/2017.
 * 
 */


public class Nest_Subscribe implements MqttCallback{

    String topic;
    int qos             = 2;
    String broker       = "tcp://localhost:1883";
    String clientId     = "nest";
    MemoryPersistence persistence = new MemoryPersistence();

    private double temperature;


    public Nest_Subscribe() {
    super();
    }

    public void subscribe(){
        try {
            MqttClient client = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            client.setCallback(this);
            client.connect(connOpts);
            client.subscribe(getTopic());

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void connectionLost(Throwable throwable) {

    }

    /*
    TODO add JSON parser here when payload arrives and pass it ot the set temperature method.
     */

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        String jsonData = new String(mqttMessage.getPayload());
        JSONObject obj = new JSONObject(jsonData);
        setTemperature(obj.getDouble("target_temperature_c"));
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }

    public double getTemperature() {
        return temperature;
    }

    public Nest_Subscribe setTemperature(double temperature) {
        this.temperature = temperature;
        return this;
    }

    public String getTopic() {
        return topic;
    }

    public Nest_Subscribe setTopic(String topic) {
        this.topic = topic;
        return this;
    }
}
