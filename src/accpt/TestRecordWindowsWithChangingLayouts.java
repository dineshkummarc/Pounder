package accpt;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

import com.mtp.pounder.Player;

import java.awt.*;

import java.awt.event.*;

import javax.swing.*;

public class TestRecordWindowsWithChangingLayouts extends TestCase {

	public TestRecordWindowsWithChangingLayouts(String name) {
		super(name);
	}

	public static Test suite() {
		return new TestSuite(TestRecordWindowsWithChangingLayouts.class);
	}

	public void setUp() throws Exception {
		super.setUp();
	}

	public void tearDown() throws Exception {
		super.tearDown();
	}

	public void testWindowRemovesAndAddsComponents() throws Exception {
		Player player = PlayerFactory.buildSpeedy("pounder/accpt/TestRecordWindowsWithChangingLayouts - two clicks.pnd");
		DynamicComponent dc = (DynamicComponent)player.play();
		assertEquals(2, dc.timesClicked);
	}

}






