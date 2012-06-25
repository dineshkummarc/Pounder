package accpt;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

import java.awt.Component;

import java.io.File;

import com.mtp.pounder.Player;
import com.mtp.pounder.PounderModel;
import com.mtp.pounder.PounderPrefs;
import com.mtp.pounder.PounderFrame;
import com.mtp.pounder.ComponentConduit;

public class TestFrameTitleUpdatesOnSave extends TestCase {

	public TestFrameTitleUpdatesOnSave(String name) {
		super(name);
	}

	public static Test suite() {
		return new TestSuite(TestFrameTitleUpdatesOnSave.class);
	}

	public void setUp() throws Exception {
		super.setUp();
	}

	public void tearDown() throws Exception {
		super.tearDown();
	}

	public static class Conduit implements ComponentConduit {
		public PounderInstance instance;

		public Conduit() throws Exception {
			instance = new PounderInstance();
			PounderPrefs prefs = instance.model.getPreferences();
			File temp = File.createTempFile("Foo", "Bar");
			temp.deleteOnExit();
			prefs.setHomeDirectory(temp.getParentFile());
		}

		public Component getComponent() {
			return instance.frame;
		}
	}

	public void testTitleMatchesFileName() throws Exception {
		Player player = PlayerFactory.buildSpeedy("pounder/accpt/TestFrameTitleUpdatesOnSave.pnd");
		Conduit c = (Conduit)player.play();

		assertEquals("theFile.pnd", c.instance.frame.getTitle());
	}

}






