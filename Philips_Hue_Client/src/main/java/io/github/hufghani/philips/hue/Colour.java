package io.github.hufghani.philips.hue;

import org.codehaus.jackson.annotate.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hamzaghani on 15/02/2017.
 */

@JsonPropertyOrder({
        "red",
        "green",
        "blue"
})
public class Colour implements Serializable{
    @JsonProperty("red")
    private int red;
    @JsonProperty("green")
    private int green;
    @JsonProperty("blue")
    private int blue;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Colour() {
        super();
    }

    public Colour(int red, int green, int blue) {
        super();
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    @JsonProperty("red")
    public int getRed() {
        return red;
    }

    @JsonProperty("red")
    public void setRed(int red) {
        this.red = red;
    }

    @JsonProperty("green")
    public int getGreen() {
        return green;
    }

    @JsonProperty("green")
    public void setGreen(int green) {
        this.green = green;
    }

    @JsonProperty("blue")
    public int getBlue() {
        return blue;
    }

    @JsonProperty("blue")
    public void setBlue(int blue) {
        this.blue = blue;
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
        return "Colour{" +
                "red=" + red +
                ", green=" + green +
                ", blue=" + blue +
                '}';
    }
}
