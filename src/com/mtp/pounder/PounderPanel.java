package com.mtp.pounder;

import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JProgressBar;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Dimension;

import com.mtp.gui.LayoutPrefs;

import com.mtp.gui.widget.StatusPanel;

import com.mtp.pounder.controller.PounderController;

import com.mtp.i18n.Strings;

/**

Main panel of Pounder.

@author Matthew Pekar

**/
public class PounderPanel extends JPanel implements LayoutPrefs {

	protected PounderController controller;
	protected PounderModel model;
	protected SetupPanel setupPanel;
	protected TestingPanel testingPanel;
		
	public PounderPanel(PounderController pc, PounderModel pm) {
		this.controller = pc;
		this.model = pm;
		initComponents();
	}

	public SetupPanel getSetupPanel() {
		return setupPanel;
	}

	public TestingPanel getTestingPanel() {
		return testingPanel;
	}

	protected void initComponents() {
		setLayout(new GridBagLayout());

		int bs = BORDER_SPACING;
		int cs = COMPONENT_SPACING;
		int ls = LABEL_SPACING;
		int ibs = INNER_BORDER_SPACING;

		setupPanel = new SetupPanel(controller, model);
		setupPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(Strings.getString("Setup")), BorderFactory.createEmptyBorder(ibs,ibs,ibs,ibs)));
		add(setupPanel, new GridBagConstraints(0,0,1,1,1.0,0.0,GridBagConstraints.NORTHWEST,GridBagConstraints.HORIZONTAL,new Insets(0,0,0,0), 0, 0));
		testingPanel = new TestingPanel(controller, model);
		testingPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder(Strings.getString("Testing")), BorderFactory.createEmptyBorder(ibs,ibs,ibs,ibs)));
		add(testingPanel, new GridBagConstraints(0,1,1,1,1.0,1.0,GridBagConstraints.NORTHWEST,GridBagConstraints.BOTH,new Insets(cs,0,0,0), 0, 0));

		JPanel statusPanel = new JPanel();
		statusPanel.setLayout(new GridBagLayout());
		add(statusPanel, new GridBagConstraints(0,2,1,1,1.0,0.0,GridBagConstraints.NORTH,GridBagConstraints.HORIZONTAL,new Insets(COMPONENT_SPACING,0,0,0), 0, 0));

		StatusPanel statusDisplay = new StatusPanel(model.getStatusModel());
		statusDisplay.setAction(controller.getViewExceptionStackTraceAction());
		statusPanel.add(statusDisplay, new GridBagConstraints(0,0,1,1,1.0,0.0,GridBagConstraints.NORTH,GridBagConstraints.HORIZONTAL,new Insets(0,0,0,0), 0, 0));
		JProgressBar pb = new JProgressBar(model.getProgressModel());
		pb.setMinimumSize(pb.getPreferredSize());
		statusPanel.add(pb, new GridBagConstraints(1,0,1,1,0.0,0.0,GridBagConstraints.NORTH,GridBagConstraints.NONE,new Insets(0,COMPONENT_SPACING,0,0), 0, 0));

	}
}
