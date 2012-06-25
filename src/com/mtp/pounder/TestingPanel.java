package com.mtp.pounder;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Component;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import com.mtp.gui.LayoutPrefs;

import com.mtp.pounder.controller.PounderController;

import com.mtp.i18n.Strings;

/**

Panel for handling the recording and playback of a PounderModel.

@author Matthew Pekar

**/
public class TestingPanel extends JPanel implements LayoutPrefs, PounderPrefsListener {

	protected PounderController controller;
	protected PounderModel model;
	protected JScrollPane listScrollPane;

	public TestingPanel(PounderController pc, PounderModel pm) {
		this.controller = pc;
		this.model = pm;
		this.model.getPreferences().addListener(this);

		initComponents();
		updateListScrollPaneComponent();
	}

	public void pounderPrefsChanged(PounderPrefs p, int what) {
		if(what == PounderPrefs.DISPLAY_SCRIPT_CHANGED)
			updateListScrollPaneComponent();
	}

	/** Update listScrollPane's component based on what the
	 * PounderPrefs desire. **/ 
	protected void updateListScrollPaneComponent() {
		Component c;
		if(model.getPreferences().getDisplayScript())
			c = new RecordingRecordList(controller);
		else {
			JLabel label = new JLabel(Strings.getString("Hidden"));
			label.setHorizontalAlignment(SwingConstants.CENTER);
			c = label;
		}

		listScrollPane.setViewportView(c);
	}

	protected JCheckBox buildPlaybackSpeedCheckBox() {
		JCheckBox ret = new JCheckBox(controller.getSetFastPlaybackAction());
		ret.setName("FastPlaybackCheckBox");
		ret.setSelected(model.getPreferences().getFastPlaybackEnabled());
		ItemListener il = new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					int change = e.getStateChange();
					if(change == ItemEvent.DESELECTED)
						model.getPreferences().setFastPlaybackEnabled(false);
					else if(change == ItemEvent.SELECTED)
						model.getPreferences().setFastPlaybackEnabled(true);
				}
			};
		ret.addItemListener(il);

		return ret;
	}

	protected JCheckBox buildIgnoreUnnamedCheckBox() {
		JCheckBox ret = new JCheckBox(controller.getSetIgnoreUnnamedAction());
		ret.setName("IgnoreUnnamedCheckBox");
		ret.setSelected(model.getPreferences().getIgnoreUnnamed());
		ItemListener il = new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					int change = e.getStateChange();
					if(change == ItemEvent.DESELECTED)
						model.getPreferences().setIgnoreUnnamed(false);
					else if(change == ItemEvent.SELECTED)
						model.getPreferences().setIgnoreUnnamed(true);
				}
			};
		ret.addItemListener(il);

		return ret;
	}

	protected JCheckBox buildSystemClassLoaderCheckBox() {
		JCheckBox ret = new JCheckBox(controller.getSetUseSystemClassLoaderAction());
		ret.setName("SystemClassLoaderCheckBox");
		ret.setSelected(model.getPreferences().getUseSystemClassLoader());
		ItemListener il = new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					int change = e.getStateChange();
					if(change == ItemEvent.DESELECTED)
						model.getPreferences().setUseSystemClassLoader(false);
					else if(change == ItemEvent.SELECTED)
						model.getPreferences().setUseSystemClassLoader(true);
				}
			};
		ret.addItemListener(il);

		return ret;
	}

	protected void initComponents() {
		setLayout(new GridBagLayout());

		int cs = COMPONENT_SPACING;

		add(new JLabel(Strings.getString("RecordedScript:")), new GridBagConstraints(0,0,2,1,1.0,0.0,GridBagConstraints.NORTH,GridBagConstraints.HORIZONTAL,new Insets(0,0,0,0), 0, 0));

		listScrollPane = new JScrollPane();
		add(listScrollPane, new GridBagConstraints(0,1,1,8,1.0,1.0,GridBagConstraints.NORTH,GridBagConstraints.BOTH,new Insets(cs,0,0,cs), 0, 0));
				
		JButton recordVerbatimButton = new JButton(controller.getRecordVerbatimAction());
		recordVerbatimButton.setName("recordVerbatimButton");
		add(recordVerbatimButton, new GridBagConstraints(1,1,1,1,0.0,0.0,GridBagConstraints.NORTH,GridBagConstraints.HORIZONTAL,new Insets(cs,0,0,0), 0, 0));
				
		JButton playbackButton = new JButton(controller.getPlayAction());
		playbackButton.setName("playbackButton");
		add(playbackButton, new GridBagConstraints(1,GridBagConstraints.RELATIVE,1,1,0.0,0.0,GridBagConstraints.NORTH,GridBagConstraints.HORIZONTAL,new Insets(cs,0,0,0), 0, 0));

		PauseButton pauseButton = new PauseButton(controller.getPauseAction(), controller.getResumeAction());
		pauseButton.setName("pauseButton");
		add(pauseButton, new GridBagConstraints(1,GridBagConstraints.RELATIVE,1,1,0.0,0.0,GridBagConstraints.NORTH,GridBagConstraints.HORIZONTAL,new Insets(cs,0,0,0), 0, 0));

		JButton stopButton = new JButton(controller.getStopAction());
		stopButton.setName("stopButton");
		add(stopButton, new GridBagConstraints(1,GridBagConstraints.RELATIVE,1,1,0.0,0.0,GridBagConstraints.NORTH,GridBagConstraints.HORIZONTAL,new Insets(cs,0,0,0), 0, 0));

		JButton newInstanceButton = new JButton(controller.getNewInstanceAction());
		newInstanceButton.setName("newInstanceButton");
		add(newInstanceButton, new GridBagConstraints(1,GridBagConstraints.RELATIVE,1,1,0.0,0.0,GridBagConstraints.NORTH,GridBagConstraints.HORIZONTAL,new Insets(cs,0,0,0), 0, 0));

		add(buildPlaybackSpeedCheckBox(), new GridBagConstraints(1,GridBagConstraints.RELATIVE,1,1,0.0,0.0,GridBagConstraints.NORTH,GridBagConstraints.HORIZONTAL,new Insets(cs + 25,0,0,0), 0, 0));

		add(buildSystemClassLoaderCheckBox(), new GridBagConstraints(1,GridBagConstraints.RELATIVE,1,1,0.0,0.0,GridBagConstraints.NORTH,GridBagConstraints.HORIZONTAL,new Insets(cs,0,0,0), 0, 0));

		add(buildIgnoreUnnamedCheckBox(), new GridBagConstraints(1,GridBagConstraints.RELATIVE,1,1,0.0,0.0,GridBagConstraints.NORTH,GridBagConstraints.HORIZONTAL,new Insets(cs,0,0,0), 0, 0));
	}
}
