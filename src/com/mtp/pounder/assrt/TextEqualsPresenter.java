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

Presents a model for editing a TextEqualsItem. 

@author Matthew Pekar

**/
public class TextEqualsPresenter extends AssertPresenter {

	protected WindowIdentifierPresenter windowIdentifierPresenter;
	protected DocumentStringModel componentNameModel;
	protected DocumentStringModel desiredValueModel;

	public TextEqualsPresenter() {
		this(new RecordingRecord());
	}

	public TextEqualsPresenter(RecordingRecord record) {
		super(record);

		this.windowIdentifierPresenter = new WindowIdentifierPresenter();
		this.componentNameModel = new DocumentStringModel();
		this.desiredValueModel = new DocumentStringModel();
	}

	public RecordingItem getAssertionItem() throws Exception {
		String componentName = componentNameModel.getString();
		if(componentName == null  || componentName.length() <= 0)
			throw new IllegalStateException("Component name must be non-null");

		String desiredValue = desiredValueModel.getString();

		if(windowIdentifierPresenter.getSetTitleAction().isEnabled()) {
			String title = windowIdentifierPresenter.getTitleModel().getString();
			if(title.length() <= 0)
				throw new Exception("Title must be non-null");

			return new TextEqualsItem(title, componentName, desiredValue);
		}
		else {
			int id = windowIdentifierPresenter.getIdModel().getValue();
			if(id < 0)
				throw new Exception("Illegal window ID value");

			return new TextEqualsItem(id, componentName, desiredValue);
		}
	}

	public DocumentStringModel getComponentNameModel() {
		return componentNameModel;
	}

	public DocumentStringModel getDesiredValueModel() {
		return desiredValueModel;
	}

	public WindowIdentifierPresenter getWindowIdentifierPresenter() {
		return windowIdentifierPresenter;
	}

	public String getAssertTitle() {
		return Strings.getString("AssertTextEquals");
	}

}
