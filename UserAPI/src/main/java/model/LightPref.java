
package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LightPref implements Serializable {
    public LightPref(Light light, String actionMethod, int actionPriority) {
        this.light = light;
        this.actionMethod = actionMethod;
        this.actionPriority = actionPriority;

    }

    @SerializedName("light")
    @Expose
    private Light light;
    @SerializedName("actionMethod")
    @Expose
    private String actionMethod;
    @SerializedName("actionPriority")
    @Expose
    private int actionPriority;

    public Light getLight() {
        return light;
    }

    public void setLight(Light light) {
        this.light = light;
    }

    public String getActionMethod() {
        return actionMethod;
    }

    public void setActionMethod(String actionMethod) {
        this.actionMethod = actionMethod;
    }

    public int getActionPriority() {
        return actionPriority;
    }

    public void setActionPriority(int actionPriority) {
        this.actionPriority = actionPriority;
    }

}
