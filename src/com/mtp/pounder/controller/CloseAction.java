package com.mtp.pounder.controller;

import java.awt.Toolkit;

import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import java.io.File;

import com.mtp.pounder.*;

import com.mtp.i18n.Strings;

/**

Action for closing the main frame.

@author Matthew Pekar

**/
public class CloseAction extends AbstractAction {

	protected PounderController controller;
	protected PounderModel model;

	public CloseAction(PounderController c, PounderModel pm) {
		super(Strings.getString("Close"));
		this.controller = c;
		this.model = pm;

		putValue(AbstractAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_W, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
	}

	public void actionPerformed(ActionEvent e) {
		controller.getFrame().closeRequested();
	}

}
