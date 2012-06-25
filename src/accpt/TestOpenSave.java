package accpt;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

import com.mtp.pounder.ComponentConduit;
import com.mtp.pounder.Player;
import com.mtp.pounder.PounderModel;
import com.mtp.pounder.PounderPrefs;
import com.mtp.pounder.PounderFrame;

import com.mtp.pounder.controller.PounderController;

import com.mtp.gui.WindowWatcher;

import java.nio.channels.FileChannel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.awt.Component;

import javax.swing.filechooser.FileSystemView;

public class TestOpenSave extends TestCase {

	protected String userHome;

	public TestOpenSave(String name) {
		super(name);
	}

	public static Test suite() {
		return new TestSuite(TestOpenSave.class);
	}

	public void setUp() throws Exception {
		super.setUp();
	}

	public void tearDown() throws Exception {
		super.tearDown();
	}

	/** Moves the given file to the temp directory for opening or
	 * saving.  Then set the prefs to point to the temp directory. **/
	public static class OpenSaveConduit implements ComponentConduit {

		public PounderInstance instance;

		public OpenSaveConduit(File file) throws Exception {
			instance = new PounderInstance();

			File tempFile = File.createTempFile("FOO", "BAR");
			File tempDir = tempFile.getParentFile();
			tempFile.delete();
			File dest = new File(tempDir, file.getName());
			dest.deleteOnExit();

			copyFile(file, dest);		

			PounderPrefs prefs = instance.model.getPreferences();
			prefs.setHomeDirectory(tempDir);
		}

		public Component getComponent() {
			return instance.frame;
		}

		protected void copyFile(File src, File dest) throws Exception {
			FileChannel srcChannel = new FileInputStream(src).getChannel();
			FileChannel destChannel = new FileOutputStream(dest).getChannel();

			long transferred = destChannel.transferFrom(srcChannel, 0, srcChannel.size());
			if(transferred != srcChannel.size())
				throw new Exception("Could not transfer entire file");
				
			srcChannel.close();
			destChannel.close();
		}

	}

	public static class TestOpenConduit extends OpenSaveConduit {

		public TestOpenConduit() throws Exception {
			super(new File("testFiles/simpleButtonClick.pnd"));
		}

	}

	/** After this script is played, the class combo box should have
	 * text: "javax.swing.JButton". **/
	public void testOpen() throws Exception {
		Player player = PlayerFactory.buildSpeedy("pounder/accpt/TestOpenSave - 1.pnd");
		TestOpenConduit c = (TestOpenConduit)player.play();
		assertEquals("javax.swing.JButton", c.instance.frame.getPounderPanel().getSetupPanel().getTestClassComboBox().getSelectedItem());
	}

}






