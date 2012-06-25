package accpt;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

import java.awt.Component;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.DefaultListModel;
import javax.swing.ListSelectionModel;
import javax.swing.TransferHandler;

import com.mtp.pounder.Player;
import com.mtp.pounder.ComponentConduit;

public class TestDragAndDrop extends TestCase {

    public TestDragAndDrop(String name) {
				super(name);
    }

    public static Test suite() {
				return new TestSuite(TestDragAndDrop.class);
    }

		public void setUp() throws Exception {
				super.setUp();
		}

		public void tearDown() throws Exception {
				super.tearDown();
		}

		public static class DraggableListConduit implements ComponentConduit {

				public DefaultListModel model = new DefaultListModel();
				public JTextArea text = new JTextArea();
				
				public DraggableListConduit() {
						text.setName("TextArea");
				}

				public Component getComponent() {
						DefaultListModel model = new DefaultListModel();
						model.addElement("Lop");
						model.addElement("Tar");
						model.addElement("Da");
						model.addElement("Boo");
						JList list = new JList(model);
						list.setName("StringList");
						list.setDragEnabled(true);

						JPanel ret = new JPanel();
						ret.setLayout(new java.awt.GridLayout());
						ret.add(new JScrollPane(list));
						ret.add(new JScrollPane(text));
						return ret;
				}

		}

		/** Drag all items to the text area at the right. Should be one
		 * nice big string. **/
		public void testDragInComponent() throws Exception {
				Player player = PlayerFactory.buildDefault("pounder/accpt/TestDragAndDrop - test drag all.pnd");
				DraggableListConduit c = (DraggableListConduit)player.play();

				assertEquals("LopTarDaBoo", c.text.getText());
		}

}








