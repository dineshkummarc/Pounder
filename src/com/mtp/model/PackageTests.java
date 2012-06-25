package com.mtp.model;

import junit.framework.Test;
import junit.framework.TestSuite;

public class PackageTests {

	public static Test suite() {
		TestSuite suite= new TestSuite("com.mtp.model tests");
		suite.addTest(FileModelTest.suite());
		suite.addTest(DefaultIntegerModelTest.suite());
		suite.addTest(PointModelTest.suite());
		suite.addTest(DefaultIntegerModelTest.suite());
		suite.addTest(ListenersTest.suite());
		suite.addTest(DefaultStringModelTest.suite());
		suite.addTest(DocumentStringModelTest.suite());
		suite.addTest(StatusModelTest.suite());

		return suite;
	}

}
