package sensors;


public class GeneralPhidSensor implements PhidSensor {


	private String sensorName, sensorId, sensorMethodType;
	private long updateTimestamp;
	private int sensorPort, sensorValue;

	public GeneralPhidSensor(){}
	
	public GeneralPhidSensor(String sensorName, int sensorValue, String sensorId, long updateTimestamp,
			int sensorPort, String sensorType) {
		this.sensorId = sensorId;
		this.sensorName = sensorName;
		this.sensorValue = sensorValue;
		this.updateTimestamp = updateTimestamp;
		this.sensorPort = sensorPort;
		this.sensorMethodType = sensorType;
	}

	public GeneralPhidSensor(GeneralPhidSensor sensor) {
		this.sensorId = sensor.getSensorId();
		this.sensorName = sensor.getSensorName();
		this.sensorValue = sensor.getSensorValue();
		this.updateTimestamp = sensor.getSensorLastUpdate();
		this.sensorPort = sensor.getSensorPort();
		this.sensorMethodType = sensor.getSensorType();
	}

	public String getSensorMethodType() {
		return sensorMethodType;
	}

	public void setSensorMethodType(String sensorMethodType) {
		this.sensorMethodType = sensorMethodType;
	}
	
	public int getSensorPort() {
		return sensorPort;
	}

	public void setSensorPort(int sensorPort) {
		this.sensorPort = sensorPort;
	}

	public long getUpdateTimestamp() {
		return updateTimestamp;
	}

	public void setUpdateTimestamp(long updateTimestamp) {
		this.updateTimestamp = updateTimestamp;
	}

	public String getSensorType() {
		return sensorMethodType;
	}

	public void setSensorName(String sensorName) {
		this.sensorName = sensorName;
	}

	@Override
	public void setSensorLastUpdate(long updateTime) {
		this.updateTimestamp = updateTime;
	}

	@Override
	public void setSensorValue(int sensorValue) {
		this.sensorValue = sensorValue;

	}

	@Override
	public void setSensorType(String sensorName) {
		this.sensorName = sensorName;
	}

	@Override
	public void setSensorId(String sensorId) {
		this.sensorId = sensorId;

	}

	@Override
	public long getSensorLastUpdate() {
		return this.updateTimestamp;
	}

	@Override
	public int getSensorValue() {
		return this.sensorValue;
	}

	@Override
	public String getSensorName() {
		return this.sensorName;
	}

	@Override
	public String getSensorId() {
		return this.sensorId;
	}

	public String toString() {
		return "SensorID: " + sensorId + "\n" + "SensorName: " + sensorName + "\n" + "SensorValue: " + sensorValue
				+ "\n" + "SensorUpdateTime: " + updateTimestamp;
	}

}
