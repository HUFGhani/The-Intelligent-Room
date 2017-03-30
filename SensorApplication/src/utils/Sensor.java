package utils;

public class Sensor {
	private String sensorName;
	private String sensorMethodType;

	/**
	 * 
	 * @return
	 */
	public String getSensorName() {
		return sensorName;
	}

	/**
	 * 
	 * @param sensorName
	 */
	public void setSensorName(String sensorName) {
		this.sensorName = sensorName;
	}

	/**
	 * 
	 * @return
	 */
	public String getSensorMethodType() {
		return sensorMethodType;
	}

	/**
	 * 
	 * @param sensorMethodType
	 */
	public void setSensorMethodType(String sensorMethodType) {
		this.sensorMethodType = sensorMethodType;
	}

	@Override
	public String toString() {
		return "Sensor [sensorName=" + sensorName + ", sensorMethodType=" + sensorMethodType + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sensorMethodType == null) ? 0 : sensorMethodType.hashCode());
		result = prime * result + ((sensorName == null) ? 0 : sensorName.hashCode());
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
		Sensor other = (Sensor) obj;
		if (sensorMethodType == null) {
			if (other.sensorMethodType != null)
				return false;
		} else if (!sensorMethodType.equals(other.sensorMethodType))
			return false;
		if (sensorName == null) {
			if (other.sensorName != null)
				return false;
		} else if (!sensorName.equals(other.sensorName))
			return false;
		return true;
	}

}
