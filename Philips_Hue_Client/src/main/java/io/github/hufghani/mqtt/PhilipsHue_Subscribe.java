package io.github.hufghani.mqtt;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.json.JSONObject;


/**
 * Created by hamzaghani on 06/04/2017.
 */
public class PhilipsHue_Subscribe implements MqttCallback {

    private String topic;
    private int qos             = 2;
    private String broker       = "tcp://localhost:1883";
    private String clientId     = "hue";
    MemoryPersistence persistence = new MemoryPersistence();
    int red ,green, blue, saturation, brightness;
    boolean automated = true , onoff = true;

    public PhilipsHue_Subscribe() {
        super();
    }


    public int getRed() {
        return red;
    }

    public PhilipsHue_Subscribe setRed(int red) {
        this.red = red;
        return this;
    }

    public int getGreen() {
        return green;
    }

    public PhilipsHue_Subscribe setGreen(int green) {
        this.green = green;
        return this;
    }

    public int getBlue() {
        return blue;
    }

    public PhilipsHue_Subscribe setBlue(int blue) {
        this.blue = blue;
        return this;
    }

    public int getSaturation() {
        return saturation;
    }

    public PhilipsHue_Subscribe setSaturation(int saturation) {
        this.saturation = saturation;
        return this;
    }

    public int getBrightness() {
        return brightness;
    }

    public PhilipsHue_Subscribe setBrightness(int brightness) {
        this.brightness = brightness;
        return this;
    }

    public boolean isAutomated() {
        return automated;
    }

    public PhilipsHue_Subscribe setAutomated(boolean automated) {
        this.automated = automated;
        return this;
    }

    public boolean isOnoff() {
        return onoff;
    }

    public PhilipsHue_Subscribe setOnoff(boolean onoff) {
        this.onoff = onoff;
        return this;
    }

    public String getTopic() {
        return topic;
    }

    public PhilipsHue_Subscribe setTopic(String topic) {
        this.topic = topic;
        return this;
    }

    public void subscribe() {
        try {
            MqttClient client = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            client.setCallback(this);
            client.connect(connOpts);
            client.subscribe(getTopic()+"/actuator/hue");
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
        JSONObject obj = new JSONObject(jsonData);
        JSONObject light = obj.getJSONObject("light");
        String name = light.getString("name");
        setOnoff(light.getBoolean("on/off"));
        JSONObject colour = light.getJSONObject("colour");
        setRed(colour.getInt("red"));
        setGreen(colour.getInt("green"));
        setBlue(colour.getInt("blue"));
        setBrightness(light.getInt("brightness"));
        setSaturation(light.getInt("Saturation"));
        setAutomated(light.getBoolean("automated"));



    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }


}
