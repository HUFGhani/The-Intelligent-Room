package com.project.smarthome.smarthome.Model;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.project.smarthome.smarthome.Model.Devices.Device;
import com.project.smarthome.smarthome.Model.Devices.DeviceBase;
import com.project.smarthome.smarthome.Model.Devices.Heating.HeatingDevice;
import com.project.smarthome.smarthome.Model.Devices.Lighting.LightingDevice;
import com.project.smarthome.smarthome.Model.Devices.Sensors.SensorDevice;
import com.project.smarthome.smarthome.Model.Preferences.UserPreference;
import com.project.smarthome.smarthome.R;

import java.util.ArrayList;
import java.util.List;

public class ConfigService {

    private Context context;
    private SharedPreferences sharedPreferences;

    public ConfigService(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
    }

    @SuppressLint("ApplySharedPref") // Needs committing immediately so that the home activity can access it when it opens
    public void saveHouseConfiguration(HouseConfiguration houseConfiguration) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.preference_house_configuration), houseConfiguration.toString());
        editor.commit();
    }

    @SuppressLint("ApplySharedPref") // Needs committing immediately so that the home activity can access it when it opens
    public void saveUserPreferences(UserPreference userPreference) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.preference_user_preference), userPreference.toString());
        editor.commit();
    }

    public UserPreference getUserPreference() {
        String jsonUserPref = sharedPreferences.getString(context.getString(R.string.preference_user_preference), null);
        return (jsonUserPref == null) ? null : new Gson().fromJson(jsonUserPref, UserPreference.class);
    }

    public HouseConfiguration getHouseConfiguration() {
        String jsonHouseConfig = sharedPreferences.getString(context.getString(R.string.preference_house_configuration), null);
        return (jsonHouseConfig == null) ? null : new Gson().fromJson(jsonHouseConfig, HouseConfiguration.class);
    }

    public HeatingDevice getHeatingDevice() {
        String jsonHeatingDevice = sharedPreferences.getString(context.getString(R.string.preference_heating_device), null);
        return (jsonHeatingDevice == null) ? null : new Gson().fromJson(jsonHeatingDevice, HeatingDevice.class);
    }

    @SuppressLint("ApplySharedPref") // Needs committing immediately so that the home activity can access it when it opens
    public void saveHeatingDevice(HeatingDevice heatingDevice) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.preference_heating_device), heatingDevice.toString());
        editor.commit();
    }

    public LightingDevice getLightingDevice() {
        String jsonLightingDevice = sharedPreferences.getString(context.getString(R.string.preference_lighting_device), null);
        return (jsonLightingDevice == null) ? null : new Gson().fromJson(jsonLightingDevice, LightingDevice.class);
    }

    @SuppressLint("ApplySharedPref") // Needs committing immediately so that the home activity can access it when it opens
    public void saveLightingDevice(LightingDevice lightingDevice) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.preference_lighting_device), lightingDevice.toString());
        editor.commit();
    }

    public List<Device> getAllDevices() {
        ArrayList<Device> devices = new ArrayList<>();

        HouseConfiguration config = getHouseConfiguration();

        devices.addAll(config.getSensors());

        HeatingDevice heatingDevice = getHeatingDevice();
        if (heatingDevice != null) {
            devices.add(heatingDevice);
        }

        LightingDevice lighting = getLightingDevice();
        if (lighting != null) {
            devices.add(lighting);
        }

        return devices;
    }

    public void saveAllDevices(List<Device> devices) {
        ArrayList<SensorDevice> sensors = new ArrayList<>();

        for (Device device : devices) {
            switch (device.getDeviceType()) {
                case DeviceBase.DEVICE_TYPE_HEATING : saveHeatingDevice((HeatingDevice) device);
                    break;
                case DeviceBase.DEVICE_TYPE_LIGHTING : saveLightingDevice((LightingDevice) device);
                    break;
                case DeviceBase.DEVICE_TYPE_SENSOR : sensors.add((SensorDevice) device);
                    break;
            }
        }

        HouseConfiguration config = getHouseConfiguration();
        config.setSensors(sensors);
        saveHouseConfiguration(config);
    }

    public void saveLastReceivedPostNotificationTime(long notificationTime) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(context.getString(R.string.preference_last_post_recieved), notificationTime);
        editor.apply();
    }

    public long getLastReceivedPostNotificationTime() {
        return sharedPreferences.getLong(context.getString(R.string.preference_last_post_recieved), 0);
    }

    public void saveInHouseStatus(boolean inHouse) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getString(R.string.preference_in_house), inHouse);
        editor.apply();
    }

    public boolean getInHouseStatus() {
        return sharedPreferences.getBoolean(context.getString(R.string.preference_in_house), false);
    }

    public void deleteAllConfig() {
        sharedPreferences.edit().clear().apply();
    }
}
