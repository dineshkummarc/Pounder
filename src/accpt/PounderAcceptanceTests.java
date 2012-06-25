package accpt;

import junit.framework.Test;
import junit.framework.TestSuite;

public class PounderAcceptanceTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Pounder Acceptance Tests");
		suite.addTest(TestPauseDuringRecording.suite());
		suite.addTest(TestPauseDuringPlayback.suite());
		suite.addTest(TestMultipleDialogs.suite());
		suite.addTest(TestConduitThatReturnsNull.suite());
		suite.addTest(TestDoubleClick.suite());
		suite.addTest(TestRecordWindowsWithChangingLayouts.suite());
		suite.addTest(TestWindowsClosed.suite());
		suite.addTest(TestExitUnsaved.suite());
		suite.addTest(TestStopShortcutForPlayback.suite());
		suite.addTest(TestFilterMouseMotionEvents.suite());
		suite.addTest(TestAssertWindowShowingByID.suite());
		suite.addTest(TestAssertWindowShowingByTitle.suite());
		suite.addTest(TestFrameTitleUpdatesOnSave.suite());
		suite.addTest(TestScrollDetected.suite());
		suite.addTest(TestPlay.suite());
		suite.addTest(TestRecord.suite());
		suite.addTest(TestWheelEvents.suite());
		suite.addTest(TestOpenSave.suite());
		suite.addTest(TestViewComment.suite());
		suite.addTest(TestViewError.suite());
		suite.addTest(TestTextEqualsByWindowID.suite());
		suite.addTest(TestTextEqualsByWindowTitle.suite());
		suite.addTest(TestDragAndDrop.suite());

		return suite;
	}
		
}
