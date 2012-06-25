package com.mtp.pounder;

import java.awt.AWTEvent;

/**

Interface for filtering events.

@author Matthew Pekar

**/
public interface Filter {
	public boolean accepts(AWTEvent e);
}
