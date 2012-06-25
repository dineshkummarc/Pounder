package com.mtp.pounder.assrt;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTextField;

import junit.framework.Test;
import junit.framework.TestSuite;

import java.util.Collection;
import java.util.Vector;

import com.mtp.pounder.RecordingItemTest;
import com.mtp.pounder.PounderPrefs;
import com.mtp.pounder.PlaybackException;

import com.mtp.gui.WindowWatcher;

public class TextEqualsItemTest extends WindowAssertItemTest {

	public TextEqualsItemTest(String name) {
		super(name);
	}

	public static Test suite() {
		return new TestSuite(TextEqualsItemTest.class);
	}

	protected WindowAssertItem buildTestItem(int windowID) {
		return new TextEqualsItem(windowID, "address", "110 W");
	}

	protected WindowAssertItem buildTestItem(String windowTitle) {
		return new TextEqualsItem(windowTitle, "address", "110 W");
	}

	public Collection buildTestItems() throws Exception {
		Collection ret = new Vector();
		ret.add(buildTestItem(32));
		ret.add(buildTestItem("Foo"));
		return ret;
	}
	
	public void testDifferentComponentNameEquals() throws Exception {
		TextEqualsItem item1 = new TextEqualsItem("foo", "address", "bar");
		TextEqualsItem item2 = new TextEqualsItem("foo", "city", "bar");
		assertTrue(! item1.equals(item2));
	}

	public void testEqualsWithNullDesiredValue() throws Exception {
		TextEqualsItem item1 = new TextEqualsItem("foo", "city", "foo");
		TextEqualsItem item2 = new TextEqualsItem("foo", "city", null);
		assertTrue(! item1.equals(item2));
		assertTrue(! item2.equals(item1));
	}

	public void testDifferentDesiredValueEquals() throws Exception {
		TextEqualsItem item1 = new TextEqualsItem("foo", "city", "foo");
		TextEqualsItem item2 = new TextEqualsItem("foo", "city", "bar");
		assertTrue(! item1.equals(item2));
	}

	public void testPlaybackComponentDoesNotExist() throws Exception {
		JFrame f = new JFrame();
		f.setVisible(true);
		windowWatcher.waitTillWindowPresent(f);

		boolean playbackExceptionThrown = false;
		try {
			new TextEqualsItem(0, "foo", "bar").playback(windowWatcher, new PounderPrefs());
		}
		catch(PlaybackException exc) {
			playbackExceptionThrown = true;
		}

		assertTrue(playbackExceptionThrown);
	}

	public void testPlaybackComponentNotJTextComponent() throws Exception {
		String name = "billy";

		JFrame f = new JFrame();
		JButton naughtyButton = new JButton();
		naughtyButton.setName(name);
		f.getContentPane().add(naughtyButton);
		f.setVisible(true);
		windowWatcher.waitTillWindowPresent(f);

		boolean playbackExceptionThrown = false;
		try {
			new TextEqualsItem(0, "billy", "bar").playback(windowWatcher, new PounderPrefs());
		}
		catch(PlaybackException exc) {
			playbackExceptionThrown = true;
		}

		assertTrue(playbackExceptionThrown);
	}

	public void testPlaybackExceptionDesiredValueNotPresent() throws Exception {
		JFrame f = new JFrame();
		JTextField field = new JTextField("twinkie");
		field.setName("button");
		f.getContentPane().add(field);
		f.setVisible(true);
		windowWatcher.waitTillWindowPresent(f);

		boolean playbackExceptionThrown = false;
		try {
			new TextEqualsItem(0, "button", "doughnut").playback(windowWatcher, new PounderPrefs());
		}
		catch(PlaybackException exc) {
			playbackExceptionThrown = true;
		}

		assertTrue(playbackExceptionThrown);
	}

	public void testPlaybackExceptionDesiredValueNull() throws Exception {
		JFrame f = new JFrame();
		JTextField field = new JTextField();
		field.setText(null);
		field.setName("button");
		f.getContentPane().add(field);
		f.setVisible(true);
		windowWatcher.waitTillWindowPresent(f);

		new TextEqualsItem(0, "button", null).playback(windowWatcher, new PounderPrefs());
	}

	public void testPlaybackExceptionDesiredValueNullAndNotPresent() throws Exception {
		JFrame f = new JFrame();
		JTextField field = new JTextField("twinkie");
		field.setName("button");
		f.getContentPane().add(field);
		f.setVisible(true);
		windowWatcher.waitTillWindowPresent(f);

		boolean playbackExceptionThrown = false;
		try {
			new TextEqualsItem(0, "button", null).playback(windowWatcher, new PounderPrefs());
		}
		catch(PlaybackException exc) {
			playbackExceptionThrown = true;
		}

		assertTrue(playbackExceptionThrown);
	}

}






