package com.mtp.pounder;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

import javax.swing.JFrame;

import java.awt.Panel;
import java.awt.Window;
import java.awt.Component;

import org.w3c.dom.Node;

import com.mtp.gui.WindowWatcher;

public class RecordingThreadTest extends TestCase {

	public RecordingThreadTest(String name) {
		super(name);
	}

	public static Test suite() {
		return new TestSuite(RecordingThreadTest.class);
	}

	WindowWatcher windowWatcher;

	public void setUp() throws Exception {
		windowWatcher = new WindowWatcher();
	}

	public void tearDown() throws Exception {
		super.tearDown();
		windowWatcher.disconnect(true);
	}

	protected static class DummyRecording implements Recording {
				
		public boolean terminateCalled;
		public boolean finished;
		public boolean began = false;
		public boolean paused;
				
		public DummyRecording() {
			this.finished = true;
			this.terminateCalled = false;
      this.paused = false;
		}

		public void begin(RecordingRecord record, WindowWatcher ww) {
			began = true;
		}

		public Node toXML() {
			return null;
		}

    public void setPaused(boolean b) {
      paused = b;
    }

    public boolean isPaused() {
      return paused;
    }

		public boolean isFinished() {
			return finished;
		}

		public void terminate() {
			terminateCalled = true;
		}
	}

	public void testConstructor() throws Exception {
		PounderModel pm = new PounderModel();
		DummyRecording dr = new DummyRecording();
		RecordingThread t = new RecordingThread(pm, dr);
		assertEquals(pm, t.model);
		assertEquals(dr, t.recording);
		assertNotNull(t.windowWatcher);
	}

	public void testInitWindowWatcher() throws Exception {
		PounderModel pm = new PounderModel();
		DummyRecording dr = new DummyRecording();
		RecordingThread t = new RecordingThread(pm, dr);

		t.initWindowWatcher();
		assertNotNull(t.windowWatcher);
		assertTrue(! t.windowWatcher.getRemoveWindows());
	}

	public static class ReturnNullConduit implements ComponentConduit {
		public Component getComponent() {
			return null;
		}
	}

	public void testClearsWindowsOnExit() throws Exception {
		PounderModel pm = new PounderModel();
		pm.setTestClass("javax.swing.JButton");
		RecordingThread t = new RecordingThread(pm, new DummyRecording());
		t.start();
		t.join();

		assertEquals(0, windowWatcher.getWindowCount());
	}

	public void testTerminates() throws Exception {
		PounderModel pm = new PounderModel();
		pm.setTestClass("javax.swing.JButton");
		RecordingThread t = new RecordingThread(pm, new DummyRecording());
		t.start();

		boolean joined = false;
		try {
			t.join();
			joined = true;
		}
		catch(InterruptedException e) {
		}

		assertTrue(joined);
	}

	public void testTerminatesCallsTerminateOnRecording() throws Exception {
		PounderModel pm = new PounderModel();
		pm.setTestClass("javax.swing.JButton");
		DummyRecording recording = new DummyRecording();
		RecordingThread t = new RecordingThread(pm, recording);
		t.start();

		t.join();

		assertTrue(recording.terminateCalled);
	}

  protected boolean setPausedValue;
  public void testSetPausedTrue() throws Exception {
		PounderModel pm = new PounderModel();
		pm.setTestClass("javax.swing.JButton");
		DummyRecording dr = new DummyRecording() {
        public void setPaused(boolean b) {
          setPausedValue = b;
        }
      };

		RecordingThread t = new RecordingThread(pm, dr);

    setPausedValue = false;
    t.setPaused(true);
    assertTrue(setPausedValue);
  }

  public void testSetPausedFalse() throws Exception {
		PounderModel pm = new PounderModel();
		pm.setTestClass("javax.swing.JButton");
		DummyRecording dr = new DummyRecording() {
        public void setPaused(boolean b) {
          setPausedValue = b;
        }
      };

		RecordingThread t = new RecordingThread(pm, dr);

    setPausedValue = true;
    t.setPaused(false);
    assertFalse(setPausedValue);
  }

	public void testSetStopRequested() throws Exception {
		PounderModel pm = new PounderModel();
		pm.setTestClass("javax.swing.JButton");
		DummyRecording dr = new DummyRecording();
		dr.finished = false;
		RecordingThread t = new RecordingThread(pm, dr);

		t.start();

		while(! dr.began) {
			Thread.yield();
		}

		t.setStopRequested(true);
		t.join();
	}

}






