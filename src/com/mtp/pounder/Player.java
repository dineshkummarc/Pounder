package com.mtp.pounder;

import com.mtp.gui.WindowWatcher;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.Enumeration;

import java.awt.Window;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.EventQueue;
import java.awt.KeyboardFocusManager;
import java.awt.KeyEventDispatcher;

import java.awt.event.KeyEvent;

import javax.swing.SwingUtilities;
import javax.swing.BoundedRangeModel;
import javax.swing.DefaultBoundedRangeModel;

import java.net.URL;

/**

Plays a Pounder script.  Has several options for controlling the
playback.

@author Matthew Pekar

@see com.mtp.gui.WindowWatcher
@see com.mtp.pounder.PounderPrefs

**/
public class Player implements PounderConstants, KeyEventDispatcher {

	/** Preferences to determine how the playback operates. **/
	protected PounderPrefs prefs;
	/** ClassLoader used to instantiate test objects. **/
	protected ClassLoader classLoader;
	/** TestInstanceFactory used to manage test object. **/
	protected TestInstanceFactory testInstanceFactory;
	/** Record of items to play. **/
	protected RecordingRecord record;
	/** Whether a stop has been requested. **/
	protected volatile boolean stopRequested;
	/** Whether to dispose windows when playback has ended. **/
	protected boolean disposeWindows;
  /** Whether the playback is paused. **/
  protected volatile boolean paused;
  protected WindowWatcher windowWatcher;

	/** Construct with given PounderModel.  Use our ClassLoader to
	 * load test object. **/
	public Player(PounderModel pm) {
		this(pm, pm.getClass().getClassLoader());
	}

	/** Construct with given PounderModel.  Use given ClassLoader to
	 * load test object. **/
	public Player(PounderModel pm, ClassLoader cl) {
		this(pm.getPreferences(), pm.getTestInstanceFactory(), pm.getRecord(), cl);
	}

	/** Construct with data from given script.  Use our ClassLoader to
	 * load test object. **/
	public Player(String script) throws Exception {
		this(script, null);
	}

	/** Construct with data from given script.  Use our ClassLoader to
	 * load test object. **/
	public Player(URL script) throws Exception {
		this(script, null);
	}

	/** Construct with data from given script.  Use given ClassLoader
	 * to load test object. **/
	public Player(URL script, ClassLoader cl) throws Exception {
		this(script.openStream(), cl);
	}

	String itemsWriterName;
	
	/** Construct with data from script stored in given File.  Use
	 * given ClassLoader to load test object. **/
	public Player(String scriptFile, ClassLoader cl) throws Exception {
		
		this(new FileInputStream(new File(scriptFile)), cl);
		itemsWriterName = scriptFile;
	}

	/** Construct with data from script stored on given InputStream.
	 * Use given ClassLoader to load test object, or Class ClassLoader
	 * if null is passed. **/
	public Player(InputStream script, ClassLoader cl) throws Exception {
		cl = (cl == null) ? getClass().getClassLoader() : cl;
		PounderReader.PounderData data = new PounderReader().read(script);
		PounderPrefs prefs = new PounderPrefs();
		TestInstanceFactory tif = data.testInstanceFactory;
		RecordingRecord rr = new RecordingRecord(data.items);
		init(prefs, tif, rr, cl);
	}

	/** Use given preferences to configure playback.  Use given
	 * TestInstanceFactory to manage the test object.  Play items in
	 * given RecordingRecord.  Use given ClassLoader to load test
	 * object.  **/
	public Player(PounderPrefs prefs, TestInstanceFactory tif, RecordingRecord record, ClassLoader cl) {
		init(prefs, tif, record, cl);
	}

	/** Initializer member varaiables. **/
	protected void init(PounderPrefs prefs, TestInstanceFactory tif, RecordingRecord record, ClassLoader cl) {
		this.prefs = prefs;
		this.testInstanceFactory = tif;
		this.record = record;
		this.classLoader = cl;
		this.stopRequested = false;
		this.disposeWindows = true;
    this.paused = false;
    this.windowWatcher = buildWindowWatcher();
	}

	/** Shortcut that calls setItemDelayEnabled() on my
	 * PounderPrefs. **/
	public void setItemDelayEnabled(boolean b) {
		prefs.setItemDelayEnabled(b);
	}

	/** Shortcut that calls setPlaybackAttempts() on my
	 * PounderPrefs. **/
	public void setPlaybackAttempts(int count) {
		prefs.setPlaybackAttempts(count);
	}

	/** Shortcut that calls setFailedPlaybackDelay() on my
	 * PounderPrefs. **/
	public void setFailedPlaybackDelay(long d) {
		prefs.setFailedPlaybackDelay(d);
	}

	/** Set whether to dispose of windows at end of playback. **/
	public void setDisposeWindows(boolean b) {
		this.disposeWindows = b;
	}

	/** If true, will stop as soon as possible. **/
	public void setStopRequested(boolean b) {
		this.stopRequested = b;
	}

	/** Return the total delay for our RecordingItem's. **/
	protected int getTotalDelay() {
		int ret = 0;

		Enumeration e = record.elements();
		while(e.hasMoreElements()) {
			RecordingItem item = (RecordingItem)e.nextElement();
			ret += item.getDelay();
		}

		return ret;
	}

