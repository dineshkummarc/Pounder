package com.mtp.pounder.controller;

import junit.framework.Test;
import junit.framework.TestSuite;

public class PackageTests {
		
	public static Test suite() {
		TestSuite suite = new TestSuite("com.mtp.pounder.controller tests");
		suite.addTest(com.mtp.pounder.controller.PounderControllerTest.suite());
		suite.addTest(com.mtp.pounder.controller.SaveActionTest.suite());
		suite.addTest(com.mtp.pounder.controller.SaveAsActionTest.suite());
		suite.addTest(com.mtp.pounder.controller.CloseActionTest.suite());
		suite.addTest(com.mtp.pounder.controller.NewInstanceActionTest.suite());

		return suite;
	}
		
}
