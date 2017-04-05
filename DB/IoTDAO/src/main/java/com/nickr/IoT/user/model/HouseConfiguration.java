package com.nickr.IoT.user.model;


import com.google.gson.Gson;


import java.util.ArrayList;

public class HouseConfiguration {
    private int houseId;
    private ArrayList<Sensor> sensors;
    private boolean hasNest;
    private boolean hasLights;
    private Location location;
    
    public HouseConfiguration(){
    	
    }
    
    public int getHouseId() {
        return houseId;
    }

    public void setHouseId(int houseId) {
        this.houseId = houseId;
    }

    public ArrayList<Sensor> getSensors() {
        if (sensors != null) {
            return sensors;
        } else {
            return new ArrayList<>();
        }
    }

    public void setSensors(ArrayList<Sensor> sensors) {
        this.sensors = sensors;
    }

    public boolean isHasNest() {
        return hasNest;
    }

    public void setHasNest(boolean hasNest) {
        this.hasNest = hasNest;
    }

    public boolean isHasLights() {
        return hasLights;
    }

    public void setHasLights(boolean hasLights) {
        this.hasLights = hasLights;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
}
