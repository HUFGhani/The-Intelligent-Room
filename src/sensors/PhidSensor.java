package sensors;

import java.sql.Timestamp;

public interface PhidSensor {

	public void setSensorLastUpdate(Timestamp time);

	public void setSensorValue(int sensorValue);

	public void setSensorType(String sensorType);

	public void setSensorId(String sensorId);
	
	public void setSensorPort(int sensorPort);

	public Timestamp getSensorLastUpdate();
	
	public String getSensorType();

	public int getSensorValue();

	public String getSensorName();

	public String getSensorId();
	
	public int getSensorPort();

}
