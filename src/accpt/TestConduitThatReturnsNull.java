package accpt;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

import com.mtp.pounder.Player;
import com.mtp.pounder.ComponentConduit;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;

public class TestConduitThatReturnsNull extends TestCase {

	public TestConduitThatReturnsNull(String name) {
		super(name);
	}

	public static Test suite() {
		return new TestSuite(TestConduitThatReturnsNull.class);
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
			f.setVisible(true);
			while(! f.isVisible()) {
				Thread.sleep(50);
			}

			f.setSize(new Dimension(400, 400));

			return null;
		}
	}

	/** Should be one window no problem. **/
	public void testScrollDetected() throws Exception {
		Player p = PlayerFactory.buildDefault("pounder/accpt/TestConduitThatReturnsNull.pnd");
		Conduit c = (Conduit)p.play();
		assertEquals("Bar", c.list.getSelectedValue());
	}

}






