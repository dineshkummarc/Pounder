package com.mtp.pounder;

/**

Exception that occurs during playback.  Caller should stop attempting
to play item.

@author Matthew Pekar

**/
public class PlaybackException extends Exception {

	public PlaybackException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public PlaybackException(String msg) {
		super(msg);
	}

}
