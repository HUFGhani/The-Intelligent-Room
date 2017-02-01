package unitTests;

import junit.framework.TestResult;
import junit.framework.TestSuite;

public class TestSuit {
	   public static void main(String[] a) {

		      TestSuite suite = new TestSuite(SensorObjectPosUnitOne.class, SensorObjectNegUnitOne.class);
		      TestResult result = new TestResult();
		      suite.run(result);
		      System.out.println("Number of test cases = " + result.runCount());
		   }
}
