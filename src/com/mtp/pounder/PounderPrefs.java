package com.mtp.pounder;

import java.util.Iterator;

import java.util.prefs.Preferences;

import java.io.File;

import com.mtp.model.Listeners;
import com.mtp.model.PointModel;

/**

The preferences class.

@author Matthew Pekar

**/
public class PounderPrefs {
		
	public static final int DISPLAY_SCRIPT_CHANGED = 1;
	public static final int PLAYBACK_OPTIONS_CHANGED = 2;

	public static boolean DEFAULT_FAST_PLAYBACK_ENABLED = false;
	public static boolean DEFAULT_ITEM_DELAY_ENABLED = true;
	public static boolean DEFAULT_IGNORE_UNNAMED = false;
	public static boolean DEFAULT_USE_SYSTEM_CLASS_LOADER = false;
	public static boolean DEFAULT_DISPLAY_SCRIPT = true;
	public static boolean DEFAULT_SAVE_PREFS_ON_EXIT = true;
	public static int DEFAULT_PLAYBACK_ATTEMPTS = 3;
	public static int DEFAULT_FAILED_PLAYBACK_DELAY = 500;


	protected PointModel defaultTestWindowLocation;
	protected EventDetector eventDetector;
	protected VerbatimRecordingOptions verbatimRecordingOptions;
	protected volatile Listeners listeners;
	protected volatile boolean displayScript;
	/** Whether to save the model's PounderPrefs on exit. **/
	protected volatile boolean savePrefsOnExit;
	/** Whether to use the system class loader instead of a DynamicClassLoader. **/
	protected volatile boolean useSystemClassLoader;
	/** The directory where files should first be searched from. **/
	protected File homeDirectory;

	/** Variables dealing with playback. **/
	protected volatile boolean fastPlaybackEnabled;
	protected volatile boolean itemDelayEnabled;
	protected volatile boolean ignoreUnnamed;
	protected volatile int playbackAttempts;
	protected volatile long failedPlaybackDelay;

	public PounderPrefs() {
		this.defaultTestWindowLocation = new PointModel(200, 200);
		this.eventDetector = new EventDetector();
		this.verbatimRecordingOptions = new VerbatimRecordingOptions();
		this.listeners = new Listeners(); 
		this.displayScript = DEFAULT_DISPLAY_SCRIPT;
		this.savePrefsOnExit = DEFAULT_SAVE_PREFS_ON_EXIT;
		this.useSystemClassLoader = DEFAULT_USE_SYSTEM_CLASS_LOADER;
		this.homeDirectory = null;
		this.fastPlaybackEnabled = DEFAULT_FAST_PLAYBACK_ENABLED;
		this.itemDelayEnabled = DEFAULT_ITEM_DELAY_ENABLED;
		this.ignoreUnnamed = DEFAULT_IGNORE_UNNAMED;
		this.playbackAttempts = DEFAULT_PLAYBACK_ATTEMPTS;
		this.failedPlaybackDelay = DEFAULT_FAILED_PLAYBACK_DELAY;
	}

	public PounderPrefs(PounderPrefs pp) {
		this.listeners = new Listeners(); 
		retrieveData(pp);
	}

	/** Retrieve data from preferences stored on system using the
	 * Preferences for our class and return self. **/
	public PounderPrefs retrieveDataFromSystem() {
		Preferences p = Preferences.userNodeForPackage(getClass());
		defaultTestWindowLocation = new PointModel();
		defaultTestWindowLocation.setX(p.getInt("defaultTestWindowLocation.x", 200));
		defaultTestWindowLocation.setY(p.getInt("defaultTestWindowLocation.y", 200));
		displayScript = p.getBoolean("displayScript", DEFAULT_DISPLAY_SCRIPT);
		useSystemClassLoader = p.getBoolean("useSystemClassLoader", DEFAULT_USE_SYSTEM_CLASS_LOADER);
		savePrefsOnExit = p.getBoolean("savePrefsOnExit", DEFAULT_SAVE_PREFS_ON_EXIT);
		fastPlaybackEnabled = p.getBoolean("fastPlaybackEnabled", DEFAULT_FAST_PLAYBACK_ENABLED);
		itemDelayEnabled = p.getBoolean("itemDelayEnabled", DEFAULT_ITEM_DELAY_ENABLED);
		ignoreUnnamed = p.getBoolean("ignoreUnnamed", DEFAULT_IGNORE_UNNAMED);
		playbackAttempts = p.getInt("playbackAttempts", DEFAULT_PLAYBACK_ATTEMPTS);
		failedPlaybackDelay = p.getLong("failedPlaybackDelay", DEFAULT_FAILED_PLAYBACK_DELAY);
		String filename = p.get("homeDirectory", null);
		homeDirectory = filename == null || filename.equals("") ? null : new File(filename);

		verbatimRecordingOptions.retrieveDataFromSystem();

		return this;
	}

