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

//        HueMQTT hueMQTT = new HueMQTT();
//        HueMQTTStatus hueMQTTStatus =  new HueMQTTStatus();
//        NestMQTT nestMQTT = new NestMQTT();
//        NestMQTTStatus nestMQTTStatus = new NestMQTTStatus();
//        SensorMQTT sensorMQTT = new SensorMQTT();
//        HouseMQTT houseMQTT = new HouseMQTT();
//        PreferenceMQTT preferenceMQTT = new PreferenceMQTT();
//        ConfigurationMQTT configurationMQTT = new ConfigurationMQTT();
//
//        for (;;) {
//            try {
//                hueMQTT.subscribe();
//                hueMQTTStatus.subscribe();
//                nestMQTT.subscribe();
//                nestMQTTStatus.subscribe();
//                sensorMQTT.subscribe();
//                houseMQTT.subscribe();
//                preferenceMQTT.subscribe();
//                configurationMQTT.subscribe();
//
//                Thread.sleep(1000);
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }


// TEST STATEMENTS

//       String jsonData = "{\"light\":{\"name\":test4,\"on/off\":true,\"colour\":{\"red\":5,\"green\":255,\"blue\":6},\"brightness\":255,\"saturation\":255,\"automated\":true}}";
//       Hue Hue = gson.fromJson(jsonData, Hue.class);
//       System.out.println(Hue.getLight().getName() + " " + Hue.isOnOff() + " " + Hue.getLight().getColour().getRed() + " " +
//                Hue.getLight().getColour().getGreen() + " " + Hue.getLight().getColour().getBlue() + " " +
//                Hue.getLight().getBrightness() + " " + Hue.getLight().getSaturation() + " " + Hue.getAutomaticStatus());
//       projectDAO.InsertPhilipsHue(Hue);

//        String jsonData = "{\"target_temperature_c\":26, \"automated\":false}";
//        Nest nest = gson.fromJson(jsonData, Nest.class);
//        System.out.println(nest.getTargetTemperatureC() + " " + nest.getAutomaticStatus());
//        projectDAO.InsertNest(nest);

//        String jsonData = "{\"sensorName\":touchSensor,\"sensorId\":1,\"sensorMethodType\":average,\"updateTimestamp\":20171203,\"sensorPort\":1,\"sensorValue\":520}";
//        Sensor sensor = gson.fromJson(jsonData, Sensor.class);
//        System.out.println(sensor.getSensorName() + " " + sensor.getSensorId() + " "  + sensor.getSensorMethodType() + " " + sensor.getUpdateTimestamp() + " " + sensor.getSensorPort() + " " + sensor.getSensorValue());
//        projectDAO.insertSensors(sensor);

//          String jsonData = "{\"userId\":1,\"firstName\":\",\",\"lastName\":\",\",\"priority\":0,\"lightPref\":{\"light\":{\"name\":\"Hue color lamp 1\",\"colour\":{\"red\":0,\"green\":0,\"blue\":150},\"saturation\":200,\"brightness\":200},\"actionMethod\":\"location\",\"actionPriority\":2},\"tmpPref\":{\"nest\":{\"target_temperature_c\":10},\"actionMethod\":\"location\",\"actionPriority\":1}}";
//          UserPreference Hpref = gson.fromJson(jsonData, UserPreference.class);
//          System.out.println(Hpref.getUserId() + " " + Hpref.getFirstName() + " " + Hpref.getLastName() + " " + Hpref.getPriority() + " " +
//                  Hpref.getLightPref().getLight().getName() + " " + Hpref.getLightPref().getLight().getColour().getRed() + " " + Hpref.getLightPref().getLight().getColour().getGreen() + " " + Hpref.getLightPref().getLight().getColour().getBlue()  + " " + Hpref.getLightPref().getLight().getSaturation() + " " + Hpref.getLightPref().getLight().getBrightness()
//                  + " " + Hpref.getLightPref().getActionMethod() + " " + Hpref.getLightPref().getActionPriority() + " " +
//                  Hpref.getTmpPref().getNest().getTargetTemperatureC() + " " + Hpref.getTmpPref().getAutomationType() + " " + Hpref.getTmpPref().getActionPriority());
//          projectDAO.InsertUserPreference(Hpref);

//  }

   }

}
