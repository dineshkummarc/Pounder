package com.mtp.pounder;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

import java.awt.event.MouseEvent;

import java.awt.AWTEvent;

import java.util.Vector;
import java.util.Iterator;

import javax.swing.JPanel;
import javax.swing.JFrame;

import com.mtp.gui.WindowWatcher;

public class VerbatimRecordingTest extends TestCase {

	protected WindowWatcher ww, ww2;
	protected Vector toBeTerminated;

	public VerbatimRecordingTest(String name) {
		super(name);
	}

	public static Test suite() {
		return new TestSuite(VerbatimRecordingTest.class);
	}

	public void setUp() throws Exception {
		super.setUp();
		ww = new WindowWatcher();
		ww2 = new WindowWatcher();
		toBeTerminated = new Vector();
	}

	public void tearDown() throws Exception {
		super.tearDown();
		ww.setFilterAllWindows(true);
		ww2.setFilterAllWindows(true);
		ww.disconnect(true);
    ww2.disconnect(true);
		ww = null;
    ww2 = null;
		Iterator i = toBeTerminated.iterator();
		while(i.hasNext()) {
			((VerbatimRecording)i.next()).terminate();
		}
	}

	protected JPanel buildTestablePanel() throws Exception {
		JPanel ret = new JPanel();
		JFrame f = new JFrame();
		f.getContentPane().add(ret);
		f.setVisible(true);
		ww.waitTillWindowPresent(f);
		return ret;
	}

  protected AWTEvent buildDummyEvent() throws Exception {
		return new MouseEvent(buildTestablePanel(), MouseEvent.MOUSE_PRESSED, 0, 0, 0, 0, 0, true);
  }

  public void testConstructor() throws Exception {
    VerbatimRecording vr = new VerbatimRecording(new PounderPrefs(), 0);
    assertFalse(vr.paused);
  }

  public void testSetPausedTrue() throws Exception {
    VerbatimRecording vr = new VerbatimRecording(new PounderPrefs(), 0);
    toBeTerminated.add(vr);
    vr.begin(new RecordingRecord(), ww);
    vr.setPaused(true);
    assertTrue(vr.paused);
  }

  public void testEventsIgnoredDuringPause() throws Exception {
    VerbatimRecording vr = new VerbatimRecording(new PounderPrefs(), 0);
		toBeTerminated.add(vr);

    RecordingRecord rr = new RecordingRecord();
    vr.begin(rr, ww2);
    vr.setPaused(true);
    vr.eventDispatched(buildDummyEvent());

    assertEquals(0, rr.getSize());
  }

  public void testSetPauseTrueDoesNotPreventAnEndEventFromBeingRecognized() throws Exception {
    PounderPrefs prefs = new PounderPrefs() {
        public EventDetector getEventDetector() {
          return new EventDetector() {
              public boolean isEndEvent(AWTEvent e) {
                return true;
              }
            };
        }
      };
    VerbatimRecording vr = new VerbatimRecording(prefs, 0);
		toBeTerminated.add(vr);

    RecordingRecord rr = new RecordingRecord();
    vr.begin(rr, ww2);
    vr.setPaused(true);
    vr.eventDispatched(buildDummyEvent());

    assertTrue(vr.finished);
  }

  public void testSetPauseTrueWindowWatcherFiltersAllWindows() throws Exception {
    VerbatimRecording vr = new VerbatimRecording(new PounderPrefs(), 0);
		toBeTerminated.add(vr);

    vr.begin(new RecordingRecord(), ww2);
    vr.setPaused(true);

    JFrame f = new JFrame();
    f.setVisible(true);
    ww.waitTillWindowPresent(f);

    assertTrue(ww2.getFilterAllWindows());
    assertEquals(0, ww2.getWindowCount());
  }

  public void testSetPauseTrueBeforeBeginCalled() throws Exception {
    VerbatimRecording vr = new VerbatimRecording(new PounderPrefs());
    vr.setPaused(true);
    vr.begin(new RecordingRecord(), ww);

    assertTrue(ww.getFilterAllWindows());
  }

