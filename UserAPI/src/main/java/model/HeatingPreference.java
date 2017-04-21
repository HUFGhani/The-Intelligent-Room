package model;

import com.google.gson.annotations.SerializedName;

public class HeatingPreference extends PreferenceBase {
    @SerializedName("target_temperature_c")
    private int targetTemp;

    public HeatingPreference() {

    }
    
    public HeatingPreference(int targetTemp, String automationType, int actionPriority) {
    	this.targetTemp = targetTemp;
    	this.automationType = automationType;
    	this.actionPriority = actionPriority;
    }

    public int getTargetTemp() {
        return targetTemp;
    }

    public void setTargetTemp(int targetTemp) {
        this.targetTemp = targetTemp;
    }
}
