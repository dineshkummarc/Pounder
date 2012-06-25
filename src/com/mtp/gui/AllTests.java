package com.mtp.gui;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

  public static Test suite() {
    TestSuite suite = new TestSuite("All com.mtp.gui tests");
    suite.addTest(PackageTests.suite());
    suite.addTest(com.mtp.gui.widget.AllTests.suite());

    return suite;
  }

}
