package unitTests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import sensors.GeneralPhidSensor;

public class SensorObjectNegUnitOne {
	private GeneralPhidSensor sensor;
	private String sensorId;

	@Before
	public void setUp() {
		sensorId = "Id123";
		sensor = new GeneralPhidSensor();
	}

	@Test
	public void negTestGetId() {
		assertEquals(sensorId, sensor.getSensorId());
	}

}
