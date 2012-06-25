package com.mtp.pounder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ItemsWriter {

	BufferedWriter bw;
	
	public ItemsWriter(final String name) {
		
		try {
			bw = new BufferedWriter(new FileWriter(new File(name)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void writeItem(RecordingItem ri, boolean status) throws IOException {
		bw.write(ri.toString() + " " + status);
		bw.newLine();
	}
	
	public void close() throws IOException {
		bw.flush();
		bw.close();
	}
	
}
