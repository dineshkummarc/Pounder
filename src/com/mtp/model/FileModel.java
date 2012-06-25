package com.mtp.model;

import java.io.File;

import java.util.Iterator;

/**

Models a java File object.  Has options for allowing invalid files.

@author Matthew Pekar

**/
public class FileModel {

	public static final String FILE_CHANGED = "File Changed";

	protected File file;
	protected Listeners listeners;

	public FileModel() {
		this(null);
	}

	public FileModel(File file) {
		this.file = file;
		this.listeners = new Listeners();
	}

	public File getFile() {
		return file;
	}

	public void setFile(File f) {
		this.file = f;
		fireChange(FILE_CHANGED);
	}

	public void addListener(FileModelListener l) {
		listeners.add(l);
	}

	public void removeListener(FileModelListener l) {
		listeners.remove(l);
	}

	protected void fireChange(String event) {
		Iterator i = listeners.getFiringIterator();
		while(i.hasNext()) {
			FileModelListener fileModelListener = (FileModelListener)i.next();
			fileModelListener.fileModelChanged((String)event);
		}
	}

	public boolean equals(Object o) {
		FileModel m = (FileModel)o;
		if(m.file == null && file == null)
			return true;

		if(m.file != null)
			return m.file.equals(file);

		return false;
	}

}

