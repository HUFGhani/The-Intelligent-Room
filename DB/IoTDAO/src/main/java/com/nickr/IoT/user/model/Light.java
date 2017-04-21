
package com.nickr.IoT.user.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Light implements Serializable {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("on/off")
    @Expose
    private Boolean onOff;
    @SerializedName("colour")
    @Expose
    private Colour colour;
    @SerializedName("brightness")
    @Expose
    private Integer brightness;
    @SerializedName("saturation")
    @Expose
    private Integer saturation;
    @SerializedName("automated")
    @Expose
    private Boolean automated;

    public Light(String name, Colour colour, Integer brightness, Integer saturation) {
        this.name = name;
        this.onOff = onOff;
        this.colour = colour;
        this.brightness = brightness;
        this.saturation = saturation;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getOnOff() {
        return onOff;
    }

    public void setOnOff(Boolean onOff) {
        this.onOff = onOff;
    }

    public Colour getColour() {
        return colour;
    }

    public void setColour(Colour colour) {
        this.colour = colour;
    }

    public Integer getBrightness() {
        return brightness;
    }

    public void setBrightness(Integer brightness) {
        this.brightness = brightness;
    }

    public Integer getSaturation() {
        return saturation;
    }

    public void setSaturation(Integer saturation) {
        this.saturation = saturation;
    }

    public Boolean getAutomated() {
        return automated;
    }

    public void setAutomated(Boolean automated) {
        this.automated = automated;
    }


}
