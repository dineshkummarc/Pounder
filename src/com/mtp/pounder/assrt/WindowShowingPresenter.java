package com.mtp.pounder.assrt;

import com.mtp.pounder.RecordingItem;
import com.mtp.pounder.RecordingRecord;

import com.mtp.model.IntegerModel;
import com.mtp.model.DefaultIntegerModel;
import com.mtp.model.DocumentStringModel;

import javax.swing.AbstractAction;

import java.awt.event.ActionEvent;

import com.mtp.i18n.Strings;

/**

Presents a model for editing a WindowShowingItem. 

@author Matthew Pekar

**/
public class WindowShowingPresenter extends AssertPresenter {

	protected WindowIdentifierPresenter windowIdentifierPresenter;

	public WindowShowingPresenter() {
		this(new RecordingRecord());
	}

	public WindowShowingPresenter(RecordingRecord record) {
		super(record);

		this.windowIdentifierPresenter = new WindowIdentifierPresenter();
	}

	public RecordingItem getAssertionItem() throws Exception {
		if(windowIdentifierPresenter.getSetTitleAction().isEnabled()) {
			String title = windowIdentifierPresenter.getTitleModel().getString();
			if(title.length() <= 0)
				throw new Exception(Strings.getString("TitleCannotBeNull"));

			return new WindowShowingItem(title);
		}
		else {
			int val = windowIdentifierPresenter.getIdModel().getValue();
			if(val < 0)
				throw new Exception(Strings.getString("InvalidWindowID"));

			return new WindowShowingItem(val);
		}
	}

	public WindowIdentifierPresenter getWindowIdentifierPresenter() {
		return windowIdentifierPresenter;
	}

	public String getAssertTitle() {
		return Strings.getString("AssertWindowShowing");
	}

}
