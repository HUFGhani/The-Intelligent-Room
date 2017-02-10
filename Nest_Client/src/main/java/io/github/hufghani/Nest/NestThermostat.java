package io.github.hufghani.Nest;

import org.codehaus.jackson.annotate.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hamzaghani on 10/02/2017.
 */
@JsonPropertyOrder({
        "thermostats"
})
public class NestThermostat implements Serializable {

    @JsonProperty("thermostats")
    private Thermostats thermostats;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public NestThermostat() {
        super();
    }

    public NestThermostat(Thermostats thermostats) {
        this.thermostats = thermostats;
    }

    @JsonProperty("thermostats")
    public Thermostats getThermostats() {
        return thermostats;
    }

    @JsonProperty("thermostats")
    public void setThermostats(Thermostats thermostats) {
        this.thermostats = thermostats;
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
        return "NestThermostat{" +
                "thermostats=" + thermostats +
                '}';
    }
}


