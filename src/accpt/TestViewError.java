package accpt;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

import com.mtp.pounder.Player;

public class TestViewError extends TestCase {

	public TestViewError(String name) {
		super(name);
	}

	public static Test suite() {
		return new TestSuite(TestViewError.class);
	}

	public void setUp() throws Exception {
		super.setUp();
	}

	public void tearDown() throws Exception {
		super.tearDown();
	}

	/** Just play the script.  It will create an instance of a class
	 * that does not exist, click on the red error message, and assert
	 * that the dialog is showing. **/
	public void testViewComment() throws Exception {
		Player player = PlayerFactory.buildSpeedy("pounder/accpt/TestViewError.pnd");
		player.play();
	}

}








