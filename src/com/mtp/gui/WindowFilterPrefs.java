package com.mtp.gui;

/**

Structure keeping track of filtering preferences for a Window.

@author Matthew Pekar

**/
public class WindowFilterPrefs {

	protected boolean filterChildren;

	/** Default is NOT to filter children. **/
	public WindowFilterPrefs() {
		this(false);
	}

	public WindowFilterPrefs(boolean filterChildren) {
		this.filterChildren = filterChildren;				
	}

	public boolean getFilterChildren() {
		return filterChildren;
	}
}
