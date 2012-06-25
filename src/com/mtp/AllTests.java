package com.mtp;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

  public static Test suite() {
    TestSuite suite = new TestSuite("All com.mtp tests");
    suite.addTest(PackageTests.suite());
    suite.addTest(com.mtp.model.AllTests.suite());
    suite.addTest(com.mtp.gui.AllTests.suite());
    suite.addTest(com.mtp.pounder.AllTests.suite());

    return suite;
  }

}
