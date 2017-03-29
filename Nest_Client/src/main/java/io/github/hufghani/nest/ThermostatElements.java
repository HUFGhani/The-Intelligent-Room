package io.github.hufghani.nest;

import org.codehaus.jackson.annotate.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hamzaghani on 10/02/2017.
 */
//@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "humidity",
        "locale",
        "temperature_scale",
        "is_using_emergency_heat",
        "has_fan",
        "software_version",
        "has_leaf",
        "where_id",
        "device_id",
        "name",
        "can_heat",
        "can_cool",
        "target_temperature_c",
        "target_temperature_f",
        "target_temperature_high_c",
        "target_temperature_high_f",
        "target_temperature_low_c",
        "target_temperature_low_f",
        "ambient_temperature_c",
        "ambient_temperature_f",
        "away_temperature_high_c",
        "away_temperature_high_f",
        "away_temperature_low_c",
        "away_temperature_low_f",
        "eco_temperature_high_c",
        "eco_temperature_high_f",
        "eco_temperature_low_c",
        "eco_temperature_low_f",
        "is_locked",
        "locked_temp_min_c",
        "locked_temp_min_f",
        "locked_temp_max_c",
        "locked_temp_max_f",
        "sunlight_correction_active",
        "sunlight_correction_enabled",
        "structure_id",
        "fan_timer_active",
        "fan_timer_timeout",
        "fan_timer_duration",
        "previous_hvac_mode",
        "hvac_mode",
        "time_to_target",
        "time_to_target_training",
        "where_name",
        "label",
        "name_long",
        "is_online",
        "last_connection",
        "hvac_state"
})

public class ThermostatElements implements Serializable {
    @JsonProperty("humidity")
    private long humidity;
    @JsonProperty("locale")
    private String locale;
    @JsonProperty("temperature_scale")
    private String temperatureScale;
    @JsonProperty("is_using_emergency_heat")
    private boolean isUsingEmergencyHeat;
    @JsonProperty("has_fan")
    private boolean hasFan;
    @JsonProperty("software_version")
    private String softwareVersion;
    @JsonProperty("has_leaf")
    private boolean hasLeaf;
    @JsonProperty("where_id")
    private String whereId;
    @JsonProperty("device_id")
    private String deviceId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("can_heat")
    private boolean canHeat;
    @JsonProperty("can_cool")
    private boolean canCool;
    @JsonProperty("target_temperature_c")
    private double targetTemperatureC;
    @JsonProperty("target_temperature_f")
    private long targetTemperatureF;
    @JsonProperty("target_temperature_high_c")
    private double targetTemperatureHighC;
    @JsonProperty("target_temperature_high_f")
    private long targetTemperatureHighF;
    @JsonProperty("target_temperature_low_c")
    private double targetTemperatureLowC;
    @JsonProperty("target_temperature_low_f")
    private long targetTemperatureLowF;
    @JsonProperty("ambient_temperature_c")
    private double ambientTemperatureC;
    @JsonProperty("ambient_temperature_f")
    private long ambientTemperatureF;
    @JsonProperty("away_temperature_high_c")
    private double awayTemperatureHighC;
    @JsonProperty("away_temperature_high_f")
    private long awayTemperatureHighF;
    @JsonProperty("away_temperature_low_c")
    private double awayTemperatureLowC;
    @JsonProperty("away_temperature_low_f")
    private long awayTemperatureLowF;
    @JsonProperty("eco_temperature_high_c")
    private double ecoTemperatureHighC;
    @JsonProperty("eco_temperature_high_f")
    private long ecoTemperatureHighF;
    @JsonProperty("eco_temperature_low_c")
    private double ecoTemperatureLowC;
    @JsonProperty("eco_temperature_low_f")
    private long ecoTemperatureLowF;
    @JsonProperty("is_locked")
    private boolean isLocked;
    @JsonProperty("locked_temp_min_c")
    private double lockedTempMinC;
    @JsonProperty("locked_temp_min_f")
    private long lockedTempMinF;
    @JsonProperty("locked_temp_max_c")
    private double lockedTempMaxC;
    @JsonProperty("locked_temp_max_f")
    private long lockedTempMaxF;
    @JsonProperty("sunlight_correction_active")
    private boolean sunlightCorrectionActive;
    @JsonProperty("sunlight_correction_enabled")
    private boolean sunlightCorrectionEnabled;
    @JsonProperty("structure_id")
    private String structureId;
    @JsonProperty("fan_timer_active")
    private boolean fanTimerActive;
    @JsonProperty("fan_timer_timeout")
    private String fanTimerTimeout;
    @JsonProperty("fan_timer_duration")
    private long fanTimerDuration;
    @JsonProperty("previous_hvac_mode")
    private String previousHvacMode;
    @JsonProperty("hvac_mode")
    private String hvacMode;
    @JsonProperty("time_to_target")
    private String timeToTarget;
    @JsonProperty("time_to_target_training")
    private String timeToTargetTraining;
    @JsonProperty("where_name")
    private String whereName;
    @JsonProperty("label")
    private String label;
    @JsonProperty("name_long")
    private String nameLong;
    @JsonProperty("is_online")
    private boolean isOnline;
    @JsonProperty("last_connection")
    private String lastConnection;
    @JsonProperty("hvac_state")
    private String hvacState;
    @JsonProperty("automated")
    private boolean automated;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public ThermostatElements() {
        super();
    }

