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

public class TestTextEqualsByWindowTitle extends TestCase {

	public TestTextEqualsByWindowTitle(String name) {
		super(name);
	}

	public static Test suite() {
		return new TestSuite(TestTextEqualsByWindowTitle.class);
	}

	public void setUp() throws Exception {
		super.setUp();
	}

	public void tearDown() throws Exception {
		super.tearDown();
	}

	/** Must use a default player because mouse motions don't catch too well
	 * on popups. **/
	public void testAssertionAdded() throws Exception {
		Player player = PlayerFactory.buildDefault("pounder/accpt/TestTextEqualsByWindowTitle.pnd");
		PounderInstance instance = (PounderInstance)player.play();
		RecordingItem ri = (RecordingItem)instance.model.getRecord().elementAt(0);
		TextEqualsItem tei = (TextEqualsItem)ri;

		assertEquals(new TextEqualsItem("Indiana", "Gary", "Stinkpit"), tei);
	}

}






