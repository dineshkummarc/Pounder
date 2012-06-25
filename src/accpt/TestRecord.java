package accpt;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

import com.mtp.pounder.Player;

public class TestRecord extends TestCase {

	public TestRecord(String name) {
		super(name);
	}

	public static Test suite() {
		return new TestSuite(TestRecord.class);
	}

	public void setUp() throws Exception {
		super.setUp();
	}

	public void tearDown() throws Exception {
		super.tearDown();
	}

	/** Test whether a warning is displayed when the test class is
	 * switched when a non-empty script is present. **/
	public void testWarningOnClassSwitch() throws Exception {
		Player player = PlayerFactory.buildSpeedy("pounder/accpt/TestRecord - test warning on class switch.pnd");
		player.play();
	}

}








