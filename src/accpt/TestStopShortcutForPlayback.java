package accpt;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

import com.mtp.pounder.Player;
import com.mtp.pounder.PounderFrame;

import com.mtp.gui.WindowWatcher;

public class TestStopShortcutForPlayback extends TestCase {

	protected WindowWatcher windowWatcher;

	public TestStopShortcutForPlayback(String name) {
		super(name);
	}

	public static Test suite() {
		return new TestSuite(TestStopShortcutForPlayback.class);
	}

	public void setUp() throws Exception {
		super.setUp();
		windowWatcher = new WindowWatcher();
	}

	public void tearDown() throws Exception {
		super.tearDown();
		windowWatcher.disconnect(true);
	}

	/** After stop shortcut is pressed, playback should be
	 * stopped. **/
	public void testStopShortcutForPlayback() throws Exception {
		Player player = PlayerFactory.buildDefault("pounder/accpt/TestStopShortcutForPlayback.pnd");
		player.setDisposeWindows(false);
		PounderInstance instance = (PounderInstance)player.play();

		assertTrue(! instance.frame.getModel().isPlaying());
	}

}
