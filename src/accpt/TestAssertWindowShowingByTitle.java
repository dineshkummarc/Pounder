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

public class TestAssertWindowShowingByTitle extends TestCase {

    public TestAssertWindowShowingByTitle(String name) {
				super(name);
    }

    public static Test suite() {
				return new TestSuite(TestAssertWindowShowingByTitle.class);
    }

		public void setUp() throws Exception {
				super.setUp();
		}

		public void tearDown() throws Exception {
				super.tearDown();
		}

		/** Must use a default player because mouse motions don't catch too well
		 * on popups. **/
		public void testWindowTitleAssertionAdded() throws Exception {
				Player player = PlayerFactory.buildDefault("pounder/accpt/TestAssertWindowShowingByTitle.pnd");
				PounderInstance instance = (PounderInstance)player.play();
				RecordingItem ri = (RecordingItem)instance.model.getRecord().elementAt(0);
				WindowShowingItem wsi = (WindowShowingItem)ri;

				assertEquals("Star Wars", wsi.getWindowTitle());
		}

}






