package com.mtp.gui.widget;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

  public static Test suite() {
    TestSuite suite = new TestSuite("All com.mtp.gui.widget tests");
    suite.addTest(PackageTests.suite());

    return suite;
  }

}