	/** Store our data to the system using the Preferences for our
	 * class. **/
	public void saveDataToSystem() {
		Preferences p = Preferences.userNodeForPackage(getClass());
		p.putInt("defaultTestWindowLocation.x", defaultTestWindowLocation.getX());
		p.putInt("defaultTestWindowLocation.y", defaultTestWindowLocation.getY());
		p.putBoolean("displayScript", displayScript);
		p.putBoolean("useSystemClassLoader", useSystemClassLoader);
		p.putBoolean("savePrefsOnExit", savePrefsOnExit);
		p.putBoolean("fastPlaybackEnabled", fastPlaybackEnabled);
		p.putBoolean("itemDelayEnabled", itemDelayEnabled);
		p.putBoolean("ignoreUnnamed", ignoreUnnamed);
		p.putInt("playbackAttempts", playbackAttempts);
		p.putLong("failedPlaybackDelay", failedPlaybackDelay);
		p.put("homeDirectory", homeDirectory == null ? "" : homeDirectory.getPath());

		verbatimRecordingOptions.saveDataToSystem();
	}

	/** Update our data to be the same as the given PounderPrefs. **/
	public void retrieveData(PounderPrefs p) {
		this.defaultTestWindowLocation = new PointModel();
		this.defaultTestWindowLocation.setX(p.defaultTestWindowLocation.getX());
		this.defaultTestWindowLocation.setY(p.defaultTestWindowLocation.getY());
		this.eventDetector = (EventDetector)p.eventDetector.clone();
		this.verbatimRecordingOptions = (VerbatimRecordingOptions)p.verbatimRecordingOptions.clone();
		this.homeDirectory = p.homeDirectory == null ? null : new File(p.homeDirectory.getPath());
		this.savePrefsOnExit = p.savePrefsOnExit;
		this.fastPlaybackEnabled = p.fastPlaybackEnabled;
		this.displayScript = p.displayScript;
		this.itemDelayEnabled = p.itemDelayEnabled;
		this.ignoreUnnamed = p.ignoreUnnamed;
		this.playbackAttempts = p.playbackAttempts;
		this.failedPlaybackDelay = p.failedPlaybackDelay;
		this.failedPlaybackDelay = p.failedPlaybackDelay;

		fireChanged(DISPLAY_SCRIPT_CHANGED);
		fireChanged(PLAYBACK_OPTIONS_CHANGED);
	}

	public boolean getIgnoreUnnamed() {
		return ignoreUnnamed;
	}

	public void setIgnoreUnnamed(boolean whether) {
		this.ignoreUnnamed = whether;
	}

	public void setHomeDirectory(File f) {
		this.homeDirectory = f;
	}

	public File getHomeDirectory() {
		return this.homeDirectory;
	}

	public boolean getUseSystemClassLoader() {
		return this.useSystemClassLoader;
	}

	public void setSavePrefsOnExit(boolean whether) {
		this.savePrefsOnExit = whether;
	}

	public boolean getSavePrefsOnExit() {
		return savePrefsOnExit;
	}

	public void setUseSystemClassLoader(boolean whether) {
		this.useSystemClassLoader = whether;
	}

	public boolean getFastPlaybackEnabled() {
		return this.fastPlaybackEnabled;
	}

