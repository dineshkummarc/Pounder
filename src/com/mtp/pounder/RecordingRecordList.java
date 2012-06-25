package com.mtp.pounder;

import javax.swing.JList;
import javax.swing.JPopupMenu;
import javax.swing.JMenu;

import javax.swing.DefaultListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.awt.Frame;

import com.mtp.pounder.controller.PounderController;
import com.mtp.pounder.controller.AssertWindowShowingAction;
import com.mtp.pounder.controller.AssertTextEqualsAction;

/**

Custom JList for displaying a RecordingRecord.  Offfers keyboard
shortcut functionality and other tweaks.

@author Matthew Pekar

**/
public class RecordingRecordList extends JList {

	protected PounderController controller;
	protected RecordingRecord record;
		
	public RecordingRecordList(PounderController controller) {
		super(controller.getModel().getRecord());

		this.controller = controller;
		this.record = controller.getModel().getRecord();

		RecordingRecord record = controller.getModel().getRecord();
		setSelectionModel(record.getSelectionModel());
		addMakeSelectionVisibleListener();
		addDeleteShortcutListener();
		addPopupListener();
	}


	protected JPopupMenu buildPopupMenu() {
		JPopupMenu ret = new JPopupMenu();

		JMenu menu = new JMenu(controller.getAddAssertAction());
		Frame f = (Frame)SwingUtilities.windowForComponent(this);
		menu.add(new AssertWindowShowingAction(controller.getModel(), f));
		menu.add(new AssertTextEqualsAction(controller.getModel(), f));
		ret.add(menu);

		return ret;
	}

	protected void addPopupListener() {
		addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					checkPopup(e);
				}

				public void mousePressed(MouseEvent e) {
					checkPopup(e);
				}

				public void mouseReleased(MouseEvent e) {
					checkPopup(e);
				}

				protected void checkPopup(MouseEvent e) {
					if(! e.isPopupTrigger())
						return;

					buildPopupMenu().show(RecordingRecordList.this, e.getX(), e.getY());
				}
			});
	}

	protected void addMakeSelectionVisibleListener() {
		record.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {
					if(e.getValueIsAdjusting())
						return;
					ensureIndexIsVisible(getSelectedIndex());
				}
			});
	}

	protected void addDeleteShortcutListener() {
		addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					int code = e.getKeyCode();
					switch(code) {
					case KeyEvent.VK_DELETE:
					case KeyEvent.VK_BACK_SPACE:
						removeSelectedItems();
						break;
					}
				}
			});
	}

	protected void removeSelectedItems() {
		int[] selected = getSelectedIndices();
		int adjustment = 0; //indexes change each time we do a removal
		for(int i=0;i < selected.length;i++) {
			record.removeElementAt(selected[i] + adjustment);
			adjustment -= 1;
		}
	}
		
}
