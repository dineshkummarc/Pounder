package com.mtp.gui;

import junit.framework.Test;
import junit.framework.TestSuite;

public class PackageTests {

  public static Test suite() {
		TestSuite suite = new TestSuite("com.mtp.gui tests");
		suite.addTest(WindowWatcherTest.suite());

		return suite;
  }

}
