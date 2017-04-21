package com.project.smarthome.smarthome.Views.HomeManagement.Preferences;


import com.project.smarthome.smarthome.Model.Devices.Lighting.LightingDevice;

public interface PreferencesView {
    void setTitle();
    void showSuccessMessage();
    void returnToAllDevicesFragment();
    void setTemperature(int temperature);
    void setLightColour(int colour);
    void showColourPickerDialog(LightingDevice light);
    void setHeatingAutomationType(String type);
    void setLightingAutomationType(String type);
}
