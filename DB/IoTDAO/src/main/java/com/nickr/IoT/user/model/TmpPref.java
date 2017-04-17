
package com.nickr.IoT.user.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TmpPref extends PreferenceBase{

    public TmpPref(Nest nest, String automationType, int actionPriority) {
        this.nest = nest;
        this.automationType = automationType;
        this.actionPriority = actionPriority;
    }

    @SerializedName("nest")
    @Expose
    private Nest nest;

    @SerializedName("target_temperature_c")
    private int targetTemp;

    public int getTargetTemp() {
        return targetTemp;
    }

    public void setTargetTemp(int targetTemp) {
        this.targetTemp = targetTemp;
    }
}
