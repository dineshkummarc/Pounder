package accpt;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

import com.mtp.pounder.Player;
import com.mtp.pounder.PounderConstants;

import com.mtp.gui.WindowWatcher;

public class TestWindowsClosed extends TestCase implements PounderConstants {

		protected WindowWatcher windowWatcher;

    public TestWindowsClosed(String name) {
				super(name);
    }

    public static Test suite() {
				return new TestSuite(TestWindowsClosed.class);
    }

		public void setUp() throws Exception {
				super.setUp();
				windowWatcher = new WindowWatcher();
		}

		public void tearDown() throws Exception {
				super.tearDown();
				windowWatcher.disconnect(true);
		}

		/** After this script is played, no windows should exist. **/
		public void testWindowsClosed() throws Exception {
				Player p = PlayerFactory.buildSpeedy("pounder/accpt/TestWindowsClosed - 1.pnd");
				p.play();
				assertEquals(0, windowWatcher.getWindowCount());
		}

}






