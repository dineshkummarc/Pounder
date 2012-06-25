package com.mtp.pounder;

import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;

import java.util.Enumeration;
import java.util.Collection;
import java.util.Iterator;

import org.w3c.dom.Node;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**

A record of all the recordin' that's been going down.  Allows
filtering, playback, and other useful operations.

@author Matthew Pekar

**/
public class RecordingRecord extends DefaultListModel {

	protected DefaultListSelectionModel selectionModel;

	public RecordingRecord() {
		init();
	}

	public RecordingRecord(Collection items) {
		addItems(items);
		init();
	}

	protected void init() {
		this.selectionModel = new DefaultListSelectionModel();
		this.selectionModel.setSelectionMode(DefaultListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	}

	public DefaultListSelectionModel getSelectionModel() {
		return selectionModel;
	}

	public boolean equals(Object o) {
		RecordingRecord r = (RecordingRecord)o;
		if(r == null)
			return false;

		if(size() != r.size())
			return false;

		Enumeration e = elements();
		Enumeration e2 = r.elements();
		while(e.hasMoreElements()) {
			if(! e.nextElement().equals(e2.nextElement()))
				return false;
		}

		return true;
	}

	public void addAssertion(RecordingItem ri) {
		int index = selectionModel.getLeadSelectionIndex();
		if(index > getSize())
			index = getSize() - 1;
		add(index > 0 ? index + 1 : getSize(), ri);
	}

	public void addItemsAsXML(Node parent, Document doc) {
		Enumeration e = elements();
		while(e.hasMoreElements()) {
			RecordingItem item = (RecordingItem)e.nextElement();
			parent.appendChild(item.toXML(doc));
		}
	}

	public void addItems(Collection c) {
		Iterator i = c.iterator();
		while(i.hasNext())
			addElement(i.next());
	}

	public void selectItem(int index) {
		selectionModel.setSelectionInterval(index, index);
	}

	public void addItem(RecordingItem item) {
		addElement(item);
		int index = getSize() - 1;
		selectItem(index);
	}

	public void removeItem(RecordingItem item) {
		removeElement(item);
	}

}
