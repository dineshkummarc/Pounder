package accpt;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

import javax.swing.JButton;
import javax.swing.JPopupMenu;

import java.awt.Component;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.mtp.pounder.Player;
import com.mtp.pounder.ComponentConduit;

import com.mtp.gui.WindowWatcher;

public class TestMultipleDialogs extends TestCase {

	public TestMultipleDialogs(String name) {
		super(name);
	}

	public static Test suite() {
		return new TestSuite(TestMultipleDialogs.class);
	}

	public void setUp() throws Exception {
		super.setUp();
	}

	public void tearDown() throws Exception {
		super.tearDown();
	}

	public static class Conduit implements ComponentConduit {
		public Conduit() {
		}
		
		public Component getComponent() {
			JButton ret = new JButton("Maktar");
			ret.addMouseListener(new MouseAdapter() {
					protected JPopupMenu popup;

					{
						popup = new JPopupMenu();
						popup.add("Foo");
						popup.add("Bar");
						popup.add("Lop");
						popup.add("Tar");
						popup.add("Da");
						popup.add("Boo");
					}

					public void mouseClicked(MouseEvent e) {
						checkMouseEvent(e);
					}

					public void mousePressed(MouseEvent e) {
						checkMouseEvent(e);
					}

					public void mouseReleased(MouseEvent e) {
						checkMouseEvent(e);
					}

					protected void checkMouseEvent(MouseEvent e) {
						if(e.isPopupTrigger())
							popup.show(e.getComponent(), e.getX(), e.getY());
					}
				});
			return ret;
		}
	}

	/** Test whether bringing up more than one dialog plays back
	 * properly.  This is used to detect WindowWatcher inconsistencies
	 * between playing and recording. **/
	public void testMultipleDialogs() throws Exception {
		Player player = PlayerFactory.buildSpeedy("pounder/accpt/TestMultipleDialogs.pnd");
		player.play();
	}

}








