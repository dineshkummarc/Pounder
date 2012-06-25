package com.mtp.gui.widget;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

import com.mtp.model.DefaultIntegerModel;

public class IntegerTextFieldTest extends TestCase {

	public IntegerTextFieldTest(String name) {
		super(name);
	}

	public static Test suite() {
		return new TestSuite(IntegerTextFieldTest.class);
	}

	public void testDefaultConstructor() throws Exception {
		IntegerTextField itf = new IntegerTextField();
		assertEquals("0", itf.getText());
	}

	public void testDefaultIntegerModelConstructor() throws Exception {
		DefaultIntegerModel model = new DefaultIntegerModel(66);
		IntegerTextField itf = new IntegerTextField(model);
		assertEquals("66", itf.getText());
	}

	public void testUpdateModelValueUpdatesText() throws Exception {
		DefaultIntegerModel model = new DefaultIntegerModel(66);
		IntegerTextField itf = new IntegerTextField(model);

		model.setValue(77);

		assertEquals("77", itf.getText());
	}

	public void testUpdateTextUpdatesModelValue() throws Exception {
		DefaultIntegerModel model = new DefaultIntegerModel(66);
		IntegerTextField itf = new IntegerTextField(model);

		itf.setText("77");

		assertEquals(77, model.getValue());
	}

	public void testSetNullTextMakesValueNegativeOne() throws Exception {
		DefaultIntegerModel model = new DefaultIntegerModel(66);
		IntegerTextField itf = new IntegerTextField(model);

		itf.setText("");

		assertEquals(-1, model.getValue());
	}

}
