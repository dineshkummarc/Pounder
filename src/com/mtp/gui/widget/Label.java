package com.mtp.gui.widget;

import com.mtp.model.StringModel;
import com.mtp.model.StringModelListener;

import javax.swing.JLabel;

/**

Label based on a StringModelInterface, nice for dynamic updates.

@author Matthew Pekar

**/
public class Label extends JLabel implements StringModelListener {

    protected StringModel model;

    public Label(StringModel model) {
				this.model = model;
				model.addListener(this);
				updateText();
    }

    //from StringModelListener
    public void stringModelChanged(StringModel source) {
				updateText();
    }

    protected void updateText() {
				setText(model.getString());
    }

}
