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

Action for playing the current script.

@author Matthew Pekar

**/
public class PlayAction extends AbstractAction {

	protected PounderController controller;
	protected PounderModel model;

	public PlayAction(PounderController c, PounderModel pm) {
		super(Strings.getString("Play"));
		this.controller = c;
		this.model = pm;

		putValue(AbstractAction.SMALL_ICON, ResourceLoader.getIcon("/toolbarButtonGraphics/media/Play16.gif"));
		putValue(AbstractAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_P, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		putValue(AbstractAction.MNEMONIC_KEY, new Integer(KeyEvent.VK_P));
	}

	public void actionPerformed(ActionEvent e) {
		model.playback();
	}

}
