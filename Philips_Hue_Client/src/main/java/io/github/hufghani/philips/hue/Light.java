package io.github.hufghani.philips.hue;

import org.codehaus.jackson.annotate.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hamzaghani on 15/02/2017.
 */
@JsonPropertyOrder({
        "name",
        "on/off",
        "colour",
        "brighness",
        "Saturation"
})
public class Light implements Serializable{
    @JsonProperty("name")
    private String name;
    @JsonProperty("on/off")
    private boolean onOff;
    @JsonProperty("colour")
    private Colour colour;
    @JsonProperty("brightness")
    private int brightness;
    @JsonProperty("Saturation")
    private int saturation;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("automated")
    private boolean automated;


    public boolean isAutomated() {
        return automated;
    }

    public Light setAutomated(boolean automated) {
        this.automated = automated;
        return this;
    }



    public Light() {
        super();
    }

    public Light(String name, boolean onOff, Colour colour, int brightness, int saturation,boolean automated) {
        super();
        this.name = name;
        this.onOff = onOff;
        this.colour = colour;
        this.brightness = brightness;
        this.saturation = saturation;
        this.automated = automated;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("on/off")
    public boolean isOnOff() {
        return onOff;
    }

    @JsonProperty("on/off")
    public void setOnOff(boolean onOff) {
        this.onOff = onOff;
    }

    @JsonProperty("colour")
    public Colour getColour() {
        return colour;
    }

    @JsonProperty("colour")
    public void setColour(Colour colour) {
        this.colour = colour;
    }

    @JsonProperty("brightness")
    public int getBrightness() {
        return brightness;
    }

    @JsonProperty("brightness")
    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    @JsonProperty("saturation")
    public int getSaturation() {
        return saturation;
    }

    @JsonProperty("saturation")
    public void setSaturation(int saturation) {
        this.saturation = saturation;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return "Light{" +
                "name='" + name + '\'' +
                ", onOff=" + onOff +
                ", colour=" + colour +
                ", brightness=" + brightness +
                ", saturation=" + saturation +
                ", automated=" + automated +
                '}';
    }
}
