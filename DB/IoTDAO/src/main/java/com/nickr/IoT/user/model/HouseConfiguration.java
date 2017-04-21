package com.nickr.IoT.user.model;


import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


import java.util.ArrayList;

public class HouseConfiguration{

    public HouseConfiguration(String houseId, ArrayList<Sensor> sensors) {
        this.houseId = houseId;
        this.sensors = sensors;
    }

    @SerializedName("houseId")
    @Expose
    private String houseId;
    @SerializedName("sensors")
    @Expose
    private ArrayList<Sensor> sensors;

    private Location location;



    public HouseConfiguration(){
    	
    }
    
    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public ArrayList<Sensor> getSensors() {
        if (sensors != null) {
            return sensors;
        } else {
            return new ArrayList<>();
        }
    }

    public String toString() {
        return new Gson().toJson(this);
    }

    public void setSensors(ArrayList<Sensor> sensors) {
        this.sensors = sensors;
    }

    public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

}