    public ThermostatElements(long humidity, String locale, String temperatureScale, boolean isUsingEmergencyHeat,
                              boolean hasFan, String softwareVersion, boolean hasLeaf, String whereId, String deviceId,
                              String name, boolean canHeat, boolean canCool, double targetTemperatureC,
                              long targetTemperatureF, double targetTemperatureHighC, long targetTemperatureHighF,
                              double targetTemperatureLowC, long targetTemperatureLowF, double ambientTemperatureC,
                              long ambientTemperatureF, double awayTemperatureHighC, long awayTemperatureHighF,
                              double awayTemperatureLowC, long awayTemperatureLowF, double ecoTemperatureHighC,
                              long ecoTemperatureHighF, double ecoTemperatureLowC, long ecoTemperatureLowF,
                              boolean isLocked, double lockedTempMinC, long lockedTempMinF, double lockedTempMaxC,
                              long lockedTempMaxF, boolean sunlightCorrectionActive, boolean sunlightCorrectionEnabled,
                              String structureId, boolean fanTimerActive, String fanTimerTimeout, long fanTimerDuration,
                              String previousHvacMode, String hvacMode, String timeToTarget, String timeToTargetTraining,
                              String whereName, String label, String nameLong, boolean isOnline, String lastConnection,
                              String hvacState, Map<String, Object> additionalProperties, boolean automated) {
        this.humidity = humidity;
        this.locale = locale;
        this.temperatureScale = temperatureScale;
        this.isUsingEmergencyHeat = isUsingEmergencyHeat;
        this.hasFan = hasFan;
        this.softwareVersion = softwareVersion;
        this.hasLeaf = hasLeaf;
        this.whereId = whereId;
        this.deviceId = deviceId;
        this.name = name;
        this.canHeat = canHeat;
        this.canCool = canCool;
        this.targetTemperatureC = targetTemperatureC;
        this.targetTemperatureF = targetTemperatureF;
        this.targetTemperatureHighC = targetTemperatureHighC;
        this.targetTemperatureHighF = targetTemperatureHighF;
        this.targetTemperatureLowC = targetTemperatureLowC;
        this.targetTemperatureLowF = targetTemperatureLowF;
        this.ambientTemperatureC = ambientTemperatureC;
        this.ambientTemperatureF = ambientTemperatureF;
        this.awayTemperatureHighC = awayTemperatureHighC;
        this.awayTemperatureHighF = awayTemperatureHighF;
        this.awayTemperatureLowC = awayTemperatureLowC;
        this.awayTemperatureLowF = awayTemperatureLowF;
        this.ecoTemperatureHighC = ecoTemperatureHighC;
        this.ecoTemperatureHighF = ecoTemperatureHighF;
        this.ecoTemperatureLowC = ecoTemperatureLowC;
        this.ecoTemperatureLowF = ecoTemperatureLowF;
        this.isLocked = isLocked;
        this.lockedTempMinC = lockedTempMinC;
        this.lockedTempMinF = lockedTempMinF;
        this.lockedTempMaxC = lockedTempMaxC;
        this.lockedTempMaxF = lockedTempMaxF;
        this.sunlightCorrectionActive = sunlightCorrectionActive;
        this.sunlightCorrectionEnabled = sunlightCorrectionEnabled;
        this.structureId = structureId;
        this.fanTimerActive = fanTimerActive;
        this.fanTimerTimeout = fanTimerTimeout;
        this.fanTimerDuration = fanTimerDuration;
        this.previousHvacMode = previousHvacMode;
        this.hvacMode = hvacMode;
        this.timeToTarget = timeToTarget;
        this.timeToTargetTraining = timeToTargetTraining;
        this.whereName = whereName;
        this.label = label;
        this.nameLong = nameLong;
        this.isOnline = isOnline;
        this.lastConnection = lastConnection;
        this.hvacState = hvacState;
        this.additionalProperties = additionalProperties;
        this.automated =automated;
    }

