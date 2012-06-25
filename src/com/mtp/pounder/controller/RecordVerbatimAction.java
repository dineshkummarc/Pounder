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

Action for beginning a verbatim recording.

@author Matthew Pekar

**/
public class RecordVerbatimAction extends AbstractAction {

	protected PounderController controller;
	protected PounderModel model;

	public RecordVerbatimAction(PounderController c, PounderModel pm) {
		super(Strings.getString("Record"));
		this.controller = c;
		this.model = pm;

		putValue(AbstractAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_R, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		putValue(AbstractAction.MNEMONIC_KEY, new Integer(KeyEvent.VK_R));
	}

	public void actionPerformed(ActionEvent e) {
		model.beginVerbatimRecording();
	}

}
