
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

    @SerializedName("nest")

    public Nest getNest() {
        return nest;
    }

    public TmpPref setNest(Nest nest) {
        this.nest = nest;
        return this;
    }

    @Override
    public String toString() {
        return "TmpPref{" +
                "nest=" + nest +
                '}';
    }
}
