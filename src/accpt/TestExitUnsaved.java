package accpt;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

import com.mtp.pounder.Player;

import com.mtp.gui.WindowWatcher;

public class TestExitUnsaved extends TestCase {

		protected WindowWatcher windowWatcher;

    public TestExitUnsaved(String name) {
				super(name);
    }

    public static Test suite() {
				return new TestSuite(TestExitUnsaved.class);
    }

		public void setUp() throws Exception {
				super.setUp();
				windowWatcher = new WindowWatcher();
		}

		public void tearDown() throws Exception {
				super.tearDown();
				windowWatcher.disconnect(true);
		}

		/** Test whether a confirm dialog is displayed when a script is
		 * modified and dispose is called.  Not guaranteed due to a
		 * sleep. **/
		public void testExitUnsaved() throws Exception {
				Player player = PlayerFactory.buildSpeedy("pounder/accpt/TestExitUnsaved - record and exit.pnd");
				player.setDisposeWindows(false);
				player.play();
				Thread.sleep(500); //sleep so dialog has a chance to appear

				assertEquals(2, windowWatcher.getWindowCount()); //1 PounderFrame, 1 confirm dialog
		}

}








