
package com.nickr.IoT.user.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Colour {

    public Colour (int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    @SerializedName("red")
    @Expose
    private int red;
    @SerializedName("green")
    @Expose
    private int green;
    @SerializedName("blue")
    @Expose
    private int blue;

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
