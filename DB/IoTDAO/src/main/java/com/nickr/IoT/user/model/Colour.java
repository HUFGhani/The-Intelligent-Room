package com.nickr.IoT.user.model;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Colour implements Serializable
{

    @SerializedName("red")
    @Expose
    private String red;
    @SerializedName("green")
    @Expose
    private String green;
    @SerializedName("blue")
    @Expose
    private String blue;
    private final static long serialVersionUID = 5964208011969454554L;

    public Colour(String red, String green, String blue) {
		super();
		this.red = red;
		this.green = green;
		this.blue = blue;
	}

	public String getRed() {
        return red;
    }

    public void setRed(String red) {
        this.red = red;
    }

    public String getGreen() {
        return green;
    }

    public void setGreen(String green) {
        this.green = green;
    }

    public String getBlue() {
        return blue;
    }

    public void setBlue(String blue) {
        this.blue = blue;
    }

}