	public void testSetPausedFalse() throws Exception {
		VerbatimRecording vr = new VerbatimRecording(new PounderPrefs(), 0);
    toBeTerminated.add(vr);
    vr.begin(new RecordingRecord(), ww);

    vr.setPaused(true);
    vr.lastTimeItemRecorded = 77;
    vr.setPaused(false);

    assertFalse(vr.paused);
    assertTrue(vr.lastTimeItemRecorded > 77);
  }

  public void testIsPaused() throws Exception {
		VerbatimRecording vr = new VerbatimRecording(new PounderPrefs(), 0);
    toBeTerminated.add(vr);
    vr.begin(new RecordingRecord(), ww);

    vr.setPaused(true);
    assertTrue(vr.paused);

    vr.setPaused(false);
    assertFalse(vr.paused);
  }

	public void testIgnoreUnnamedTrue() throws Exception {
		PounderPrefs prefs = new PounderPrefs();
		prefs.setIgnoreUnnamed(true);

		VerbatimRecording vr = new VerbatimRecording(prefs, 0);
		vr.filter = new DefaultFilter(ww);
		vr.record = new RecordingRecord();
		vr.windowWatcher = ww;
		toBeTerminated.add(vr);

		MouseEvent me = new MouseEvent(buildTestablePanel(), MouseEvent.MOUSE_PRESSED, 0, 0, 0, 0, 0, true);
		vr.eventDispatched(me);

		assertEquals(0, vr.record.getSize());
	}

	public void testIgnoreUnnamedFalse() throws Exception {
		PounderPrefs prefs = new PounderPrefs();
		prefs.setIgnoreUnnamed(false);

		VerbatimRecording vr = new VerbatimRecording(prefs, 0);
		toBeTerminated.add(vr);
		vr.begin(new RecordingRecord(), ww);

		JPanel p = buildTestablePanel();
		p.setName("Rudiger");

		MouseEvent me = new MouseEvent(p, MouseEvent.MOUSE_PRESSED, 0, 0, 0, 0, 0, true);
		vr.eventDispatched(me);

		assertTrue(vr.record.getSize() > 0);
	}

	public void testTerminateTwiceLeavesOnlyOneDummyRecording() throws Exception {
		VerbatimRecording vr = new VerbatimRecording(new PounderPrefs(), 0);
		toBeTerminated.add(vr);
		RecordingRecord record = new RecordingRecord();
		vr.begin(record, ww);
		vr.terminate();
		vr.terminate();

		int size = record.getSize();
		assertTrue(record.elementAt(size - 1) instanceof DummyRecordingItem);
		if(size > 1)
			assertTrue(! (record.elementAt(size - 1) instanceof DummyRecordingItem));
	}

	public void testCheckMouseReleaseEndsDrag() throws Exception {
		VerbatimRecording vr = new VerbatimRecording(new PounderPrefs(), 0);
		toBeTerminated.add(vr);
		vr.dragging = true;

		//dragging should NOT end on a button 2 release
		MouseEvent me = new MouseEvent(new JPanel(), MouseEvent.MOUSE_RELEASED, 0, 0, 0, 0, 0, false, MouseEvent.BUTTON2);
		vr.checkMouseReleaseEndsDrag(me);

		//dragging should end on a button 1 release
		me = new MouseEvent(new JPanel(), MouseEvent.MOUSE_RELEASED, 0, 0, 0, 0, 0, false, MouseEvent.BUTTON1);
		vr.checkMouseReleaseEndsDrag(me);
		assertTrue(! vr.dragging);
	}

	private boolean endDragCalled = false;
	public void testCheckIsDragEnd() throws Exception {
		VerbatimRecording vr = new VerbatimRecording(new PounderPrefs(), 0) {
				protected void endDrag(MouseEvent me, long delay) {
					endDragCalled = true;
				}
			};
		toBeTerminated.add(vr);

		//shouldn't be called on a mouse entered
		MouseEvent me = new MouseEvent(new JPanel(), MouseEvent.MOUSE_ENTERED, 0, 0, 0, 0, 0, false, MouseEvent.BUTTON1);
		vr.checkIsDragEnd(me, 0);
		assertTrue(! endDragCalled);

		//or a mouse exited
		me = new MouseEvent(new JPanel(), MouseEvent.MOUSE_EXITED, 0, 0, 0, 0, 0, false, MouseEvent.BUTTON2);
		vr.checkIsDragEnd(me, 0);
		assertTrue(! endDragCalled);

		//should be called on a mouse movement
		me = new MouseEvent(new JPanel(), MouseEvent.MOUSE_MOVED, 0, 0, 0, 0, 0, false, MouseEvent.BUTTON1);
		vr.checkIsDragEnd(me, 0);
		assertTrue(endDragCalled);
	}

