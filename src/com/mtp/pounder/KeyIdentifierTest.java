package com.mtp.pounder;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.JDialog;
import javax.swing.JButton;

public class KeyIdentifierTest extends ComponentIdentifierTest {

	public KeyIdentifierTest(String name) {
		super(name);
	}

	public static Test suite() {
		return new TestSuite(KeyIdentifierTest.class);
	}

	protected ComponentIdentifier buildComponentIdentifier(Component c, JFrame f) throws Exception {
		return new KeyIdentifier(c);
	}

	protected String arrayToString(int[] arr) {
		StringBuffer ret = new StringBuffer("[");
		for(int i=0;i < arr.length;i++) {
			ret.append(String.valueOf(arr[i]));
		}
		ret.append("]");
		return ret.toString();
	}

	public void assertEquals(int[] arr1, int[] arr2) throws Exception {
		if(arr1.length != arr2.length)
			throw new Exception("Arrays have different length: " + arrayToString(arr1) + ", " + arrayToString(arr2));

		for(int i=0;i < arr1.length;i++) {
			if(arr1[i] != arr2[i])
				throw new Exception("Arrays have different value: " + arrayToString(arr1) + ", " + arrayToString(arr2));
		}
	}

	public void testGetHeightStopsAtDialog() throws Exception {
		JFrame frame = new JFrame();
		JDialog dialog = new JDialog(frame);
		JButton b = new JButton();
		dialog.getContentPane().add(b);

		KeyIdentifier ki = new KeyIdentifier();
		assertEquals(1, ki.getHeight(dialog));
		assertEquals(5, ki.getHeight(b));
	}

	public void testGetKeyOnDialog() throws Exception {
		JFrame frame = new JFrame();
		JDialog dialog = new JDialog(frame);
		JButton b = new JButton();
		dialog.getContentPane().add(b);

		KeyIdentifier ki = new KeyIdentifier(b);
		int[] desiredResult = {0, 1, 0, 0};
		int[] ugh = ki.key;
		assertEquals(ki.key, desiredResult);
	}

}
