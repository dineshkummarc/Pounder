package accpt;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

import java.awt.Component;

import com.mtp.pounder.Player;
import com.mtp.pounder.PounderModel;
import com.mtp.pounder.PounderFrame;
import com.mtp.pounder.RecordingItem;
import com.mtp.pounder.ComponentConduit;

import com.mtp.pounder.assrt.TextEqualsItem;

public class TestPauseDuringPlayback extends AcceptanceTest {

	public TestPauseDuringPlayback(String name) {
		super(name);
	}

	public static Test suite() {
		return new TestSuite(TestPauseDuringPlayback.class);
	}

	public void testAssertionAdded() throws Exception {
		Player player = PlayerFactory.buildSpeedy("pounder/accpt/TestPauseDuringPlayback.pnd");
    player.setDisposeWindows(false);
		PounderInstance instance = (PounderInstance)player.play();

    assertTrue(instance.model.isPaused());
	}

}






