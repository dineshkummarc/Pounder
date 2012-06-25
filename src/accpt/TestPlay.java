package accpt;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

import com.mtp.pounder.Player;

import com.mtp.gui.WindowWatcher;

public class TestPlay extends TestCase {

	protected WindowWatcher windowWatcher;

	public TestPlay(String name) {
		super(name);
	}

	public static Test suite() {
		return new TestSuite(TestPlay.class);
	}

	public void setUp() throws Exception {
		super.setUp();
		windowWatcher = new WindowWatcher();
	}

	public void tearDown() throws Exception {
		super.tearDown();
		windowWatcher.disconnect(true);
	}


	/** Test frame should be dispose()'d of after script finished. **/
	public void testTestWindowDisposedOnEmptyScriptPlay() throws Exception {
		Player player = PlayerFactory.buildSpeedy("pounder/accpt/TestPlay - play empty record.pnd");
		player.setDisposeWindows(false);
		player.play();

		assertEquals(1, windowWatcher.getWindowCount());
	}

}