    @JsonProperty("humidity")
    public long getHumidity() {
        return humidity;
    }

    @JsonProperty("humidity")
    public void setHumidity(long humidity) {
        this.humidity = humidity;
    }

    @JsonProperty("locale")
    public String getLocale() {
        return locale;
    }

    @JsonProperty("locale")
    public void setLocale(String locale) {
        this.locale = locale;
    }

    @JsonProperty("temperature_scale")
    public String getTemperatureScale() {
        return temperatureScale;
    }

    @JsonProperty("temperature_scale")
    public void setTemperatureScale(String temperatureScale) {
        this.temperatureScale = temperatureScale;
    }

    @JsonProperty("is_using_emergency_heat")
    public boolean isIsUsingEmergencyHeat() {
        return isUsingEmergencyHeat;
    }

    @JsonProperty("is_using_emergency_heat")
    public void setIsUsingEmergencyHeat(boolean isUsingEmergencyHeat) {
        this.isUsingEmergencyHeat = isUsingEmergencyHeat;
    }

    @JsonProperty("has_fan")
    public boolean isHasFan() {
        return hasFan;
    }

    @JsonProperty("has_fan")
    public void setHasFan(boolean hasFan) {
        this.hasFan = hasFan;
    }

    @JsonProperty("software_version")
    public String getSoftwareVersion() {
        return softwareVersion;
    }

    @JsonProperty("software_version")
    public void setSoftwareVersion(String softwareVersion) {
        this.softwareVersion = softwareVersion;
    }

    @JsonProperty("has_leaf")
    public boolean isHasLeaf() {
        return hasLeaf;
    }

    @JsonProperty("has_leaf")
    public void setHasLeaf(boolean hasLeaf) {
        this.hasLeaf = hasLeaf;
    }

    @JsonProperty("where_id")
    public String getWhereId() {
        return whereId;
    }

    @JsonProperty("where_id")
    public void setWhereId(String whereId) {
        this.whereId = whereId;
    }

    @JsonProperty("device_id")
    public String getDeviceId() {
        return deviceId;
    }

    @JsonProperty("device_id")
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("can_heat")
    public boolean isCanHeat() {
        return canHeat;
    }

    @JsonProperty("can_heat")
    public void setCanHeat(boolean canHeat) {
        this.canHeat = canHeat;
    }

    @JsonProperty("can_cool")
    public boolean isCanCool() {
        return canCool;
    }

    @JsonProperty("can_cool")
    public void setCanCool(boolean canCool) {
        this.canCool = canCool;
    }

    @JsonProperty("target_temperature_c")
    public double getTargetTemperatureC() {
        return targetTemperatureC;
    }

    @JsonProperty("target_temperature_c")
    public void setTargetTemperatureC(double targetTemperatureC) {
        this.targetTemperatureC = targetTemperatureC;
    }

    @JsonProperty("target_temperature_f")
    public long getTargetTemperatureF() {
        return targetTemperatureF;
    }

    @JsonProperty("target_temperature_f")
    public void setTargetTemperatureF(long targetTemperatureF) {
        this.targetTemperatureF = targetTemperatureF;
    }

    @JsonProperty("target_temperature_high_c")
    public double getTargetTemperatureHighC() {
        return targetTemperatureHighC;
    }

    @JsonProperty("target_temperature_high_c")
    public void setTargetTemperatureHighC(double targetTemperatureHighC) {
        this.targetTemperatureHighC = targetTemperatureHighC;
    }

    @JsonProperty("target_temperature_high_f")
    public long getTargetTemperatureHighF() {
        return targetTemperatureHighF;
    }

    @JsonProperty("target_temperature_high_f")
    public void setTargetTemperatureHighF(long targetTemperatureHighF) {
        this.targetTemperatureHighF = targetTemperatureHighF;
    }

