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

public class TestPauseDuringRecording extends AcceptanceTest {

	public TestPauseDuringRecording(String name) {
		super(name);
	}

	public static Test suite() {
		return new TestSuite(TestPauseDuringRecording.class);
	}

	public void testPauseDuringRecording() throws Exception {
		Player player = PlayerFactory.buildSpeedy("pounder/accpt/TestPauseDuringRecording.pnd");
    player.setDisposeWindows(false);
		PounderInstance instance = (PounderInstance)player.play();

    assertTrue(instance.model.isPaused());
	}

}






