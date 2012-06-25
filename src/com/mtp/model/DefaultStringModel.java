package com.mtp.model;

import java.util.Iterator;

/**

Models a String.

@author Matthew Pekar

**/
public class DefaultStringModel implements StringModel {

	protected String string;
	protected Listeners listeners;

	public DefaultStringModel() {
		this("");
	}

	public DefaultStringModel(String s) {
		this.string = s;
		this.listeners = new Listeners();
	}

	public String getString() {
		return string;
	}

	public void setString(String s) {
		this.string = s;
		fireChange();
	}
    
	public void addListener(StringModelListener l) {
		listeners.add(l);
	}

	public void removeListener(StringModelListener l) {
		listeners.remove(l);
	}

	protected void fireChange() {
		Iterator i = listeners.getFiringIterator();
		while(i.hasNext()) {
			StringModelListener l = (StringModelListener)i.next();
			l.stringModelChanged(this);
		}
	}

}
