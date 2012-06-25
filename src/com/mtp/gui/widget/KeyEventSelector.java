package com.mtp.gui.widget;

import com.mtp.gui.LayoutPrefs;

import javax.swing.JPanel;
import javax.swing.JLabel;

import java.util.Collection;
import java.util.Vector;
import java.util.Iterator;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.awt.Component;
import java.awt.Dimension;

import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;

import com.mtp.model.Listeners;

import com.mtp.i18n.Strings;

/**

Allows user to click on this widget and then enter a KeyEvent.

@author Matthew Pekar

**/
public class KeyEventSelector extends JPanel implements LayoutPrefs {

	protected static final int WAITING_FOR_MOUSE_PRESS = 1;
	protected static final int WAITING_FOR_KEY_PRESS = 2;

	protected int state;
	protected JLabel statusLabel, valueLabel;
	protected KeyEvent keyEvent;
	protected AWTEventListener keyListener;
	protected Listeners listeners;

	public KeyEventSelector() {
		this(null);
	}

	public KeyEventSelector(KeyEvent e) {
		this.listeners = new Listeners();
		this.keyEvent = null;
		this.keyListener = buildKeyListener();

		init();

		setState(WAITING_FOR_MOUSE_PRESS);
		setPreferredSize(new Dimension(200, 50));
		setKeyEvent(e);
	}

	protected AWTEventListener buildKeyListener() {
		return new AWTEventListener() {
				public void eventDispatched(AWTEvent e) {
					KeyEvent ke = (KeyEvent)e;
					if(ke.getID() == KeyEvent.KEY_PRESSED) {
						int code = ke.getKeyCode();
						if(code == KeyEvent.VK_CONTROL || 
							 code == KeyEvent.VK_SHIFT || 
							 code == KeyEvent.VK_ALT || 
							 code == KeyEvent.VK_META)
							return;

						removeKeyListener();
						setKeyEvent(new KeyEvent((Component)ke.getSource(), ke.getID(), ke.getWhen(), ke.getModifiersEx(), ke.getKeyCode(), ke.getKeyChar(), ke.getKeyLocation()));
						setState(WAITING_FOR_MOUSE_PRESS);
					}
				}
			};
	}

	public KeyEvent getKeyEvent() {
		return keyEvent;
	}

	public void setKeyEvent(KeyEvent e) {
		keyEvent = e;
		setState(WAITING_FOR_MOUSE_PRESS);
		fireEvent(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "KeyEventChanged"));
	}

	protected void setState(int state) {
		this.state = state;
		if(state == WAITING_FOR_MOUSE_PRESS) {
			statusLabel.setText(Strings.getString("ClickToRecordKeyEvent"));
			removeKeyListener();
		}
		else if(state == WAITING_FOR_KEY_PRESS) {
			attachKeyListener();
			statusLabel.setText(Strings.getString("EnterKeyEvent"));
		}
		valueLabel.setText(Strings.getString("Value:") + getKeyEventAsString());
		repaint(1);
	}

	protected void init() {
		removeAll();
		setLayout(new GridBagLayout());

		statusLabel = new JLabel("");
		add(statusLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0,0,0,0), 0, 0));

		valueLabel = new JLabel("");
		add(valueLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0,0,0,0), 0, 0));

		MouseAdapter mouseAdapter = new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					if(e.getButton() == MouseEvent.BUTTON1) {
						if(state == WAITING_FOR_MOUSE_PRESS) {
							setState(WAITING_FOR_KEY_PRESS);
						}
						else {
							setState(WAITING_FOR_MOUSE_PRESS);
						}
					}
				}
			};
		addMouseListener(mouseAdapter);
		statusLabel.addMouseListener(mouseAdapter);
		valueLabel.addMouseListener(mouseAdapter);
	}

	protected void attachKeyListener() {
		long mask = AWTEvent.KEY_EVENT_MASK;
		Toolkit.getDefaultToolkit().addAWTEventListener(keyListener, mask);
	}

	protected void removeKeyListener() {
		Toolkit.getDefaultToolkit().removeAWTEventListener(keyListener);
	}

	protected String getKeyEventAsString() {
		if(keyEvent == null)
			return "None";
		return KeyEvent.getKeyModifiersText(keyEvent.getModifiers()) + " " + KeyEvent.getKeyText(keyEvent.getKeyCode());
	}

	public synchronized void addActionListener(ActionListener l) {
		listeners.add(l);
	}

	public synchronized void removeActionListener(ActionListener l) {
		listeners.remove(l);
	}

	protected void fireEvent(ActionEvent e) {
		Iterator i = listeners.getFiringIterator();
		while(i.hasNext()) {
			ActionListener l = (ActionListener)i.next();
			l.actionPerformed(e);
		}
	}

}
