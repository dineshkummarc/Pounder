package accpt;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

import com.mtp.pounder.Player;
import com.mtp.pounder.ComponentConduit;

import java.awt.*;

import java.awt.event.*;

import javax.swing.*;

public class TestWindowAlreadyVisible extends TestCase {

	public TestWindowAlreadyVisible(String name) {
		super(name);
	}

	public static Test suite() {
		return new TestSuite(TestWindowAlreadyVisible.class);
	}

	public void setUp() throws Exception {
		super.setUp();
	}

	public void tearDown() throws Exception {
		super.tearDown();
	}

	public static class Conduit implements ComponentConduit {

		public JList list;

		public Conduit() {
		}
				
		public Component getComponent() throws Exception {
			JFrame f = new JFrame();
			f.setTitle("Bluh");
			Object[] items = {"Foo", "Bar"};
			list = new JList(items);
			f.getContentPane().add(list);
			f.setSize(new Dimension(400, 400));
			f.setVisible(true);

			return f;
		}
	}
	
	public void testWindowRemovesAndAddsComponents() throws Exception {
		Player player = PlayerFactory.buildSpeedy("pounder/accpt/TestWindowAlreadyVisible.pnd");
		Conduit c = (Conduit)player.play();
		assertEquals("Bar", c.list.getSelectedValue());
	}

}






