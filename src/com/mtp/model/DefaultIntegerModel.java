package com.mtp.model;

import java.util.Iterator;

/**

Default implementation of an IntegerModel that just stores an 'int'.

@author Matthew Pekar

**/
public class DefaultIntegerModel implements IntegerModel {
		
	protected int value;
	protected Listeners listeners;

	public DefaultIntegerModel() {
		this(0);
	}

	public DefaultIntegerModel(int val) {
		this.value = val;
		this.listeners = new Listeners();
	}

	public boolean equals(Object o) {
		if(o == null)
			return false;

		if(! (o instanceof DefaultIntegerModel))
			return false;

		DefaultIntegerModel dim = (DefaultIntegerModel)o;
		return value == dim.value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int val) {
		this.value = val;
		fireChange();
	}

	public void addListener(IntegerModelListener l) {
		listeners.add(l);
	}

	public void removeListener(IntegerModelListener l) {
		listeners.remove(l);
	}

	public void fireChange() {
		Iterator i = listeners.getFiringIterator();
		while(i.hasNext()) {
			IntegerModelListener l = (IntegerModelListener)i.next();
			l.integerModelChanged(this);
		}
	}
		
}