    @JsonProperty("target_temperature_low_c")
    public double getTargetTemperatureLowC() {
        return targetTemperatureLowC;
    }

    @JsonProperty("target_temperature_low_c")
    public void setTargetTemperatureLowC(double targetTemperatureLowC) {
        this.targetTemperatureLowC = targetTemperatureLowC;
    }

    @JsonProperty("target_temperature_low_f")
    public long getTargetTemperatureLowF() {
        return targetTemperatureLowF;
    }

    @JsonProperty("target_temperature_low_f")
    public void setTargetTemperatureLowF(long targetTemperatureLowF) {
        this.targetTemperatureLowF = targetTemperatureLowF;
    }

    @JsonProperty("ambient_temperature_c")
    public double getAmbientTemperatureC() {
        return ambientTemperatureC;
    }

    @JsonProperty("ambient_temperature_c")
    public void setAmbientTemperatureC(double ambientTemperatureC) {
        this.ambientTemperatureC = ambientTemperatureC;
    }

    @JsonProperty("ambient_temperature_f")
    public long getAmbientTemperatureF() {
        return ambientTemperatureF;
    }

    @JsonProperty("ambient_temperature_f")
    public void setAmbientTemperatureF(long ambientTemperatureF) {
        this.ambientTemperatureF = ambientTemperatureF;
    }

    @JsonProperty("away_temperature_high_c")
    public double getAwayTemperatureHighC() {
        return awayTemperatureHighC;
    }

    @JsonProperty("away_temperature_high_c")
    public void setAwayTemperatureHighC(double awayTemperatureHighC) {
        this.awayTemperatureHighC = awayTemperatureHighC;
    }

    @JsonProperty("away_temperature_high_f")
    public long getAwayTemperatureHighF() {
        return awayTemperatureHighF;
    }

    @JsonProperty("away_temperature_high_f")
    public void setAwayTemperatureHighF(long awayTemperatureHighF) {
        this.awayTemperatureHighF = awayTemperatureHighF;
    }

    @JsonProperty("away_temperature_low_c")
    public double getAwayTemperatureLowC() {
        return awayTemperatureLowC;
    }

    @JsonProperty("away_temperature_low_c")
    public void setAwayTemperatureLowC(double awayTemperatureLowC) {
        this.awayTemperatureLowC = awayTemperatureLowC;
    }

    @JsonProperty("away_temperature_low_f")
    public long getAwayTemperatureLowF() {
        return awayTemperatureLowF;
    }

    @JsonProperty("away_temperature_low_f")
    public void setAwayTemperatureLowF(long awayTemperatureLowF) {
        this.awayTemperatureLowF = awayTemperatureLowF;
    }

    @JsonProperty("eco_temperature_high_c")
    public double getEcoTemperatureHighC() {
        return ecoTemperatureHighC;
    }

    @JsonProperty("eco_temperature_high_c")
    public void setEcoTemperatureHighC(double ecoTemperatureHighC) {
        this.ecoTemperatureHighC = ecoTemperatureHighC;
    }

    @JsonProperty("eco_temperature_high_f")
    public long getEcoTemperatureHighF() {
        return ecoTemperatureHighF;
    }

    @JsonProperty("eco_temperature_high_f")
    public void setEcoTemperatureHighF(long ecoTemperatureHighF) {
        this.ecoTemperatureHighF = ecoTemperatureHighF;
    }

    @JsonProperty("eco_temperature_low_c")
    public double getEcoTemperatureLowC() {
        return ecoTemperatureLowC;
    }

    @JsonProperty("eco_temperature_low_c")
    public void setEcoTemperatureLowC(double ecoTemperatureLowC) {
        this.ecoTemperatureLowC = ecoTemperatureLowC;
    }

    @JsonProperty("eco_temperature_low_f")
    public long getEcoTemperatureLowF() {
        return ecoTemperatureLowF;
    }

    @JsonProperty("eco_temperature_low_f")
    public void setEcoTemperatureLowF(long ecoTemperatureLowF) {
        this.ecoTemperatureLowF = ecoTemperatureLowF;
    }

    @JsonProperty("is_locked")
    public boolean isIsLocked() {
        return isLocked;
    }

