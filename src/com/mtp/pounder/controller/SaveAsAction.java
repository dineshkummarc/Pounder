package com.mtp.pounder.controller;

import java.awt.Toolkit;

import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;
import javax.swing.JFileChooser;

import java.io.File;

import com.mtp.pounder.*;

import com.mtp.i18n.Strings;

/**

Extends SaveAction and makes sure file chooser is always shown.

@author Matthew Pekar

**/
public class SaveAsAction extends SaveAction {

	public SaveAsAction(PounderController c, PounderModel pm) {
		super(Strings.getString("SaveAs"), c, pm);

		putValue(SMALL_ICON, ResourceLoader.getIcon("/toolbarButtonGraphics/general/SaveAs16.gif"));
		putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_A));
	}

	protected File getFileForWriting() {
		return showFileChooser();
	}

}
