package com.mtp.pounder.controller;

import java.awt.Toolkit;

import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.mtp.pounder.*;

import com.mtp.i18n.Strings;

/**

Action for showing a new test instance.

@author Matthew Pekar

**/
public class NewInstanceAction extends AbstractAction {

	protected PounderController controller;
	protected PounderModel model;

	public NewInstanceAction(PounderController c, PounderModel pm) {
		super(Strings.getString("NewInstance"));
		this.controller = c;
		this.model = pm;

		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_N));
	}

	public void actionPerformed(ActionEvent e) {
		model.newInstance();
	}

}
