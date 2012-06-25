package com.mtp.pounder;

import javax.swing.filechooser.FileFilter;

import javax.swing.JFileChooser;

import java.io.File;

import com.mtp.i18n.Strings;

/**

File chooser for Pounder files.

@author Matthew Pekar

**/
public class PounderFileChooser extends JFileChooser implements PounderConstants {

	protected PounderPrefs prefs;

	public PounderFileChooser(PounderPrefs prefs) {
		this.prefs = prefs;

		setName("PounderFrameOpenFileChooser");
		setMultiSelectionEnabled(false);
		setCurrentDirectory(prefs.getHomeDirectory());

		initFilter();
	}

	public void setCurrentDirectory(File dir) {
		super.setCurrentDirectory(dir);
		if(prefs != null)
			prefs.setHomeDirectory(dir);
	}

	protected void initFilter() {
		addChoosableFileFilter(new FileFilter() {
				public boolean accept(File f) {
					return f.isDirectory() || 
						f.getName().endsWith(".xml");
				}
								
				public String getDescription() {
					return Strings.getString("XMLFiles");
				}
			});

		addChoosableFileFilter(new FileFilter() {
				public boolean accept(File f) {
					return f.isDirectory() || 
						f.getName().endsWith(FILE_EXTENSION);
				}
								
				public String getDescription() {
					return Strings.getString("PounderScripts");
				}
			});
	}
}
