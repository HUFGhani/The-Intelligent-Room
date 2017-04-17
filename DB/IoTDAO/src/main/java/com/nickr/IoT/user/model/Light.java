
package com.nickr.IoT.user.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Light {

    public Light(String name, Colour colour, int saturation, int brightness) {
        this.name = name;
        this.colour = colour;
        this.saturation = saturation;
        this.brightness = brightness;
    }

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("colour")
    @Expose
    private Colour colour;
    @SerializedName("Saturation")
    @Expose
    private int saturation;
    @SerializedName("brightness")
    @Expose
    private int brightness;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Colour getColour() {
        return colour;
    }

    public void setColour(Colour colour) {
        this.colour = colour;
    }

    public int getSaturation() {
        return saturation;
    }

    public void setSaturation(int saturation) {
        this.saturation = saturation;
    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    @Override
    public String toString() {
        return "Light{" +
                "name='" + name + '\'' +
                ", colour=" + colour +
                ", saturation=" + saturation +
                ", brightness=" + brightness +
                '}';
    }
}
