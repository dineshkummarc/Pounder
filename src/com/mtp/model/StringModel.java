package com.mtp.model;

/**

Interface for modeling a String.

@author Matthew Pekar

**/

public interface StringModel {

	public static final String STRING_CHANGED = "String Changed";

	public void addListener(StringModelListener l);
	public void removeListener(StringModelListener l);
	public String getString();
	public void setString(String s);

}
