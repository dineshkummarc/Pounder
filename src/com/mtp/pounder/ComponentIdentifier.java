package com.mtp.pounder;

import java.awt.Component;
import java.awt.Window;

/**

Interface for finding a Component in a Window.

Subclasses <b>must</b> have a constructor

equals() should be correctly implemented.

@author Matthew Pekar

**/
public interface ComponentIdentifier {

	/** Instantiate a new instance from String that was given from
	 * asString().  Although I can't make it required, it is! **/
	//		public static ComponentIdentifier instantiate(String s);
		
	/** Get Component we identify from given Window. **/
	public Component getComponent(Window w);

	/** Should return String of form
	 * <full_classname>:<attributes>. **/
	public String asString();

}

