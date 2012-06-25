package com.mtp.pounder.assrt;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import com.mtp.gui.LayoutPrefs;

import com.mtp.gui.widget.IntegerTextField;

/**

Panel for editing a WindowShowingPresenter.

@author Matthew Pekar

**/
public class WindowShowingPanel extends JPanel implements LayoutPrefs {

	protected WindowShowingPresenter presenter;
		
	public WindowShowingPanel() {
		this(new WindowShowingPresenter());
	}

	public WindowShowingPanel(WindowShowingPresenter p) {
		setName("WindowShowingPanel");
		setPresenter(p);
	}

	public void setPresenter(WindowShowingPresenter p) {
		this.presenter = p;
		initLayout();
	}

	protected void initLayout() {
		removeAll();
		setLayout(new GridBagLayout());
		
		add(new WindowIdentifierPanel(presenter.getWindowIdentifierPresenter()), new GridBagConstraints(0,0,1,1,1.0,1.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,new Insets(0,0,0,0),0,0));
	}
}
