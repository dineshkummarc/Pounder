package com.mtp.pounder;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.AbstractAction;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.KeyStroke;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.JCheckBoxMenuItem;

import javax.swing.filechooser.FileFilter;

import java.net.URL;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.Toolkit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import java.io.File;

import com.mtp.gui.LayoutPrefs;
import com.mtp.gui.SystemExitWatcher;
import com.mtp.gui.WindowUtilities;

import com.mtp.model.FileModelListener;

import com.mtp.pounder.controller.PounderController;

import com.mtp.i18n.Strings;

/**

Main frame for the Pounder interface.

@author Matthew Pekar
	 
**/
public class PounderFrame extends JFrame implements LayoutPrefs, PounderConstants {

	public static final String NAME = "PounderFrame";

	protected PounderPrefsListener prefsListener;
	protected PounderPanel pounderPanel;
	protected JCheckBoxMenuItem viewRecordedScriptMenuItem;
	protected PounderController controller;
	protected PounderModel model;

	public PounderFrame() {
		this(new PounderModel());
	}

	public PounderFrame(PounderModel pm) {
		this(new PounderController(pm));
	}

	public PounderFrame(PounderController pc) {
		this.model = pc.getModel();
		this.controller = pc;
		this.controller.setFrame(this);

		setName(NAME);
		setTitle(Strings.getString("DefaultTitle"));
		URL imageURL = getClass().getResource("/logo.png");
		setIconImage(Toolkit.getDefaultToolkit().getImage(imageURL));

		addCloseListener();
		addTitleChangeListener();
		initPounderPrefsListener();
		initMenu();
		initComponents();

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setSize(500, 600);
		WindowUtilities.center(this);
	}

	public PounderModel getModel() {
		return model;
	}

	protected void initPounderPrefsListener() {
		prefsListener = new PounderPrefsListener() {
				public void pounderPrefsChanged(PounderPrefs p, int what) {
					if(what == PounderPrefs.DISPLAY_SCRIPT_CHANGED)
						viewRecordedScriptMenuItem.setSelected(p.getDisplayScript());
				}
			};
	}

	protected void addCloseListener() {
		addWindowListener(new java.awt.event.WindowAdapter() {
				public void windowClosing(java.awt.event.WindowEvent e) {
					closeRequested();
				}
			});
	}

	/** Override dispose() to make sure everything is stopped.  This
	 * is very important because if we don't call stop(), recording or
	 * playback threads may still be lurking around.  Save preferences
	 * to system. **/
	public void dispose() {
		if(model.getPreferences().getSavePrefsOnExit())
			model.getPreferences().saveDataToSystem();
		controller.getStopAction().actionPerformed(null);
		super.dispose();
	}

	/** Check if save needed and close if desired. **/
	public void closeRequested() {
		controller.getStopAction().actionPerformed(null);

		if(model.isSaveNeeded()) {
			String message = Strings.getString("SaveConfirmation");
			String title = Strings.getString("SaveConfirmationDialogTitle");
			int result = JOptionPane.showConfirmDialog(PounderFrame.this, message, title, JOptionPane.YES_NO_CANCEL_OPTION);
			switch(result) {
			case JOptionPane.YES_OPTION:
				controller.getSaveAction().actionPerformed(null);
				dispose();
				break;
			case JOptionPane.CANCEL_OPTION:
				return;
			case JOptionPane.NO_OPTION:
				dispose();
				break;
			}
		}
		else 
			dispose();
	}

	//We MUST keep around this reference, or else it will get GC'd!
	protected FileModelListener titleChangeListener;

	protected void addTitleChangeListener() {
		titleChangeListener = new FileModelListener() {
				public void fileModelChanged(String what) {
					File f = model.getFileModel().getFile();
					if(f == null) {
						String untitledString = Strings.getString("Untitled");
						setTitle(untitledString);
					}
					else
						setTitle(f.getName());
				}
			};
		model.getFileModel().addListener(titleChangeListener);
	}

	protected JMenu buildFileMenu() {
		String fileString = Strings.getString("File");
		JMenu ret = new JMenu(fileString);
		ret.setMnemonic(KeyEvent.VK_F);

		ret.add(controller.getOpenAction());
		ret.add(controller.getSaveAction());
		ret.add(controller.getSaveAsAction());
		ret.add(controller.getCloseAction());

		return ret;
	}