	public void testTerminatesAfterMaxIdleTime() throws Exception {
		VerbatimRecording vr = new VerbatimRecording(new PounderPrefs(), 0);
		toBeTerminated.add(vr);
		vr.begin(new RecordingRecord(), ww);
		Thread.sleep(100);
		assertTrue(vr.isFinished());
	}

	public void testBeginInitializesFilter() throws Exception {
		VerbatimRecording vr = new VerbatimRecording(new PounderPrefs(), 0);
		toBeTerminated.add(vr);
		vr.begin(new RecordingRecord(), ww);
		assertNotNull(vr.filter);
	}

	public void testDoesNotTerminateBeforeMaxIdleTime() throws Exception {
		VerbatimRecording vr = new VerbatimRecording(new PounderPrefs(), 1000) {
				public void eventDispatched(AWTEvent e) {
					//ignore
				}
			};
		toBeTerminated.add(vr);
		vr.begin(new RecordingRecord(), ww);
		Thread.sleep(500);
		assertTrue(! vr.isFinished());
		Thread.sleep(600);
		assertTrue(vr.isFinished());
	}
		
	public void testAddMouseReleaseEventFiltersModfiers() throws Exception {
		VerbatimRecording vr = new VerbatimRecording(new PounderPrefs(), 100000);
		toBeTerminated.add(vr);
		vr.begin(new RecordingRecord(), ww);

		JPanel p = new JPanel();
		int modifiers = MouseEvent.BUTTON1_DOWN_MASK | 
			MouseEvent.BUTTON2_DOWN_MASK | 
			MouseEvent.BUTTON3_DOWN_MASK |
			MouseEvent.CTRL_DOWN_MASK;

		modifiers = vr.addMouseReleaseEvent(1, p, 0, modifiers, 0, 0, 0);
		//button 1 mask should be gone, all others present
		assertEquals(0, (modifiers & MouseEvent.BUTTON1_DOWN_MASK));
		assertEquals(MouseEvent.CTRL_DOWN_MASK, (modifiers & MouseEvent.CTRL_DOWN_MASK));
		assertEquals(MouseEvent.BUTTON2_DOWN_MASK, (modifiers & MouseEvent.BUTTON2_DOWN_MASK));
		assertEquals(MouseEvent.BUTTON3_DOWN_MASK, (modifiers & MouseEvent.BUTTON3_DOWN_MASK));

		modifiers = vr.addMouseReleaseEvent(2, p, 0, modifiers, 0, 0, 0);
		//buttons 1 and 2 masks should be gone, 3 and ctrl should be present
		assertEquals(0, (modifiers & MouseEvent.BUTTON1_DOWN_MASK));
		assertEquals(0, (modifiers & MouseEvent.BUTTON2_DOWN_MASK));
		assertEquals(MouseEvent.CTRL_DOWN_MASK, (modifiers & MouseEvent.CTRL_DOWN_MASK));
		assertEquals(MouseEvent.BUTTON3_DOWN_MASK, (modifiers & MouseEvent.BUTTON3_DOWN_MASK));

		modifiers = vr.addMouseReleaseEvent(3, p, 0, modifiers, 0, 0, 0);
		//only ctrl should be present
		assertEquals(0, (modifiers & MouseEvent.BUTTON1_DOWN_MASK));
		assertEquals(0, (modifiers & MouseEvent.BUTTON2_DOWN_MASK));
		assertEquals(0, (modifiers & MouseEvent.BUTTON3_DOWN_MASK));
		assertEquals(MouseEvent.CTRL_DOWN_MASK, (modifiers & MouseEvent.CTRL_DOWN_MASK));
	}

}






