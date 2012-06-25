package com.mtp.pounder;
	
import java.awt.Frame;

import com.mtp.gui.WindowWatcher;
		
/**
			 
Interface for recording events and playing them back later.

@author Matthew Pekar
			 
**/
public interface Recording {
			
	/** Begin recording on given Frame and add to the given
	 * RecordingRecord. 

	 @arg ww The WindowWatcher used to track Window's in the recording. 
	**/
	public void begin(RecordingRecord record, WindowWatcher ww);

  public void setPaused(boolean b);

  public boolean isPaused();
																	
	public boolean isFinished();
																		
	public void terminate();
																			
}
