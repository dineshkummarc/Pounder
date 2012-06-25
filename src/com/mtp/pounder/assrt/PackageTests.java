package com.mtp.pounder.assrt;

import junit.framework.Test;
import junit.framework.TestSuite;

public class PackageTests {
		
	public static Test suite() {
		TestSuite suite = new TestSuite("com.mtp.pounder.assrt tests");
		suite.addTest(WindowShowingItemTest.suite());
		suite.addTest(WindowShowingPresenterTest.suite());
		suite.addTest(WindowIdentifierPresenterTest.suite());
		suite.addTest(TextEqualsItemTest.suite());
		suite.addTest(TextEqualsPresenterTest.suite());

		return suite;
	}
		
}
