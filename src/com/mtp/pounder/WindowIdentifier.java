package com.mtp.pounder;

import java.awt.Component;
import java.awt.Window;

/**

When an event is detected in a Window, it should just use itself as
the Component.

@author Matthew Pekar

**/
public class WindowIdentifier implements ComponentIdentifier {

	/** Instantiate a new instance from String that was given from
	 * asString().  Although I can't make it required, it is! **/
	public static ComponentIdentifier instantiate(String s) {
		return new WindowIdentifier();
	}
		
	/** Get Component we identify from given Window. **/
	public Component getComponent(Window w) {
		return w;
	}

	public String asString() {
		return "com.mtp.pounder.WindowIdentifier:";
	}

	public String toString() {
		return asString();
	}

	public boolean equals(Object o) {
		if(o == null)
			return false;
		return (o instanceof WindowIdentifier);
	}

}

