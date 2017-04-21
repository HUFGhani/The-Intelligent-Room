
package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Nest {
    public Nest(int targetTemperatureC) {
        this.targetTemperatureC = targetTemperatureC;
        this.automaticStatus = automaticStatus;
    }

    @SerializedName("target_temperature_c")
    @Expose
    private int targetTemperatureC;

    @SerializedName("automated")
    @Expose
    private Boolean automaticStatus;


    public Boolean getAutomaticStatus() {
        return automaticStatus;
    }

    public void setAutomaticStatus(Boolean automaticStatus) {
        this.automaticStatus = automaticStatus;
    }

    public int getTargetTemperatureC() {
        return targetTemperatureC;
    }

    public void setTargetTemperatureC(int targetTemperatureC) {
        this.targetTemperatureC = targetTemperatureC;
    }

    @Override
    public String toString() {
        return "Nest{" +
                "targetTemperatureC=" + targetTemperatureC +
                ", automaticStatus=" + automaticStatus +
                '}';
    }
}
