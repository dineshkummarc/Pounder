package com.mtp.pounder.controller;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("All com.mtp.pounder.controller tests");
		suite.addTest(PackageTests.suite());
				
		return suite;
	}

}