	/** Set sensible values for a fast playback if desired. **/
	public void setFastPlaybackEnabled(boolean whether) {
		this.fastPlaybackEnabled = whether;

		if(whether)
			setPlaybackOptions(false, 100, 50);
		else
			setPlaybackOptions(DEFAULT_ITEM_DELAY_ENABLED, DEFAULT_PLAYBACK_ATTEMPTS, DEFAULT_FAILED_PLAYBACK_DELAY);
	}

	public void setPlaybackOptions(boolean itemDelayEnabled, int playbackAttempts, long failedPlaybackDelay) {
		this.itemDelayEnabled = itemDelayEnabled;
		this.playbackAttempts = playbackAttempts;
		this.failedPlaybackDelay = failedPlaybackDelay;
		fireChanged(PLAYBACK_OPTIONS_CHANGED);
	}

	/** Set whether item delay is enabled. **/
	public void setItemDelayEnabled(boolean b) {
		this.itemDelayEnabled = b;
		fireChanged(PLAYBACK_OPTIONS_CHANGED);
	}

	/** Set the number of attempts to playback a failing item before
	 * halting the playback session. **/
	public void setPlaybackAttempts(int count) {
		this.playbackAttempts = count;
		fireChanged(PLAYBACK_OPTIONS_CHANGED);
	}

	/** Set the value (in milliseconds) for delay after a playback
	 * failure. **/
	public void setFailedPlaybackDelay(long amount) {
		this.failedPlaybackDelay = amount;
		fireChanged(PLAYBACK_OPTIONS_CHANGED);
	}

	public int getPlaybackAttempts() {
		return this.playbackAttempts;
	}

	public long getFailedPlaybackDelay() {
		return failedPlaybackDelay;
	}

	public boolean getItemDelayEnabled() {
		return this.itemDelayEnabled;
	}

	public boolean getDisplayScript() {
		return this.displayScript;
	}

	public void setDisplayScript(boolean b) {
		this.displayScript = b;
		fireChanged(DISPLAY_SCRIPT_CHANGED);
	}

	public void addListener(PounderPrefsListener l) {
		listeners.add(l);
	}

	public void removeListener(PounderPrefsListener l) {
		listeners.remove(l);
	}

	protected void fireChanged(int what) {
		Iterator i = listeners.getFiringIterator();
		while(i.hasNext()) {
			PounderPrefsListener l = (PounderPrefsListener)i.next();
			l.pounderPrefsChanged(this, what);
		}
	}

	public EventDetector getEventDetector() {
		return eventDetector;
	}

	public synchronized PointModel getDefaultTestWindowLocation() {
		return defaultTestWindowLocation;
	} 

	public VerbatimRecordingOptions getVerbatimRecordingOptions() {
		return verbatimRecordingOptions;
	}

	/** Actually just updates current values, rather than changing the
	 * pointer. **/
	public void setVerbatimRecordingOptions(VerbatimRecordingOptions vro) {
		this.verbatimRecordingOptions.retrieveData(vro);
	}

	public boolean equals(Object o) {
		PounderPrefs p = (PounderPrefs)o;

		if(homeDirectory == null) {
			if(p.homeDirectory != null)
				return false;
		}
		else if(! homeDirectory.equals(p.homeDirectory))
			return false;

		return eventDetector.equals(p.eventDetector) && 
			defaultTestWindowLocation.equals(p.defaultTestWindowLocation) &&
			verbatimRecordingOptions.equals(p.verbatimRecordingOptions) &&
			displayScript == p.displayScript &&
			savePrefsOnExit == p.savePrefsOnExit &&
			useSystemClassLoader == p.useSystemClassLoader &&
			itemDelayEnabled == p.itemDelayEnabled &&
			ignoreUnnamed == p.ignoreUnnamed &&
			playbackAttempts == p.playbackAttempts &&
			failedPlaybackDelay == p.failedPlaybackDelay;
	}

	public String toString() {
		return "PounderPrefs. defaultTestWindowLocation: " + defaultTestWindowLocation.getX() + "," + defaultTestWindowLocation.getY() + ", homeDirectory: " + homeDirectory;
	}

}