	/** Build Object used for playback. **/
	protected Object buildTestObject() throws PlaybackException {
		try {
			return testInstanceFactory.showNewTestInstance(classLoader, prefs, windowWatcher);
		}
		catch(Exception exc) {
			throw new PlaybackException(exc.getMessage(), exc);
		}
	}

	/** Play and return created test object. **/
	public Object play() throws PlaybackException {
		return play(new DefaultBoundedRangeModel());
	}

	/** Make sure the system event queue is empty before
	 * returning. **/
	protected void clearEventQueue() {
		EventQueue eq = Toolkit.getDefaultToolkit().getSystemEventQueue();
		while(eq.peekEvent() != null) {
			try {
				Thread.sleep(10);
			}
			catch(Exception exc) {
			}
		}
	}

	/** Add ourselves a keyboard listener for the stop
	 * shortcut. **/
	protected void addStopShortcutListener() {
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this);
	}

	/** Remvoe ourselves a keyboard listener for the stop
	 * shortcut. **/
	protected void removeStopShortcutListener() {
		KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(this);
	}

	/** Inherited from KeyEventDispatcher.  We check if it's the stop
	 * shorcut. **/
	public boolean dispatchKeyEvent(KeyEvent e) {
		if(prefs.getEventDetector().isEndEvent(e))
			setStopRequested(true);
		return false;
	}

	/** Delay for given amount, but do it fine grained; checking if
	 * stop is requested along the way. **/
	protected void doDelayWhileCheckingForStop(long amount) {
		long time = System.currentTimeMillis();
		long destTime = time + amount;
		while(time < destTime) {
			try {
        Thread.sleep(10);
      }
      catch(InterruptedException exc) {
      }

			if(stopRequested)
				return;
			time = System.currentTimeMillis();
		}
	}

	/** Return a new WindowWatcher for playback. **/
	protected WindowWatcher buildWindowWatcher() {
		WindowWatcher ret = new WindowWatcher();
		ret.setRemoveWindows(false);
		return ret;
	}

  protected void initRangeModel(BoundedRangeModel rm) {
		rm.setMinimum(0);
		rm.setMaximum(getTotalDelay());
		rm.setValue(0);
		rm.setExtent(0);
  }

  public void setPaused(boolean b) {
    paused = b;
    windowWatcher.setFilterAllWindows(b);
  }

  public boolean isPaused() {
    return paused;
  }

	/** Playback the script and update the given range model.  Return
	 * created test object. **/
  	ItemsWriter iw;
  
	public Object play(BoundedRangeModel rm) throws PlaybackException {
		addStopShortcutListener();
    initRangeModel(rm);

		Object ret = buildTestObject();

		Enumeration e = record.elements();
		RecordingItem item = null;
		int index = 0;
		iw = new ItemsWriter(itemsWriterName + ".replay");
		while(e.hasMoreElements()) {
			if(checkStopRequested(rm))
				break;

			item = (RecordingItem)e.nextElement();
			

			if(prefs.getItemDelayEnabled())
				doDelayWhileCheckingForStop(item.getDelay());

			if(checkStopRequested(rm))
				break;

			try {
				clearEventQueue();
				playItem(item, prefs.getPlaybackAttempts());
				rm.setValue(rm.getValue() + (int)item.getDelay());
			}
			catch(Throwable t) {
				record.selectItem(index);
				playbackEnded(rm);
				throw new PlaybackException(t.getMessage(), t);
			}

			index += 1;
		}

		playbackEnded(rm);
		try {
			iw.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return ret;
	}

	/** Return true if stop was requested.  If stop requested, perform
	 * necessary cleanup before returning. **/
	protected boolean checkStopRequested(BoundedRangeModel rm) {
		if(stopRequested) {
			rm.setValue(0);
			windowWatcher.setFilterAllWindows(true);
		}

		return stopRequested;
	}

	/** Function called when playback has ended.  Destroys
	 * TestInstanceFactory and disconnects WindowWatcher.  Clears given
	 * BoundedRangeModel. **/
	protected void playbackEnded(BoundedRangeModel rm) {
		removeStopShortcutListener();
		windowWatcher.disconnect(disposeWindows);
    windowWatcher = null;

		rm.setMinimum(0);
		rm.setMaximum(0);
		rm.setValue(0);
		rm.setExtent(0);
	}

  protected void sleepWhilePaused() {
    while(paused) {
				try {
					Thread.sleep(50);
				}
				catch(InterruptedException exc) {
				}
    }
  }
  
	/** Calls playback on the given item with the given WindowWatcher.
   * Will pause if needed. **/
  protected void playItem(RecordingItem item) throws Throwable {
    sleepWhilePaused();
    /*
     * 
     */
    //System.out.println("replaying " + item.toString());
    
    item.playback(windowWatcher, prefs);
    iw.writeItem(item, true);
  }

	/** Calls playback on the given item.  Will attempt to play
	 * multiple times if errors occur. 

   @arg attempts The number of times to attempt playback if failures occur.
  **/
	protected void playItem(RecordingItem item, int attempts) throws Throwable {
		long failedPlaybackDelay = prefs.getFailedPlaybackDelay();
		for(int i=0;i < (attempts - 1);i++) {
      try {
        playItem(item);
        return;
      }
      catch(PlaybackException exc) {
    	  iw.writeItem(item, false);
				throw exc;
			}
			catch(Throwable t) {
				try {
					Thread.sleep(failedPlaybackDelay);
				}
				catch(InterruptedException exc) {
				}
			}
    }

    playItem(item);
  }

}
