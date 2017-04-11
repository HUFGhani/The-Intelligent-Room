package com.nickr.IoT;

import com.google.gson.Gson;
import com.nickr.IoT.MQTT.*;
import com.nickr.IoT.user.model.*;
import com.nickr.IoT.userDAO.projectDAO;
import com.sun.scenario.effect.light.*;

public class App {
    Gson gson = new Gson();
    com.nickr.IoT.userDAO.projectDAO projectDAO = new projectDAO();
    public static void main(String[] args) {
        new App();
    }

    public App() {

       // HueMQTT hueMQTT = new HueMQTT();
//        HueMQTTStatus hueMQTTStatus =  new HueMQTTStatus();
     // NestMQTT nestMQTT = new NestMQTT();
//        NestMQTTStatus nestMQTTStatus = new NestMQTTStatus();
//        SensorMQTT sensorMQTT = new SensorMQTT();
//        HouseMQTT houseMQTT = new HouseMQTT();
//        PreferenceMQTT preferenceMQTT = new PreferenceMQTT();
//        ConfigurationMQTT configurationMQTT = new ConfigurationMQTT();

//        for (;;) {
//            try {
////                hueMQTT.subscribe();
////                hueMQTTStatus.subscribe();
//                nestMQTT.subscribe();
////                nestMQTTStatus.subscribe();
////                sensorMQTT.subscribe();
////                houseMQTT.subscribe();
////                preferenceMQTT.subscribe();
////                configurationMQTT.subscribe();
//
//                Thread.sleep(1000);
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }


// TEST STATEMENTS

//       String jsonData = "{\"light\":{\"name\":test2,\"on/off\":true,\"colour\":{\"red\":5,\"green\":255,\"blue\":6},\"brightness\":255,\"Saturation\":255,\"automated\":false}}";
//       Hue Hue = gson.fromJson(jsonData, Hue.class);
//       System.out.println(Hue.getLight().getName() + " " + Hue.getLight().isOnOff() + " " + Hue.getLight().getColour().getRed() + " " +
//                Hue.getLight().getColour().getGreen() + " " + Hue.getLight().getColour().getBlue() + " " +
//                Hue.getLight().getBrightness() + " " + Hue.getLight().getSaturation() + " " + Hue.getLight().isAutomaticStatus());
//       projectDAO.InsertPhilipsHue(Hue);


//        String jsonData = "{\"target_temperature_c\":20, \"automated\":false}";
//        Nest nest = gson.fromJson(jsonData, Nest.class);
//        System.out.println(nest.getTargetTemperatureC() + " " + nest.getAutomated());
//        projectDAO.InsertNest(nest);

        String jsonData = "{\"sensorName\":motionSensor,\"sensorId\":1,\"sensorMethodType\":onChanged,\"updateTimestamp\":2017-04-10 12:52:18,\"sensorPort\":0,\"sensorValue\":644}";
        Sensor sensor = gson.fromJson(jsonData, Sensor.class);
        System.out.println(sensor.getSensorMethodType());
        projectDAO.insertSensors(sensor);

//          String jsonData = "{\"colour\":{\"red\":255,\"green\":255,\"blue\":230},\"saturation\":3,\"brightness\":10,\"actionMethod\":location,\"actionPriority\":3}";
//          LightPref Lpref = gson.fromJson(jsonData, LightPref.class);
//          System.out.println(Lpref.getColour().getRed() + " " + Lpref.getColour().getGreen() + " " + Lpref.getColour().getBlue() + " " + Lpref.getSaturation() + " " + Lpref.getBrightness() + " " + Lpref.getActionMethod() + " " + Lpref.getActionPriority());
//          projectDAO.InsertLightPreference(Lpref);

//          String jsonData = "{\"target_temperature_c\":40,\"actionMethod\":Location,\"actionPriority\":2}";
//          HeatingPreference Hpref = gson.fromJson(jsonData, HeatingPreference.class);
//          System.out.println(Hpref.getTargetTemp() + " " + Hpref.getAutomationType() + " " + Hpref.getActionPriority());
//          projectDAO.InsertHeatPreference(Hpref);
    }
}
