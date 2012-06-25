package com.mtp.model;

import java.util.Iterator;

/**

Represents an integer and alerts listeners of changes.

@author Matthew Pekar

**/
public interface IntegerModel {
		
	public int getValue();
	public void setValue(int val);
	public void addListener(IntegerModelListener l);
	public void removeListener(IntegerModelListener l);
		
}
