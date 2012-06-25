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

import com.mtp.pounder.assrt.WindowShowingItem;

import com.mtp.pounder.controller.PounderController;

public class TestAssertWindowShowingByID extends TestCase {

	public TestAssertWindowShowingByID(String name) {
		super(name);
	}

	public static Test suite() {
		return new TestSuite(TestAssertWindowShowingByID.class);
	}

	public void setUp() throws Exception {
		super.setUp();
	}

	public void tearDown() throws Exception {
		super.tearDown();
	}

	/** Must use a default player because mouse motions don't catch too well
	 * on popups. **/
	public void testWindowIDAssertionAdded() throws Exception {
		Player player = PlayerFactory.buildDefault("pounder/accpt/TestAssertWindowShowingByID.pnd");
		PounderInstance instance = (PounderInstance)player.play();
		RecordingItem ri = (RecordingItem)instance.model.getRecord().elementAt(0);
		WindowShowingItem wsi = (WindowShowingItem)ri;

		assertEquals(666, wsi.getWindowID());
	}

}






