package com.mtp.pounder;

import junit.framework.Test;
import junit.framework.TestSuite;

public class PackageTests {
		
	public static Test suite() {
		TestSuite suite = new TestSuite("com.mtp.pounder tests");
		suite.addTest(RecordingThreadTest.suite());
		suite.addTest(PounderModelTest.suite());
		suite.addTest(KeyItemTest.suite());
		suite.addTest(MouseClickItemTest.suite());
		suite.addTest(MouseMotionItemTest.suite());
		suite.addTest(TestInstanceFactoryTest.suite());
		suite.addTest(PounderPrefsTest.suite());
		suite.addTest(EventDetectorTest.suite());
		suite.addTest(RecordingRecordTest.suite());
		suite.addTest(PounderReaderWriterTest.suite());
		suite.addTest(DefaultFilterTest.suite());
		suite.addTest(VerbatimRecordingTest.suite());
		suite.addTest(PlayerTest.suite());
		suite.addTest(PlaybackThreadTest.suite());
		suite.addTest(DynamicClassLoaderTest.suite());
		suite.addTest(ComponentIdentifierFactoryTest.suite());
		suite.addTest(NameIdentifierTest.suite());
		suite.addTest(KeyIdentifierTest.suite());
		suite.addTest(RecordingRecordListTest.suite());
		suite.addTest(MouseWheelItemTest.suite());
		suite.addTest(VerbatimRecordingOptionsTest.suite());
		suite.addTest(InputMethodItemTest.suite());
		suite.addTest(PauseButtonTest.suite());

		return suite;
	}
		
}
