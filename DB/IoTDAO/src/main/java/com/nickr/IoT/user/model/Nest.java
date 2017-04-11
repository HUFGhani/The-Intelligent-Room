package com.nickr.IoT.user.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Nest{
    @SerializedName("target_temperature_c")
    @Expose
    private Integer targetTemperatureC;
    @SerializedName("automated")
    @Expose
    private Boolean automated;

    public Integer getTargetTemperatureC() {
        return targetTemperatureC;
    }

    public void setTargetTemperatureC(Integer targetTemperatureC) {
        this.targetTemperatureC = targetTemperatureC;
    }

    public Boolean getAutomated() {
        return automated;
    }

    public void setAutomated(Boolean automated) {
        this.automated = automated;
    }

}