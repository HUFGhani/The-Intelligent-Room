
package com.nickr.IoT.user.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Colour implements Serializable {

    @SerializedName("red")
    @Expose
    private Integer red;
    @SerializedName("green")
    @Expose
    private Integer green;
    @SerializedName("blue")
    @Expose
    private Integer blue;

    public Colour(Integer red, Integer green, Integer blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public Integer getRed() {
        return red;
    }

    public void setRed(Integer red) {
        this.red = red;
    }

    public Integer getGreen() {
        return green;
    }

    public void setGreen(Integer green) {
        this.green = green;
    }

    public Integer getBlue() {
        return blue;
    }

    public void setBlue(Integer blue) {
        this.blue = blue;
    }

}
