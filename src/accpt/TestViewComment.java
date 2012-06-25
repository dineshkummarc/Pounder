package accpt;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

import com.mtp.pounder.Player;

public class TestViewComment extends TestCase {

	public TestViewComment(String name) {
		super(name);
	}

	public static Test suite() {
		return new TestSuite(TestViewComment.class);
	}

	public void setUp() throws Exception {
		super.setUp();
	}

	public void tearDown() throws Exception {
		super.tearDown();
	}

	/** Just play the script.  It will call view->comment, and check if
	 * the window appears. **/
	public void testViewComment() throws Exception {
		Player player = PlayerFactory.buildSpeedy("pounder/accpt/TestViewComment.pnd");
		player.play();
	}

}








