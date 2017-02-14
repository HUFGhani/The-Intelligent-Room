package io.github.hufghani.nest;

import org.codehaus.jackson.annotate.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hamzaghani on 10/02/2017.
 */
@JsonPropertyOrder({
        "0KD654moZP0wgM2qlRxQxFcNidHWg5j2"
})
public class Thermostats implements Serializable {

    private ThermostatElements thermostatElements;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Thermostats() {
super();
    }

    public Thermostats(ThermostatElements thermostatElements) {
        this.thermostatElements = thermostatElements;
    }

    @JsonProperty("0KD654moZP0wgM2qlRxQxFcNidHWg5j2")
    public ThermostatElements getThermostatElements() {
        return thermostatElements;
    }

    @JsonProperty("0KD654moZP0wgM2qlRxQxFcNidHWg5j2")
    public Thermostats setThermostatElements(ThermostatElements thermostatElements) {
        this.thermostatElements = thermostatElements;
        return this;
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
        return "Thermostats{" +
                "thermostatElements=" + thermostatElements +
                '}';
    }
}
