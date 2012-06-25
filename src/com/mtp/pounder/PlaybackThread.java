package com.mtp.pounder;

import java.awt.Window;

import com.mtp.i18n.Strings;

/**

Responsible for playing back a RecordingRecord.

@author Matthew Pekar

**/
public class PlaybackThread extends Thread {

	protected PounderModel model;
	protected ClassLoader classLoader;
	protected Player player;

	public PlaybackThread(PounderModel model, ClassLoader cl) {
		this.model = model;
		this.classLoader = cl;
		this.player = buildPlayer(model, classLoader);
	}

	protected Player buildPlayer(PounderModel model, ClassLoader cl) {
		Player ret = new Player(model, classLoader);
		PounderPrefs prefs = model.getPreferences();
		ret.setItemDelayEnabled(prefs.getItemDelayEnabled());
		ret.setPlaybackAttempts(prefs.getPlaybackAttempts());
		ret.setFailedPlaybackDelay(prefs.getFailedPlaybackDelay());
		return ret;
	}

	/** Set whether thread should stop when possible. **/
	public void setStopRequested(boolean b) {
		this.player.setStopRequested(b);
	}

  public void setPaused(boolean b) {
    this.player.setPaused(b);
  }

  public boolean isPaused() {
    return this.player.isPaused();
  }

	public void run() {
		try {
			model.getStatusModel().setStatus(Strings.getString("BeginPlayback"));
			this.player.play(model.getProgressModel());
			model.getStatusModel().setStatus(Strings.getString("EndPlayback"));
		}
		catch(Throwable t) {
			model.getStatusModel().handleException(t);
		}

		model.playbackFinished();
	}

}
