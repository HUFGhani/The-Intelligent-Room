package unitTests;

import static org.junit.Assert.*;

import java.sql.Timestamp;

import org.junit.Before;
import org.junit.Test;

import sensors.GeneralPhidSensor;
import static org.junit.Assert.assertEquals;

public class SensorObjectPosUnitOne {
	String sensorName, sensorId, sensorType;
	Timestamp updateTimestamp;
	int sensorPort, sensorValue;
	GeneralPhidSensor sensor;

	@Before
	public void setUp() {
		sensorId = "Id123";
		sensor = new GeneralPhidSensor();
		sensor.setSensorId(sensorId);

	}

	@Test
	public void posTestGetId() {
		sensorId = "Id123";
		sensor = new GeneralPhidSensor();
		sensor.setSensorId(sensorId);
		assertEquals(sensorId, sensor.getSensorId());
	}

}
