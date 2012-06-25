package com.mtp.model;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import java.lang.ref.WeakReference;

/**

Nice utility class for everything which must be listenable.  Does the
WeakReference bit for you.

Allows adds/removes during iteration.

@author Matthew Pekar

**/
public class Listeners {

	protected Collection listeners;

	public Listeners() {
		listeners = buildCollection();
	}

	protected Collection buildCollection() {
		return new Vector();
	}

	/** Return true if given listener is stored. **/
	public synchronized boolean contains(Object o) {
		Iterator i = listeners.iterator();
		while(i.hasNext()) {
			WeakReference ref = (WeakReference)i.next();
			Object theObj = ref.get();
			if(theObj == null)
				i.remove();
			if(theObj == o)
				return true;
		}

		return false;
	}

	/** Remove dead references. **/
	protected void removeDead() {
		Iterator i = listeners.iterator();
		while(i.hasNext()) {
			WeakReference ref = (WeakReference)i.next();
			if(ref.get() == null)
				i.remove();
		}
	}

	public synchronized int getSize() {
		removeDead();
		return listeners.size();
	}

	public synchronized void add(Object o) {
		listeners.add(new WeakReference(o));
	}

	/** Remove the given listener and any dead references. **/
	public synchronized void remove(Object o) {
		Iterator i = listeners.iterator();
		while(i.hasNext()) {
			WeakReference ref = (WeakReference)i.next();
			Object theObj = ref.get();
			if((theObj == o) || (theObj == null))
				i.remove();
		}
		listeners.remove(o);
	}

	/** Returns an Iterator that can be used to call the appropriate
	 * method on each listener. **/
	public synchronized Iterator getFiringIterator() {
		Collection temp = buildCollection();
		Iterator i = listeners.iterator();
		while(i.hasNext()) {
			WeakReference ref = (WeakReference)i.next();
			Object theObj = ref.get();
			if(theObj == null)
				i.remove();
			else
				temp.add(theObj);
		}
		return temp.iterator();
	}

}
