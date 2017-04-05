package com.nickr.IoT.user.model;


import com.google.gson.annotations.SerializedName;

public abstract class PreferenceBase {

    @SerializedName("actionMethod")
    protected String automationType;

    protected int actionPriority;

    public int getActionPriority() {
        return actionPriority;
    }

    public void setActionPriority() {
        if (automationType.equals("location")) {
            actionPriority = 1;
        } else if (automationType.equals("motion")) {
            actionPriority = 2;
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public String getAutomationType() {
        return automationType;
    }

    public void setAutomationType(String automationType) {
        this.automationType = automationType;
        setActionPriority();
    }
}