	protected JMenu buildEditMenu() {
		String editString = Strings.getString("Edit");
		JMenu ret = new JMenu(editString);
		ret.setMnemonic(KeyEvent.VK_E);

		ret.add(controller.getEditPreferencesAction());

		return ret;
	}

	protected JMenu buildViewMenu() {
		String viewString = Strings.getString("View");
		JMenu ret = new JMenu(viewString);
		ret.setMnemonic(KeyEvent.VK_V);

		ret.add(controller.getViewCommentAction());

		String recordedScriptString = Strings.getString("RecordedScript");
		viewRecordedScriptMenuItem = new JCheckBoxMenuItem(recordedScriptString);
		viewRecordedScriptMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					model.getPreferences().setDisplayScript(viewRecordedScriptMenuItem.isSelected());
				}
			});
		viewRecordedScriptMenuItem.setSelected(model.getPreferences().getDisplayScript());
		ret.add(viewRecordedScriptMenuItem);

		return ret;
	}

	public PounderPanel getPounderPanel() {
		return pounderPanel;
	}

	protected JMenu buildHelpMenu() {
		String helpString = Strings.getString("Help");
		JMenu ret = new JMenu(helpString);
		ret.setMnemonic(KeyEvent.VK_H);
				
		ret.add(controller.getAboutAction());

		return ret;
	}

	protected void initMenu() {
		JMenuBar menuBar = new JMenuBar();

		menuBar.add(buildFileMenu());
		menuBar.add(buildEditMenu());
		menuBar.add(buildViewMenu());
		menuBar.add(buildHelpMenu());

		setJMenuBar(menuBar);
	}

	protected void initComponents() {
		getContentPane().removeAll();
		getContentPane().setLayout(new GridBagLayout());

		int bs = BORDER_SPACING;

		pounderPanel = new PounderPanel(controller, model);
		getContentPane().add(pounderPanel, new GridBagConstraints(0,0,1,1,1.0,1.0,GridBagConstraints.NORTH,GridBagConstraints.BOTH,new Insets(bs,bs,bs,bs), 0, 0));
		validate();
	}

	/*
	protected static class TestTheme extends javax.swing.plaf.metal.DefaultMetalTheme {
			//yellow			return new javax.swing.plaf.ColorUIResource(255, 255, 102);
			//purlple			return new javax.swing.plaf.ColorUIResource(102, 51, 153); //purple

		public javax.swing.plaf.ColorUIResource getPrimary1() {
			return new javax.swing.plaf.ColorUIResource(102, 51, 153); //purple
		}


		public javax.swing.plaf.ColorUIResource getPrimary2() {
			return new javax.swing.plaf.ColorUIResource(153, 102, 255); //lightish purple
		}


		public javax.swing.plaf.ColorUIResource getPrimary3() {
			return new javax.swing.plaf.ColorUIResource(102, 51, 153); //purple
		}

		public javax.swing.plaf.ColorUIResource getSecondary1() {
			return new javax.swing.plaf.ColorUIResource(255, 204, 102); //orangish peach
		}


		public javax.swing.plaf.ColorUIResource getSecondary2() {
			return new javax.swing.plaf.ColorUIResource(158, 134, 168); //button text and outline when disabled
		}

		public javax.swing.plaf.ColorUIResource getSecondary3() {
			return new javax.swing.plaf.ColorUIResource(249, 229, 199); //light peach ; background
		}


	}
	*/

	public static void main(String[] args) throws Exception {
		//		javax.swing.plaf.metal.MetalLookAndFeel laf = new javax.swing.plaf.metal.MetalLookAndFeel();
		//		laf.setCurrentTheme(new TestTheme());
		//		javax.swing.UIManager.setLookAndFeel(laf);

		//		java.util.Locale.setDefault(new java.util.Locale("pt", "PT"));

		SystemExitWatcher.instantiate();
		PounderFrame pf = new PounderFrame(new PounderModel(true));
		pf.setVisible(true);
	}
}
