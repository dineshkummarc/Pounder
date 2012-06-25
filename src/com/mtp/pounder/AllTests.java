package com.mtp.pounder;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("All com.mtp.pounder tests");
		suite.addTest(PackageTests.suite());
		suite.addTest(com.mtp.pounder.controller.AllTests.suite());
		suite.addTest(com.mtp.pounder.assrt.AllTests.suite());
				
		return suite;
	}

}
