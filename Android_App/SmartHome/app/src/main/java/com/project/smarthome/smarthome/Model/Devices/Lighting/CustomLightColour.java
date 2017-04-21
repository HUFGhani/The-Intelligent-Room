package com.project.smarthome.smarthome.Model.Devices.Lighting;


import com.google.gson.annotations.SerializedName;

public class CustomLightColour {

    @SerializedName("red")
    private int red;

    @SerializedName("green")
    private int green;

    @SerializedName("blue")
    private int blue;

    public CustomLightColour(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }
}
