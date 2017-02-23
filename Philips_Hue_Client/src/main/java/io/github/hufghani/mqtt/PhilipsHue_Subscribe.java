package io.github.hufghani.mqtt;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.json.hue.JSONObject;

/**
 * Created by hamzaghani on 16/02/2017.
 */

/*
TODO need to talk about which topic we would like to use
 */
public class PhilipsHue_Subscribe implements MqttCallback {
    String topic;
    int qos             = 2;
    String broker       = "tcp://localhost:1883";
    String clientId     = "hue";
    MemoryPersistence persistence = new MemoryPersistence();

    private int red,green,blue, brighness ,saturation;
    private Boolean onOff;

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    public int getBrighness() {
        return brighness;
    }

    public int getSaturation() {
        return saturation;
    }

    public Boolean getOnOff() {
        return onOff;
    }

    public String getTopic() {
        return topic;
    }

    public PhilipsHue_Subscribe setTopic(String topic) {
        this.topic = topic;
        return this;
    }

    public PhilipsHue_Subscribe() {
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
        JSONObject light = obj.getJSONObject("light");
        String name = light.getString("name");
        onOff = light.getBoolean("on/off");
        JSONObject colour = light.getJSONObject("colour");
        red = colour.getInt("red");
        green = colour.getInt("green");
        blue = colour.getInt("blue");
        brighness = light.getInt("brighness");
        saturation = light.getInt("Saturation");

        System.out.println(name);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}
