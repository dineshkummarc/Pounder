package com.mtp.pounder;

import javax.swing.JPanel;
import javax.swing.JCheckBox;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import com.mtp.i18n.Strings;

/**

Panel used to create a VerbatimRecordingOptions.

@author Matthew Pekar

**/
public class VerbatimRecordingOptionsPanel extends JPanel {

	protected VerbatimRecordingOptions options;
	protected JCheckBox keyCheckBox, mouseInputCheckBox, mouseMotionCheckBox, mouseDragCheckBox, windowCheckBox;

		
	public VerbatimRecordingOptionsPanel() {
		this(new VerbatimRecordingOptions());
	}

	public VerbatimRecordingOptionsPanel(VerbatimRecordingOptions options) {
		if(options == null)
			throw new IllegalArgumentException("VerbatimRecordingOptions cannot be null");
		this.options = options;
		initComponents();
	}

	/** Retrieve data from check boxes and place it into 'options'. **/
	protected void retrieveResults() {
		options.doKeyEvents = keyCheckBox.isSelected();
		options.doMouseInputEvents = mouseInputCheckBox.isSelected();
		options.doMouseMotionEvents = mouseMotionCheckBox.isSelected();
		options.doWindowEvents = windowCheckBox.isSelected();
		options.doMouseDragEvents = mouseDragCheckBox.isSelected();
	}

	protected void initComponents() {
		setLayout(new GridBagLayout());

		ActionListener clickActionListener = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					retrieveResults();
				}
			};

		GridBagConstraints constraints = new GridBagConstraints(0,GridBagConstraints.RELATIVE,1,1,1.0,0.0,GridBagConstraints.NORTHWEST,GridBagConstraints.HORIZONTAL,new Insets(0,0,0,0),0,0);

		keyCheckBox = new JCheckBox(Strings.getString("KeyEvents"));
		keyCheckBox.addActionListener(clickActionListener);
		keyCheckBox.setSelected(options.doKeyEvents);
		add(keyCheckBox, constraints);

		mouseInputCheckBox = new JCheckBox(Strings.getString("MouseInputEvents"));
		mouseInputCheckBox.addActionListener(clickActionListener);
		mouseInputCheckBox.setSelected(options.doMouseInputEvents);
		add(mouseInputCheckBox, constraints);

		mouseMotionCheckBox = new JCheckBox(Strings.getString("MouseMotionEvents"));
		mouseMotionCheckBox.addActionListener(clickActionListener);
		mouseMotionCheckBox.setSelected(options.doMouseMotionEvents);
		add(mouseMotionCheckBox, constraints);

		mouseDragCheckBox = new JCheckBox(Strings.getString("MouseDragEvents"));
		mouseDragCheckBox.addActionListener(clickActionListener);
		mouseDragCheckBox.setSelected(options.doMouseDragEvents);
		add(mouseDragCheckBox, constraints);

		windowCheckBox = new JCheckBox(Strings.getString("WindowEvents"));
		windowCheckBox.addActionListener(clickActionListener);
		windowCheckBox.setSelected(options.doWindowEvents);
		add(windowCheckBox, constraints);
	}

}
