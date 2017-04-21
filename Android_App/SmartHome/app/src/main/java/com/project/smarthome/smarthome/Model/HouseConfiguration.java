package com.project.smarthome.smarthome.Model;


import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.project.smarthome.smarthome.Model.Devices.Sensors.SensorDevice;

import java.util.ArrayList;

public class HouseConfiguration {
    @SerializedName("houseId")
    private String houseId;

    @SerializedName("sensors")
    private ArrayList<SensorDevice> sensors;

    @SerializedName("location")
    private LatLng location;

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public ArrayList<SensorDevice> getSensors() {
        return (sensors != null) ? sensors : new ArrayList<SensorDevice>();
    }

    public void setSensors(ArrayList<SensorDevice> sensors) {
        this.sensors = sensors;
    }

    public void updateSensor(SensorDevice sensor) {
        for (int index = 0; index < sensors.size(); index++) {
            if (sensors.get(index).getId() == sensor.getId()) {
                sensors.set(index, sensor);
            }
        }
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
