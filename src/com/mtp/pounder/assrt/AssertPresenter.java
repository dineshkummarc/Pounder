package com.mtp.pounder.assrt;

import com.mtp.model.StatusModel;

import com.mtp.pounder.RecordingItem;
import com.mtp.pounder.RecordingRecord;

/**

Abstract class for Presenter's for an assertion item.

@author Matthew Pekar

**/
public abstract class AssertPresenter {

	protected RecordingRecord record;
	protected StatusModel statusModel;

	public AssertPresenter(RecordingRecord record) {
		this.record = record;
		this.statusModel = new StatusModel();
		this.statusModel.setStatus(" ");
	}

	public StatusModel getStatusModel() {
		return statusModel;
	}

	public RecordingRecord getRecord() {
		return record;
	}

	public abstract String getAssertTitle();

	public abstract RecordingItem getAssertionItem() throws Exception;

}
