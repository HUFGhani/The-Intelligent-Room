package io.github.hufghani.philips.hue.properties;

import org.codehaus.jackson.annotate.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hamzaghani on 15/02/2017.
 */
@JsonPropertyOrder({
        "houseId",
        "usersname",
        "hueLightIP"
})
public class HueConfigProperties implements Serializable {

    @JsonProperty("houseId")
    private String houseId;
    @JsonProperty("usersname")
    private String usersname;
    @JsonProperty("hueLightIP")
    private String hueLightIP;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public HueConfigProperties() {
        super();
    }

    public HueConfigProperties(String houseId, String usersname, String hueLightIP) {
        super();
        this.houseId = houseId;
        this.usersname = usersname;
        this.hueLightIP = hueLightIP;
    }

    @JsonProperty("houseID")
    public String getHouseID() {
        return houseId;
    }

    @JsonProperty("houseID")
    public void setHouseID(String houseID) {
        this.houseId = houseId;
    }

    @JsonProperty("usersname")
    public String getUsersname() {
        return usersname;
    }

    @JsonProperty("usersname")
    public void setUsersname(String usersname) {
        this.usersname = usersname;
    }

    @JsonProperty("hueLightIP")
    public String getHueLightIP() {
        return hueLightIP;
    }

    @JsonProperty("hueLightIP")
    public void setHueLightIP(String hueLightIP) {
        this.hueLightIP = hueLightIP;
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
        return "{" +
                "houseId='" + houseId + '\'' +
                ", usersname='" + usersname + '\'' +
                ", hueLightIP='" + hueLightIP + '\'' +
                '}';
    }
}
