package accpt;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

import com.mtp.pounder.Player;
import com.mtp.pounder.PounderFrame;
import com.mtp.pounder.PounderModel;
import com.mtp.pounder.PounderPrefs;
import com.mtp.pounder.ComponentConduit;
import com.mtp.pounder.RecordingRecord;
import com.mtp.pounder.MouseMotionItem;

import com.mtp.pounder.controller.PounderController;

import java.awt.Component;

import java.util.Enumeration;

public class TestFilterMouseMotionEvents extends TestCase {

	public TestFilterMouseMotionEvents(String name) {
		super(name);
	}

	public static Test suite() {
		return new TestSuite(TestFilterMouseMotionEvents.class);
	}

	/** No mouse motion events should exist in the script. **/
	public void testFilterMouseMotionEvents() throws Exception {
		Player player = PlayerFactory.buildSpeedy("pounder/accpt/TestFilterMouseMotionEvents.pnd");
		PounderInstance instance = (PounderInstance)player.play();

		RecordingRecord rr = instance.model.getRecord();
		Enumeration e = rr.elements();
		while(e.hasMoreElements()) {
			assertTrue(! (e.nextElement() instanceof MouseMotionItem));
		}
	}

}








