package io.github.hufghani.nest.properties;

import org.codehaus.jackson.annotate.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hamzaghani on 16/02/2017.
 */
@JsonPropertyOrder({
        "houseID",
        "usersname",
        "hueLightIP"
})

public class NestConfigProperties implements Serializable {

    @JsonProperty("houseID")
    private String houseID;
    @JsonProperty("usersname")
    private String usersname;
    @JsonProperty("hueLightIP")
    private String hueLightIP;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public NestConfigProperties() {
        super();
    }

    public NestConfigProperties(String houseID, String usersname, String hueLightIP) {
        super();
        this.houseID = houseID;
        this.usersname = usersname;
        this.hueLightIP = hueLightIP;
    }

    @JsonProperty("houseID")
    public String getHouseID() {
        return houseID;
    }

    @JsonProperty("houseID")
    public void setHouseID(String houseID) {
        this.houseID = houseID;
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
                "houseID='" + houseID + '\'' +
                ", usersname='" + usersname + '\'' +
                ", hueLightIP='" + hueLightIP + '\'' +
                '}';
    }
}
