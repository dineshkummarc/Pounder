package com.mtp.pounder;

import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Point;

import java.awt.event.ActionEvent;

import com.mtp.gui.LayoutPrefs;

import com.mtp.gui.widget.IntegerTextField;

import com.mtp.pounder.controller.PounderController;

import com.mtp.model.PointModel;

import com.mtp.i18n.Strings;

/**

Used to define the setup of a Pounder script.

@author Matthew Pekar

**/
public class SetupPanel extends JPanel implements LayoutPrefs {

	protected static final String LOAD_COMPONENT_COMMAND = "LoadComponent";
		
	protected PounderModel model;
	protected PounderController controller;
	protected JComboBox testClassComboBox;
	protected AbstractAction loadComponentAction, updateDefaultWindowLocationAction;

	public SetupPanel(PounderController pc, PounderModel pm) {
		this.controller = pc;
		this.model = pm;
		initActions();
		initComponents();
	}

	public JComboBox getTestClassComboBox() {
		return testClassComboBox;
	}

	/** Check if script has any items in it, and if so display a
	 * warning.  Return true if OK to change the class.  **/
	protected boolean confirmChangeClass() {
		RecordingRecord rr = model.getRecord();
		if(rr.getSize() > 0) {
			int result = JOptionPane.showConfirmDialog(this, Strings.getString("ChangingClassWillDeleteRecordedScriptOK"), Strings.getString("DeleteScript?"), JOptionPane.YES_NO_OPTION);
			return result == JOptionPane.YES_OPTION;
		}
				
		return true;
	}

	protected void initActions() {
		loadComponentAction = new AbstractAction() {
				public void actionPerformed(ActionEvent e) {
					if(! e.getActionCommand().equals(LOAD_COMPONENT_COMMAND))
						return;

					if(! confirmChangeClass()) {
						testClassComboBox.setSelectedItem(model.getTestInstanceFactory().getSetupClass());
						return;
					}

					Object o = testClassComboBox.getSelectedItem();
					if(o == null)
						return;
					if(o.toString().length() == 0)
						return;
										
					model.setTestClass(o.toString());
				}
			};
	}

	protected JPanel buildClassPanel() {
		JPanel ret = new JPanel();
		ret.setLayout(new GridBagLayout());

		ret.add(new JLabel(Strings.getString("Class:")), new GridBagConstraints(0,0,1,1,0.0,0.0,GridBagConstraints.NORTHWEST,GridBagConstraints.NONE,new Insets(0,0,0,0), 0, 0));

		testClassComboBox = new JComboBox(model.getLoadedTestObjects());
		testClassComboBox.setName("TestClassComboBox");
		testClassComboBox.setAction(controller.getSetTestClassAction());
		testClassComboBox.setEditable(true);
		testClassComboBox.setActionCommand(LOAD_COMPONENT_COMMAND);
		testClassComboBox.addActionListener(loadComponentAction);
		ret.add(testClassComboBox, new GridBagConstraints(1,0,1,1,1.0,0.0,GridBagConstraints.NORTHWEST,GridBagConstraints.HORIZONTAL,new Insets(0,LABEL_SPACING,0,0), 0, 0));

		return ret;
	}

	protected JPanel buildDefaultLocationPanel() {
		JPanel ret = new JPanel();
		ret.setLayout(new GridBagLayout());


		PointModel defaultLocation = model.getPreferences().getDefaultTestWindowLocation();
		IntegerTextField xField = new IntegerTextField(defaultLocation.getXModel());
		xField.setName("XTextField");
		xField.setAction(controller.getUpdateDefaultWindowXAction());
		IntegerTextField yField = new IntegerTextField(defaultLocation.getYModel());
		yField.setName("YTextField");
		yField.setAction(controller.getUpdateDefaultWindowYAction());

		int cs = COMPONENT_SPACING;
		int ls = LABEL_SPACING;

		ret.add(new JLabel(Strings.getString("DefaultWindowLocation:")), new GridBagConstraints(0,0,4,1,1.0,0.0,GridBagConstraints.NORTH,GridBagConstraints.HORIZONTAL,new Insets(0,0,0,0), 0, 0));
		ret.add(new JLabel(Strings.getString("X:")), new GridBagConstraints(0,1,1,1,0.0,0.0,GridBagConstraints.NORTH,GridBagConstraints.NONE,new Insets(cs,0,0,0), 0, 0));
		ret.add(xField, new GridBagConstraints(1,1,1,1,0.5,0.0,GridBagConstraints.NORTH,GridBagConstraints.HORIZONTAL,new Insets(cs,ls,0,0), 0, 0));
		ret.add(new JLabel(Strings.getString("Y:")), new GridBagConstraints(2,1,1,1,0.0,0.0,GridBagConstraints.NORTH,GridBagConstraints.NONE,new Insets(cs,cs,0,0), 0, 0));
		ret.add(yField, new GridBagConstraints(3,1,1,1,0.5,0.0,GridBagConstraints.NORTH,GridBagConstraints.HORIZONTAL,new Insets(cs,ls,0,0), 0, 0));

		return ret;
	}

	protected void initComponents() {
		setLayout(new GridBagLayout());

		int cs = COMPONENT_SPACING;
		add(buildClassPanel(), new GridBagConstraints(0,0,1,1,1.0,0.0,GridBagConstraints.NORTH,GridBagConstraints.HORIZONTAL,new Insets(0,0,0,0), 0, 0));
		add(buildDefaultLocationPanel(), new GridBagConstraints(0,1,1,1,0.0,0.0,GridBagConstraints.NORTHWEST,GridBagConstraints.NONE,new Insets(cs,0,0,0), 0, 0));
	}

}
