package com.nickr.IoT;

import com.nickr.IoT.MQTT.*;

/**
 * Created by hamzaghani on 05/04/2017.
 */
public class App {

    public static void main(String[] args) {
        new App();
    }

    public App() {

        HueMQTT hueMQTT = new HueMQTT();
//        HueMQTTStatus hueMQTTStatus =  new HueMQTTStatus();
//        NestMQTT nestMQTT = new NestMQTT();
//        NestMQTTStatus nestMQTTStatus = new NestMQTTStatus();
//        SensorMQTT sensorMQTT = new SensorMQTT();
//        HouseMQTT houseMQTT = new HouseMQTT();
//        PreferenceMQTT preferenceMQTT = new PreferenceMQTT();
//        ConfigurationMQTT configurationMQTT = new ConfigurationMQTT();

        for (;;) {
            try {
                hueMQTT.subscribe();
//                hueMQTTStatus.subscribe();
//                nestMQTT.subscribe();
//                nestMQTTStatus.subscribe();
//                sensorMQTT.subscribe();
//                houseMQTT.subscribe();
//                preferenceMQTT.subscribe();
//                configurationMQTT.subscribe();

                Thread.sleep(1000);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
