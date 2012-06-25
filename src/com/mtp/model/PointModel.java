package com.mtp.model;

import java.awt.Point;

import java.util.Iterator;

/**

Model for a Point.  

@author Matthew Pekar

**/
public class PointModel {

	protected Listeners listeners;
		
	protected DefaultIntegerModel x, y;

	public PointModel() {
		this(0, 0);
	}

	public PointModel(int xVal, int yVal) {
		this.x = new DefaultIntegerModel(xVal);
		this.y = new DefaultIntegerModel(yVal);
		this.listeners = new Listeners();
	}

	public boolean equals(Object o) {
		if(o == null)
			return false;
				
		if(! (o instanceof PointModel))
			return false;

		PointModel pm = (PointModel)o;
		return x.getValue() == pm.getX() &&
			y.getValue() == pm.getY();
	}

	public DefaultIntegerModel getXModel() {
		return x;
	}

	public DefaultIntegerModel getYModel() {
		return y;
	}

	public int getX() {
		return x.getValue();
	}

	public int getY() {
		return y.getValue();
	}

	public void setX(int val) {
		x.setValue(val);
	}

	public void setY(int val) {
		y.setValue(val);
	}

	public Point getValue() {
		return new Point(x.getValue(), y.getValue());
	}

	public void addListener(PointModelListener l) {
		listeners.add(l);
	}

	public void removeListener(PointModelListener l) {
		listeners.remove(l);
	}

	protected void fireChange() {
		Iterator i = listeners.getFiringIterator();
		while(i.hasNext()) {
			PointModelListener l = (PointModelListener)i.next();
			l.pointModelChanged(this);
		}
	}

}
