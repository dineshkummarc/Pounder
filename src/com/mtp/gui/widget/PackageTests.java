package com.mtp.gui.widget;

import junit.framework.Test;
import junit.framework.TestSuite;

public class PackageTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("com.mtp.gui.widget tests");
		suite.addTest(KeyEventSelectorTest.suite());

		return suite;
	}
		
}
