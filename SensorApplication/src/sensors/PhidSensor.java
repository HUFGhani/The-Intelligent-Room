package sensors;

import java.sql.Timestamp;

public interface PhidSensor {

	public void setSensorLastUpdate(long time);

	public void setSensorValue(int sensorValue);

	public void setSensorType(String sensorType);

	public void setSensorId(String sensorId);
	
	public void setSensorPort(int sensorPort);

	public long getSensorLastUpdate();
	
	public String getSensorType();

	public int getSensorValue();

	public String getSensorName();

	public String getSensorId();
	
	public int getSensorPort();

}
