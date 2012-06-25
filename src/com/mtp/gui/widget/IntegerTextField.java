package com.mtp.gui.widget;

import com.mtp.model.IntegerModel;
import com.mtp.model.IntegerModelListener;
import com.mtp.model.IntegerDocument;
import com.mtp.model.DefaultIntegerModel;

import javax.swing.text.Document;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;

import javax.swing.JTextField;

import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;

/**

Text field for entering integers only.

@author Matthew Pekar

**/
public class IntegerTextField extends JTextField implements IntegerModelListener {

	protected IntegerModel model;
	protected boolean ignoreModelChange;
		
	public IntegerTextField() {
		this(new DefaultIntegerModel());
	}

	public IntegerTextField(IntegerModel model) {
		this.model = null;

		initDocumentListener();

		setModel(model);
	}

	protected void setModel(IntegerModel model) {
		if(this.model != null)
			this.model.removeListener(this);

		this.model = model;
		this.model.addListener(this);
		this.ignoreModelChange = false;

		updateText();
	}

	/** Update value of text based on model. **/
	protected void updateText() {
		try {
			Document d = getDocument();
			d.remove(0, d.getLength());
			d.insertString(0, String.valueOf(model.getValue()), null);
		}
		catch(BadLocationException exc) {
		}
	}

	/** Update value of model based on text. **/
	protected void updateModel() {
		if(model == null)
			return;

		Document d = getDocument();
		try {
			model.setValue(Integer.valueOf(d.getText(0, d.getLength())).intValue());
		}
		catch(Exception exc) {
			model.setValue(-1);
		}
	}

	protected void initDocumentListener() {
		DocumentListener dl = new DocumentListener() {
				public void changedUpdate(DocumentEvent e) {
					updateModel();
				}

				public void insertUpdate(DocumentEvent e) {
					updateModel();
				}

				public void removeUpdate(DocumentEvent e) {
					updateModel();
				}
			};
		getDocument().addDocumentListener(dl);
	}

	protected Document createDefaultModel() {
		return new IntegerDocument() {
				public void insertString(int offs, String s, AttributeSet a) throws BadLocationException {
					ignoreModelChange = true;
					super.insertString(offs, s, a);
					ignoreModelChange = false;
				}

				public void remove(int offs, int len) throws BadLocationException {
					ignoreModelChange = true;
					super.remove(offs, len);
					ignoreModelChange = false;
				}
			};
	}

	public void integerModelChanged(IntegerModel im) {
		if(ignoreModelChange)
			return;

		setText(String.valueOf(im.getValue()));
	}

}
