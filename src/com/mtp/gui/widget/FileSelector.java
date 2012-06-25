package com.mtp.gui.widget;

/*

Allows the user to select a file through drag n' drop or a popup.
Works on a FileModel.

TODO: implement :)

TODO: have it remove itself as a listener at appropriate time

*/

import javax.swing.JComponent;
import javax.swing.JFrame;

import java.awt.Graphics;
import java.awt.Dimension;

import java.io.File;

import com.mtp.model.FileModel;
import com.mtp.model.FileModelListener;

public class FileSelector extends JComponent implements FileModelListener {

	public static void main(String args[]) {
		JFrame frame = new JFrame("FOOBAR");
		frame.getContentPane().add(new FileSelector(new FileModel()));
		frame.pack();
		frame.setSize(new Dimension(400, 400));
		frame.setLocation(50, 50);
		frame.show();
	}

	protected FileModel model;

	public FileSelector(FileModel model) {
		this.model = model;
		model.addListener(this);
	}

	//from FileModelListener
	public void fileModelChanged(String what) {
		if(what.equals(FileModel.FILE_CHANGED)) {
	    handleFileChange();
		}
	}

	protected void handleFileChange() {
		updateToolTip();
		repaint();
	}

	protected void updateToolTip() {
		File file = model.getFile();
		if(file != null) {
	    setToolTipText(file.getName());
		}
		else {
	    setToolTipText("null");
		}
	}

	public void paint(Graphics g) {
		Dimension size = getSize();
		if(model.getFile() != null)
	    g.fillRect(5,5,size.width - 5, size.height - 5);
	}

}

