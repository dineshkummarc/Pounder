package accpt;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

import com.mtp.pounder.Player;

import javax.swing.JPanel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.awt.Dimension;

public class TestDoubleClick extends TestCase {

	public TestDoubleClick(String name) {
		super(name);
	}

	public static Test suite() {
		return new TestSuite(TestDoubleClick.class);
	}

	public void setUp() throws Exception {
		super.setUp();
	}

	public void tearDown() throws Exception {
		super.tearDown();
	}

	public static class DoubleClickPanel extends JPanel {
		public int doubleClicks = 0;

		public DoubleClickPanel() {
			addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						if(e.getClickCount() == 2)
							doubleClicks += 1;
					}
				});
			setPreferredSize(new Dimension(400, 400));
			setSize(getPreferredSize());
		}
	}

	/** Double click should work. **/
	public void testCannotEditWhenRecording() throws Exception {
		Player player = PlayerFactory.buildSpeedy("pounder/accpt/TestDoubleClick.pnd");
		DoubleClickPanel dcp = (DoubleClickPanel)player.play();
		
		assertEquals(1, dcp.doubleClicks);
	}

}






