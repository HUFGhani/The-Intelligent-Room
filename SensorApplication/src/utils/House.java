package utils;

import java.util.ArrayList;

import sensors.GeneralPhidSensor;

public class House {
	private String houseId;
	private ArrayList<GeneralPhidSensor> sensors;

	/**
	 * 
	 * @return
	 */
	public ArrayList<GeneralPhidSensor> getSensors() {
		return sensors;
	}

	/**
	 * 
	 * @param sensors
	 */
	public void setSensors(ArrayList<GeneralPhidSensor> sensors) {
		this.sensors = sensors;
	}

	/**
	 * 
	 * @return
	 */
	public String getHouseId() {
		return houseId;
	}

	/**
	 * 
	 * @param houseId
	 */
	public void setHouseId(String houseId) {
		this.houseId = houseId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((houseId == null) ? 0 : houseId.hashCode());
		result = prime * result + ((sensors == null) ? 0 : sensors.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		House other = (House) obj;
		if (houseId == null) {
			if (other.houseId != null)
				return false;
		} else if (!houseId.equals(other.houseId))
			return false;
		if (sensors == null) {
			if (other.sensors != null)
				return false;
		} else if (!sensors.equals(other.sensors))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "House [houseId=" + houseId + ", sensors=" + sensors + "]";
	}

}
