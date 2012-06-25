package com.mtp.gui;

import java.awt.Window;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Point;

/**

Useful utilities for Window's.

@author Matthew Pekar

**/
public class WindowUtilities {

	public static void center(Window w) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		center(w, new Rectangle(0, 0, screenSize.width, screenSize.height));
	}

	/** Center the given Window in the given bounds. **/
	public static void center(Window w, Rectangle bounds) {
		Dimension windowSize = w.getSize();
		if((windowSize.getWidth() > bounds.width) || (windowSize.getHeight() > bounds.height)) {
			w.setLocation(0, 0);
			return;
		}
	
		int widthOffset = (int)((bounds.width - windowSize.width) / 2);
		int heightOffset = (int)((bounds.height - windowSize.height) / 2);
		w.setLocation(new Point(bounds.x + widthOffset, bounds.y + heightOffset));
	}

}
