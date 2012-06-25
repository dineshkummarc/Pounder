package com.mtp.model;

import javax.swing.text.PlainDocument;
import javax.swing.text.BadLocationException;

import java.util.Iterator;

/**

Models a String using a PlainDocument.

@author Matthew Pekar

**/
public class DocumentStringModel extends PlainDocument implements StringModel {

	protected Listeners listeners;

	public DocumentStringModel() {
		this("");
	}

	public DocumentStringModel(String s) {
		this.listeners = new Listeners();

		setString(s);
	}

	public String getString() {
		try {
			return getText(0, getLength());
		}
		catch(BadLocationException exc) {
			return null;
		}
	}

	public void setString(String s) {
		try {
			remove(0, getLength());
			insertString(0, s, null);
			fireChange();
		}
		catch(BadLocationException exc) {
		}
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
