package com.mtp.gui.widget;

import javax.swing.JComboBox;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UIManager;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

import com.mtp.gui.SystemExitWatcher;

/**

Combo box for selecting the look and feel

@author Matthew Pekar

**/
public class LookAndFeelComboBox extends JComboBox {

	public LookAndFeelComboBox() {
		init();

		addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					try {
						if(e.getStateChange() != ItemEvent.SELECTED)
							return;

						UIManager.setLookAndFeel(((SetLookAndFeelItem)e.getItem()).getClassName());
						SystemExitWatcher systemExitWatcher = SystemExitWatcher.getInstance();
						if(systemExitWatcher != null)
							systemExitWatcher.updateUIs();
					}
					catch(Exception exc) {
						exc.printStackTrace();
					}
				}
			});
	}

	protected static class SetLookAndFeelItem {
				
		protected LookAndFeelInfo info;

		public SetLookAndFeelItem(LookAndFeelInfo info) {
			this.info = info;
		}

		public String getClassName() {
			return info.getClassName();
		}

		public String toString() {
			return info.getName();
		}

	}

	/** Retrieve available look and feels. **/
	protected void init() {
		LookAndFeelInfo[] lfs = UIManager.getInstalledLookAndFeels();
		for(int i=0;i < lfs.length;i++) {
			addItem(new SetLookAndFeelItem(lfs[i]));
		}
	}
		
}
