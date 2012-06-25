package com.mtp.model;

import java.util.Iterator;

import java.awt.Color;

import com.mtp.i18n.Strings;

/**

Represents an integer and alerts listeners of changes.

@author Matthew Pekar

**/
public class StatusModel {

	protected Listeners listeners;
	protected String status;
	protected Color color;
	protected StackTraceElement[] stackTrace;
	protected Throwable exception;

	public StatusModel() {
		this.listeners = new Listeners();
		this.status = "";
		this.color = Color.BLACK;
		this.exception = null;
	}

	public Throwable getException() {
		return exception;
	}

	/** Return my StatusModelListener's. **/
	public Listeners getListeners() {
		return listeners;
	}

	public String getStatus() {
		return status;
	}

	public void setColor(Color c) {
		this.color = c;
	}

	public Color getColor() {
		return color;
	}

	public void setStatus(String s) {
		setStatus(s, Color.BLACK);
	}

	public void setStatus(String s, Color c) {
		this.status = s;
		setColor(c);
		fireChange();
	}

	protected void fireChange() {
		Iterator i = listeners.getFiringIterator();
		while(i.hasNext()) {
			StatusModelListener l = (StatusModelListener)i.next();
			l.statusModelChanged(this);
		}
	}

	protected Throwable getRootCause(Throwable t) {
		return t.getCause() == null ? t : getRootCause(t.getCause());
	}

	protected boolean specialExceptionCaseHandled(Throwable t) {
		Throwable root = getRootCause(t);
		if(t.getMessage() == null || t.getMessage().length() == 0) {
			handleException(t.getClass().getName(), t);
			return true;
		}
		else if(root instanceof ClassNotFoundException) {
			handleException(Strings.getString("ClassNotFound:") + root.getLocalizedMessage(), t);
			return true;
		}

		return false;
	}

	public void handleException(Throwable t) {
		if(specialExceptionCaseHandled(t))
			return;

		setStatus(t.getLocalizedMessage(), Color.RED);
		exception = t;
	}

	public void handleException(String humanMessage, Throwable t) {
		setStatus(humanMessage, Color.RED);
		exception = t;
	}
		
}
