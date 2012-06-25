package com.mtp.pounder;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import com.mtp.pounder.PounderModel;
import com.mtp.pounder.PounderPrefs;
import com.mtp.pounder.VerbatimRecordingOptions;
import com.mtp.pounder.controller.PounderController;


public class CommandLineRecorder {
	
	public static PounderModel m;
	
	private static class FileWritter implements Runnable {
		private String name;
		public FileWritter(final String name) {
			this.name = name;
		}
		public void run() {
			//m.recordingThread.recording.terminate();
			PounderController pc = new PounderController(m);
			m.getPreferences().saveDataToSystem();
			//pc.getSaveAction().actionPerformed(null);
			try {
				File f = new File(name);
				new PounderWriter().write(m, f);
				m.getFileModel().setFile(f);
				flush();
			
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			pc.getStopAction().actionPerformed(null);

			//System.out.println("executed");
			
		}
		
		private void flush() {
			Enumeration e = m.record.elements();
			RecordingItem item;
			ItemsWriter iw = new ItemsWriter(name + ".record");
			while(e.hasMoreElements()) {
			
				item = (RecordingItem)e.nextElement();
				try {
					iw.writeItem(item, true);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			try {
				iw.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * console version pounder launcher
	 * to run it, two arguments should be passed
	 * 1: main class to launch of the recording application
	 * 2: full path name of the file to save script into
	 * @param args
	 */

	public static void main(String[] args) {
		
		
		FileWritter fw = new FileWritter (args[1]);
		Thread shutdownHookThread = new Thread (fw);
		Runtime.getRuntime().addShutdownHook(shutdownHookThread);
		
		
		final VerbatimRecordingOptions vro = new VerbatimRecordingOptions();
		vro.doKeyEvents = true;
		vro.doMouseDragEvents = true;
		vro.doMouseInputEvents = true;
		vro.doMouseMotionEvents = true;
		vro.doWindowEvents = true;
		
		final PounderPrefs prefs = new PounderPrefs();
		prefs.setVerbatimRecordingOptions(vro);
		prefs.saveDataToSystem();
		
		
		m = new PounderModel();
		m.setTestClass(args[0]);
		m.setPreferences(prefs);
		m.beginVerbatimRecording();
	
				
		//m.recordingThread.windowWatcher.
		//m.recordingFinished();
		
		
	}

}
