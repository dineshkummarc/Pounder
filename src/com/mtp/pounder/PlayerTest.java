package com.mtp.pounder;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

import javax.swing.JFrame;

import java.awt.Component;
import java.awt.Panel;
import java.awt.Window;

import org.w3c.dom.Node;

import com.mtp.gui.WindowWatcher;

public class PlayerTest extends TestCase {

	public PlayerTest(String name) {
		super(name);
	}

	public static Test suite() {
		return new TestSuite(PlayerTest.class);
	}

	protected WindowWatcher windowWatcher;

	public void setUp() throws Exception {
		super.setUp();
		windowWatcher = new WindowWatcher();
	}

	public void tearDown() throws Exception {
		super.tearDown();
		windowWatcher.disconnect(true);
	}

	public static class ReturnNullConduit implements ComponentConduit {
		public Component getComponent() {
			return null;
		}
	}

	private boolean waitTillWindowPresentCalled;
	public void testBuildTestObjectWithNullReturnConduitDoesNotCallWaitTillWindowPresent() throws Exception {
		PounderModel pm = new PounderModel();
		pm.setTestClass("com.mtp.pounder.PlayerTest$ReturnNullConduit");

		waitTillWindowPresentCalled = false;
		Player p = new Player(pm) {
				protected WindowWatcher buildWindowWatcher() {
					WindowWatcher ret = new WindowWatcher() {
							public void waitTillWindowPresent(Window w) {
								waitTillWindowPresentCalled = true;
							}
						};
					ret.setRemoveWindows(false);
					return ret;
				}
			};
		p.play();

		assertTrue(! waitTillWindowPresentCalled);
	}

  public void testConstructor() throws Exception {
    Player p = new Player(new PounderModel());
    assertFalse(p.paused);
    assertNotNull(windowWatcher);
  }

	public void testBuildWindowWatcher() throws Exception {
		PounderModel pm = new PounderModel();
		pm.setTestClass("javax.swing.JButton");

		Player p = new Player(pm);
		assertTrue(! p.buildWindowWatcher().getRemoveWindows());
	}

  private boolean sleepWhilePausedCalled;
  public void testSetPausedTrue() throws Throwable {
    PounderModel pm = new PounderModel();
		pm.setTestClass("javax.swing.JButton");
		Player p = new Player(pm) {
        protected void sleepWhilePaused() {
          sleepWhilePausedCalled = true;
        }
      };

    p.setPaused(true);
    sleepWhilePausedCalled = false;
    p.playItem(new DummyRecordingItem());

    assertTrue(p.paused);
    assertTrue(sleepWhilePausedCalled);
  }

  public void testSetPausedTrueWindowWatcherFiltersAllWindows() throws Exception {
    PounderModel pm = new PounderModel();
		pm.setTestClass("javax.swing.JButton");
		Player p = new Player(pm);
    p.setPaused(true);

    assertTrue(p.windowWatcher.getFilterAllWindows());
  }

  public void testSetPausedFalse() throws Exception {
    PounderModel pm = new PounderModel();
		pm.setTestClass("javax.swing.JButton");
		Player p = new Player(pm);
    p.setPaused(true);
    p.setPaused(false);

    assertFalse(p.paused);
  }

	public void testSetItemDelayEnabled() throws Exception {
		PounderModel pm = new PounderModel();
		pm.setTestClass("javax.swing.JButton");
		for(int i=0;i < 5;i++) {
			pm.getRecord().addItem(new DummyRecordingItem(1000));
		}

		Player player = new Player(pm);
		player.setItemDelayEnabled(false);
		long startTime = System.currentTimeMillis();
		player.play();

		//dunno how long the play() takes, but should be less than the sum of the items
		assertTrue(System.currentTimeMillis() - startTime < 5000);
	}

	protected JFrame testFrame;

	public void testSetDisposeWindowsToFalse() throws Exception {
		PounderModel pm = new PounderModel();
		pm.setTestClass("javax.swing.JButton");

		testFrame = new JFrame();
		testFrame.setVisible(false);

		RecordingRecord record = pm.getRecord();
		record.addItem(new DummyRecordingItem() {
				public void playback(WindowWatcher ww, PounderPrefs prefs) {
					testFrame.setVisible(true);
				}
			});

		Player player = new Player(pm);
		player.setDisposeWindows(false);
		player.play();

		boolean visible = testFrame.isVisible();
		testFrame.dispose();
		assertTrue(visible);
	}

  public void testPlayClearsMemberWindowWatcher() throws Exception {
    PounderModel pm = new PounderModel();
		pm.setTestClass("javax.swing.JButton");
    Player player = new Player(pm);
		player.play();

    assertNull(player.windowWatcher);
  }

	public void testPlayItemPlaysOnce() throws Exception {
		PounderModel pm = new PounderModel();
		pm.setTestClass("javax.swing.JButton");
		DummyRecordingItem item = new DummyRecordingItem();
		pm.getRecord().addItem(item);

		Player player = new Player(pm);
		player.play();

		assertEquals(1, item.playCount);
	}

	public void testPlayItemThatThrowsPlaybackExceptionGetsThrownOutOfPlay() throws Exception {
		PounderModel pm = new PounderModel();
		pm.setTestClass("javax.swing.JButton");

		RecordingRecord record = pm.getRecord();
		MisbehavingItem mi = new MisbehavingItem() {
				public void playback(WindowWatcher ww, PounderPrefs prefs) throws Exception {
					playCount += 1;
					throw new PlaybackException("Catch me!");
				}
			};
		record.addItem(mi);

		Player player = new Player(pm);
		player.setPlaybackAttempts(3);
		player.setFailedPlaybackDelay(0);

		boolean caught = false;
		try {
			player.play();
		}
		catch(PlaybackException exc) {
			caught = true;
		}

		assertTrue(caught);
		assertEquals(1, mi.playCount);
	}

	public void testMisbehavingComponentPlayedPlaybackAttempts() throws Exception {
		PounderModel pm = new PounderModel();
		pm.setTestClass("javax.swing.JButton");

		RecordingRecord record = pm.getRecord();
		MisbehavingItem mi = new MisbehavingItem();
		record.addItem(mi);

		Player player = new Player(pm);
		player.setPlaybackAttempts(3);
		player.setFailedPlaybackDelay(0);
		try {
			player.play();
		}
		catch(PlaybackException exc) {
		}

		assertEquals(3, mi.playCount);
	}

	public void testSimplePlayback() throws Exception {
		PounderModel pm = new PounderModel();
		pm.setTestClass("javax.swing.JButton");

		RecordingRecord record = pm.getRecord();
		CheckPlayedItem cpi = new CheckPlayedItem(0);
		record.addItem(cpi);

		Player player = new Player(pm);
		player.play();

		assertTrue(cpi.played);
	}

	public void testSetStopRequested() throws Exception {
		PounderModel pm = new PounderModel();
		pm.setTestClass("javax.swing.JButton");
		final Player player = new Player(pm);

		RecordingRecord record = pm.getRecord();
		CheckPlayedItem cpi = new CheckPlayedItem(0);
		record.addItem(cpi);

		RecordingItem doStopItem = new DummyRecordingItem() {
				public void playback(WindowWatcher ww, PounderPrefs prefs) {
					player.setStopRequested(true);
				}
			};
		record.addItem(doStopItem);

		CheckPlayedItem cpi2 = new CheckPlayedItem(0);
		record.addItem(cpi2);

		player.play();

		assertTrue(cpi.played);
		assertTrue(! cpi2.played);
	}

}






