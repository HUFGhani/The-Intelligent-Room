package com.project.smarthome.smarthome.Model.Devices.Lighting;

import android.graphics.Color;

import com.google.gson.annotations.SerializedName;
import com.project.smarthome.smarthome.Model.Devices.Device;
import com.project.smarthome.smarthome.Model.Devices.DeviceBase;

public class LightingDevice extends DeviceBase implements Device {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("on/off")
    private boolean active;

    @SerializedName("colour")
    private CustomLightColour colour;

    @SerializedName("brightness")
    private int brightness;

    @SerializedName("saturation")
    private int saturation;

    @SerializedName("automated")
    private boolean automated;

    public LightingDevice(int id, String name, boolean active, int colour, int brightness, int saturation) {
        this.id = id;
        this.name = name;
        this.active = active;
        this.colour = new CustomLightColour(Color.red(colour), Color.green(colour), Color.blue(colour));
        this.brightness = brightness;
        this.saturation = saturation;
        this.automated = false;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getDeviceType() {
        return DeviceBase.DEVICE_TYPE_LIGHTING;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        if (brightness < 0 || brightness > 255) {
            throw new IllegalArgumentException("Colour must be in range 0-255");
        }
        this.brightness = brightness;
    }

    public CustomLightColour getColourAsCustomColour() {
        return this.colour;
    }

    public int getColour() {
        return android.graphics.Color.rgb(this.colour.getRed(), this.colour.getGreen(), this.colour.getBlue());
    }

    public void setColour(int colour) {
        this.colour = new CustomLightColour(Color.red(colour), Color.green(colour), Color.blue(colour));;
    }

    public void setColour(CustomLightColour colour) {
        this.colour = colour;
    }

    public int getSaturation() {
        return saturation;
    }

    public void setSaturation(int saturation) {
        this.saturation = saturation;
    }

    public boolean isAutomated() {
        return automated;
    }

    public void setAutomated(boolean automated) {
        this.automated = automated;
    }

    public static LightingDevice getDefault() {
        return new LightingDevice(0, "Hue color lamp 1", false, 0xFF2196F3, 50, 100);
    }
}
