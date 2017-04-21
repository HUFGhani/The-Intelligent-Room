package com.project.smarthome.smarthome.Presenters.HomeManagement.Preferences;


import android.graphics.Color;

import com.project.smarthome.smarthome.Code.MessageQueues.CustomMqttClient;
import com.project.smarthome.smarthome.Model.ConfigService;
import com.project.smarthome.smarthome.Model.Devices.Lighting.CustomLightColour;
import com.project.smarthome.smarthome.Model.Devices.Lighting.LightingDevice;
import com.project.smarthome.smarthome.Model.Preferences.Lighting.LightingPreference;
import com.project.smarthome.smarthome.Model.Preferences.UserPreference;
import com.project.smarthome.smarthome.Views.HomeManagement.Preferences.PreferencesView;

public class PreferencesPresenter {

    private PreferencesView view;
    private ConfigService configService;
    private UserPreference userPreference;

    public PreferencesPresenter(PreferencesView view, ConfigService configService) {
        this.view = view;
        this.configService = configService;
    }

    public void onStart() {
        view.setTitle();
        userPreference = configService.getUserPreference();

        if (userPreference.heatingPreference.getTargetTemp() == 0) {
            userPreference.heatingPreference.setTargetTemp(18);
        }
        view.setTemperature(userPreference.heatingPreference.getTargetTemp());

        if (userPreference.lightingPreference.getColour() == null) {
            userPreference.lightingPreference.setColour(new CustomLightColour(33, 150, 243));
        }
        CustomLightColour c = userPreference.lightingPreference.getColour();
        int colour = Color.rgb(c.getRed(), c.getGreen(), c.getBlue());
        view.setLightColour(colour);

        if (userPreference.lightingPreference.getAutomationType() == null) {
            onLightingAutomationTypeChanged(0);
        }
        view.setLightingAutomationType(userPreference.lightingPreference.getAutomationType());

        if (userPreference.heatingPreference.getAutomationType() == null) {
            onHeatingAutomationTypeChanged(0);
        }
        view.setHeatingAutomationType(userPreference.lightingPreference.getAutomationType());
    }

    public void onHeatingAutomationTypeChanged(int spinnerPosition) {
        userPreference.heatingPreference.setAutomationType(getAutomationTypeString(spinnerPosition));
    }

    public void onLightingAutomationTypeChanged(int spinnerPosition) {
        userPreference.lightingPreference.setAutomationType(getAutomationTypeString(spinnerPosition));
    }

    public void onUserPressedSave() {
        configService.saveUserPreferences(userPreference);
        publishUserPreferences(userPreference);
        rePublishToInHouse();
        view.showSuccessMessage();
        view.returnToAllDevicesFragment();
    }

    public void onUserChoseNewTemperature(int temperature) {
        userPreference.heatingPreference.setTargetTemp(temperature);
        view.setTemperature(temperature);
    }

    public void onUserChoseNewLighting(LightingDevice light) {
        userPreference.lightingPreference.setSaturation(light.getSaturation());
        userPreference.lightingPreference.setBrightness(light.getBrightness());
        userPreference.lightingPreference.setColour(light.getColourAsCustomColour());
        view.setLightColour(light.getColour());
    }

    public void chooseNewColourSettings() {
        LightingPreference lightPref = userPreference.lightingPreference;
        CustomLightColour colour = (lightPref.getColour() == null) ? new CustomLightColour(33, 150, 243) : lightPref.getColour();
        LightingDevice light = LightingDevice.getDefault();
        light.setColour(colour);
        light.setBrightness(lightPref.getBrightness());
        light.setSaturation(lightPref.getSaturation());
        view.showColourPickerDialog(light);
    }

    private void publishUserPreferences(UserPreference userPreference) {
        String houseId = configService.getHouseConfiguration().getHouseId();
        int userId = userPreference.getUserId();
        String topic = String.format("%1$s/%2$s/preference", houseId, userId);
        CustomMqttClient client = new CustomMqttClient(topic);
        client.sendMessage(userPreference.toString());
    }

    private static String getAutomationTypeString(int pos) {
        switch (pos) {
            case 0 : return "location";
            case 1 : return "motion";
            case 2 : return "light";
            default : throw new UnsupportedOperationException("Position - " + pos);
        }
    }

    private void rePublishToInHouse() {
        String inHouse = String.valueOf(configService.getInHouseStatus());
        String topic = String.format("%1$s/%2$s/inHouse", configService.getHouseConfiguration().getHouseId(), userPreference.getUserId());
        CustomMqttClient client = new CustomMqttClient(topic);
        client.sendMessage(inHouse);
    }
}
