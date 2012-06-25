package accpt;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

import com.mtp.pounder.Player;
import com.mtp.pounder.ComponentConduit;

import java.awt.Component;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;

public class TestScrollDetected extends TestCase {

  public TestScrollDetected(String name) {
    super(name);
  }

  public static Test suite() {
    return new TestSuite(TestScrollDetected.class);
  }

  public static class ScrollConduit implements ComponentConduit {
				
    public JList list;

    public ScrollConduit() {
      DefaultListModel items = new DefaultListModel();
      for(int i=0;i < 50;i++)
        items.addElement("Foo");
      items.addElement("Bar");
      list = new JList(items);
    }

    public Component getComponent() {
      return new JScrollPane(list);
    }
  }

  /** Play the script.  Last item in the list should be selected. **/
  public void testScrollDetected() throws Exception {
    Player p = PlayerFactory.buildDefault("pounder/accpt/TestScrollDetected - scroll to bottom.pnd");
    ScrollConduit c = (ScrollConduit)p.play();
    assertEquals("Bar", c.list.getSelectedValue());
  }

}






