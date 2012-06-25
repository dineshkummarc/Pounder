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

Action for saving a PounderModel.

@author Matthew Pekar

**/
public class SaveAction extends AbstractAction implements PounderConstants {

	protected PounderController controller;
	protected PounderModel model;

	/** Customized versions of SaveAction use this constructor, which
	 * does not add any action information. **/
	public SaveAction(String name, PounderController c, PounderModel pm) {
		super(name);
		this.controller = c;
		this.model = pm;
	}

	public SaveAction(PounderController c, PounderModel pm) {
		super(Strings.getString("Save"));
		this.controller = c;
		this.model = pm;

		putValue(SMALL_ICON, ResourceLoader.getIcon("/toolbarButtonGraphics/general/Save16.gif"));
		putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_S));
	}

	protected File getFileForWriting() {
		File f = model.getFileModel().getFile();
		if(f != null)
			return f;

		return showFileChooser();
	}

	public void actionPerformed(ActionEvent e) {
		if(controller == null || model == null)
			return;

		File f = getFileForWriting();
		if(f == null) {
			String saveCancelledString = Strings.getString("SaveCancelled");
			model.getStatusModel().setStatus(saveCancelledString);
			return;
		}

		try {
			new PounderWriter().write(model, f);
			String saveSuccessfulString = Strings.getString("SaveSuccessful");
			model.getStatusModel().setStatus(saveSuccessfulString);
			model.getFileModel().setFile(f);
		}
		catch(Exception exc) {
			String errorSavingString = Strings.getString("ErrorSaving");
			model.getStatusModel().handleException(errorSavingString, exc);
		}
	}

	/** Return a new File with the extension fixed, if necessary. **/
	protected File fixExtension(File f) {
		if(f.getName().endsWith(FILE_EXTENSION) ||
			 f.getName().endsWith(".xml"))
			return f;

		return new File(f.getAbsolutePath() + FILE_EXTENSION);
	}

	protected File showFileChooser () {
		JFileChooser fileChooser = controller.getFileChooser();
		int res = fileChooser.showSaveDialog(controller.getFrame());
		switch(res) {
		case JFileChooser.APPROVE_OPTION:
			return fixExtension(fileChooser.getSelectedFile());
		default:
			return null;
		}				
	}

}
