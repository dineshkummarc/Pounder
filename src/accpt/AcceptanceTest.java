package accpt;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

import com.mtp.gui.WindowWatcher;

public abstract class AcceptanceTest extends TestCase {

  protected WindowWatcher windowWatcher;

  public AcceptanceTest(String name) {
    super(name);
  }

  public void setUp() throws Exception {
    super.setUp();
    windowWatcher = new WindowWatcher();
  }

  public void tearDown() throws Exception {
    super.tearDown();
    windowWatcher.disconnect(true);
  }

}
