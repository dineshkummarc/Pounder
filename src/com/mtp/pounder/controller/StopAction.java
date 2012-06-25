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

Action for stopping the current recording or playback.

@author Matthew Pekar

**/
public class StopAction extends AbstractAction {

	protected PounderController controller;
	protected PounderModel model;

	public StopAction(PounderController c, PounderModel pm) {
		super(Strings.getString("Stop"));
		this.controller = c;
		this.model = pm;

		putValue(AbstractAction.SMALL_ICON, ResourceLoader.getIcon("/toolbarButtonGraphics/media/Stop16.gif"));
		putValue(AbstractAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_PERIOD, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		putValue(AbstractAction.MNEMONIC_KEY, new Integer(KeyEvent.VK_PERIOD));
	}

	public void actionPerformed(ActionEvent e) {
		model.stop();
	}

}
