package io.github.hufghani.philips.hue;

import org.codehaus.jackson.annotate.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hamzaghani on 15/02/2017.
 */
@JsonPropertyOrder({
        "light"
})
public class PhilipsHue implements Serializable {
    @JsonProperty("light")
    private Light light;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public PhilipsHue() {
        super();
    }

    public PhilipsHue(Light light) {
        super();
        this.light = light;
    }

    @JsonProperty("light")
    public Light getLight() {
        return light;
    }

    @JsonProperty("light")
    public void setLight(Light light) {
        this.light = light;
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
        return "PhilipsHue{" +
                "light=" + light +
                '}';
    }
}
