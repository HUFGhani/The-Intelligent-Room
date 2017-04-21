package com.project.smarthome.smarthome.Model.Preferences;


import com.google.gson.annotations.SerializedName;

public abstract class PreferenceBase {

    @SerializedName("actionMethod")
    protected String automationType;

    @SerializedName("actionPriority")
    protected int actionPriority;

    public int getActionPriority() {
        return actionPriority;
    }

    public void setActionPriority() {
        if (automationType.equals("location")) {
            actionPriority = 1;
        } else if (automationType.equals("motion")) {
            actionPriority = 2;
        } else if (automationType.equals("light")) {
            actionPriority = 3;
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