    @JsonProperty("is_locked")
    public void setIsLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }

    @JsonProperty("locked_temp_min_c")
    public double getLockedTempMinC() {
        return lockedTempMinC;
    }

    @JsonProperty("locked_temp_min_c")
    public void setLockedTempMinC(double lockedTempMinC) {
        this.lockedTempMinC = lockedTempMinC;
    }

    @JsonProperty("locked_temp_min_f")
    public long getLockedTempMinF() {
        return lockedTempMinF;
    }

    @JsonProperty("locked_temp_min_f")
    public void setLockedTempMinF(long lockedTempMinF) {
        this.lockedTempMinF = lockedTempMinF;
    }

    @JsonProperty("locked_temp_max_c")
    public double getLockedTempMaxC() {
        return lockedTempMaxC;
    }

    @JsonProperty("locked_temp_max_c")
    public void setLockedTempMaxC(double lockedTempMaxC) {
        this.lockedTempMaxC = lockedTempMaxC;
    }

    @JsonProperty("locked_temp_max_f")
    public long getLockedTempMaxF() {
        return lockedTempMaxF;
    }

    @JsonProperty("locked_temp_max_f")
    public void setLockedTempMaxF(long lockedTempMaxF) {
        this.lockedTempMaxF = lockedTempMaxF;
    }

    @JsonProperty("sunlight_correction_active")
    public boolean isSunlightCorrectionActive() {
        return sunlightCorrectionActive;
    }

    @JsonProperty("sunlight_correction_active")
    public void setSunlightCorrectionActive(boolean sunlightCorrectionActive) {
        this.sunlightCorrectionActive = sunlightCorrectionActive;
    }

    @JsonProperty("sunlight_correction_enabled")
    public boolean isSunlightCorrectionEnabled() {
        return sunlightCorrectionEnabled;
    }

    @JsonProperty("sunlight_correction_enabled")
    public void setSunlightCorrectionEnabled(boolean sunlightCorrectionEnabled) {
        this.sunlightCorrectionEnabled = sunlightCorrectionEnabled;
    }

    @JsonProperty("structure_id")
    public String getStructureId() {
        return structureId;
    }

    @JsonProperty("structure_id")
    public void setStructureId(String structureId) {
        this.structureId = structureId;
    }

    @JsonProperty("fan_timer_active")
    public boolean isFanTimerActive() {
        return fanTimerActive;
    }

    @JsonProperty("fan_timer_active")
    public void setFanTimerActive(boolean fanTimerActive) {
        this.fanTimerActive = fanTimerActive;
    }

    @JsonProperty("fan_timer_timeout")
    public String getFanTimerTimeout() {
        return fanTimerTimeout;
    }

    @JsonProperty("fan_timer_timeout")
    public void setFanTimerTimeout(String fanTimerTimeout) {
        this.fanTimerTimeout = fanTimerTimeout;
    }

    @JsonProperty("fan_timer_duration")
    public long getFanTimerDuration() {
        return fanTimerDuration;
    }

    @JsonProperty("fan_timer_duration")
    public void setFanTimerDuration(long fanTimerDuration) {
        this.fanTimerDuration = fanTimerDuration;
    }

    @JsonProperty("previous_hvac_mode")
    public String getPreviousHvacMode() {
        return previousHvacMode;
    }

    @JsonProperty("previous_hvac_mode")
    public void setPreviousHvacMode(String previousHvacMode) {
        this.previousHvacMode = previousHvacMode;
    }

    @JsonProperty("hvac_mode")
    public String getHvacMode() {
        return hvacMode;
    }

    @JsonProperty("hvac_mode")
    public void setHvacMode(String hvacMode) {
        this.hvacMode = hvacMode;
    }

    @JsonProperty("time_to_target")
    public String getTimeToTarget() {
        return timeToTarget;
    }

    @JsonProperty("time_to_target")
    public void setTimeToTarget(String timeToTarget) {
        this.timeToTarget = timeToTarget;
    }

    @JsonProperty("time_to_target_training")
    public String getTimeToTargetTraining() {
        return timeToTargetTraining;
    }

    @JsonProperty("time_to_target_training")
    public void setTimeToTargetTraining(String timeToTargetTraining) {
        this.timeToTargetTraining = timeToTargetTraining;
    }

    @JsonProperty("where_name")
    public String getWhereName() {
        return whereName;
    }

    @JsonProperty("where_name")
    public void setWhereName(String whereName) {
        this.whereName = whereName;
    }

    @JsonProperty("label")
    public String getLabel() {
        return label;
    }

    @JsonProperty("label")
    public void setLabel(String label) {
        this.label = label;
    }

    @JsonProperty("name_long")
    public String getNameLong() {
        return nameLong;
    }

    @JsonProperty("name_long")
    public void setNameLong(String nameLong) {
        this.nameLong = nameLong;
    }

    @JsonProperty("is_online")
    public boolean isIsOnline() {
        return isOnline;
    }

    @JsonProperty("is_online")
    public void setIsOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

    @JsonProperty("last_connection")
    public String getLastConnection() {
        return lastConnection;
    }

    @JsonProperty("last_connection")
    public void setLastConnection(String lastConnection) {
        this.lastConnection = lastConnection;
    }

    @JsonProperty("hvac_state")
    public String getHvacState() {
        return hvacState;
    }

    @JsonProperty("hvac_state")
    public void setHvacState(String hvacState) {
        this.hvacState = hvacState;
    }

    @JsonProperty("automated")
    public boolean isAutomated() {
        return automated;
    }

    @JsonProperty("automated")
    public void setAutomated(boolean automated) {
        this.automated = automated;
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
        return "ThermostatElements{" +
                "humidity=" + humidity +
                ", locale='" + locale + '\'' +
                ", temperatureScale='" + temperatureScale + '\'' +
                ", isUsingEmergencyHeat=" + isUsingEmergencyHeat +
                ", hasFan=" + hasFan +
                ", softwareVersion='" + softwareVersion + '\'' +
                ", hasLeaf=" + hasLeaf +
                ", whereId='" + whereId + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", name='" + name + '\'' +
                ", canHeat=" + canHeat +
                ", canCool=" + canCool +
                ", targetTemperatureC=" + targetTemperatureC +
                ", targetTemperatureF=" + targetTemperatureF +
                ", targetTemperatureHighC=" + targetTemperatureHighC +
                ", targetTemperatureHighF=" + targetTemperatureHighF +
                ", targetTemperatureLowC=" + targetTemperatureLowC +
                ", targetTemperatureLowF=" + targetTemperatureLowF +
                ", ambientTemperatureC=" + ambientTemperatureC +
                ", ambientTemperatureF=" + ambientTemperatureF +
                ", awayTemperatureHighC=" + awayTemperatureHighC +
                ", awayTemperatureHighF=" + awayTemperatureHighF +
                ", awayTemperatureLowC=" + awayTemperatureLowC +
                ", awayTemperatureLowF=" + awayTemperatureLowF +
                ", ecoTemperatureHighC=" + ecoTemperatureHighC +
                ", ecoTemperatureHighF=" + ecoTemperatureHighF +
                ", ecoTemperatureLowC=" + ecoTemperatureLowC +
                ", ecoTemperatureLowF=" + ecoTemperatureLowF +
                ", isLocked=" + isLocked +
                ", lockedTempMinC=" + lockedTempMinC +
                ", lockedTempMinF=" + lockedTempMinF +
                ", lockedTempMaxC=" + lockedTempMaxC +
                ", lockedTempMaxF=" + lockedTempMaxF +
                ", sunlightCorrectionActive=" + sunlightCorrectionActive +
                ", sunlightCorrectionEnabled=" + sunlightCorrectionEnabled +
                ", structureId='" + structureId + '\'' +
                ", fanTimerActive=" + fanTimerActive +
                ", fanTimerTimeout='" + fanTimerTimeout + '\'' +
                ", fanTimerDuration=" + fanTimerDuration +
                ", previousHvacMode='" + previousHvacMode + '\'' +
                ", hvacMode='" + hvacMode + '\'' +
                ", timeToTarget='" + timeToTarget + '\'' +
                ", timeToTargetTraining='" + timeToTargetTraining + '\'' +
                ", whereName='" + whereName + '\'' +
                ", label='" + label + '\'' +
                ", nameLong='" + nameLong + '\'' +
                ", isOnline=" + isOnline +
                ", lastConnection='" + lastConnection + '\'' +
                ", hvacState='" + hvacState + '\'' +
                ", additionalProperties=" + additionalProperties +
                '}';
    }

    public String getStatus(){
        return "{ " +
                "targetTemperatureC:"+targetTemperatureC+","
                +"automated:"+automated+
                "}";
    }
}
