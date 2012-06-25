package com.mtp.pounder;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;

import java.util.List;
import java.util.Vector;

import java.net.URL;

import java.awt.Component;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import javax.swing.text.PlainDocument;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Document;

import org.xml.sax.SAXException;

import java.lang.reflect.InvocationTargetException;

import com.mtp.gui.WindowWatcher;

import com.mtp.i18n.Strings;

/**

Reads data necessary for a Pounding from an InputStream.

@author Matthew Pekar

**/
public class PounderReader implements PounderConstants {

	/** Default constructor. **/
	public PounderReader() {
	}

	/** Read data from given File and place it in given
	 * PounderModel. **/
	public void readToModel(PounderModel pm, File f) throws Exception {
		PounderData pd = read(new FileInputStream(f));
		PounderModel ret = new PounderModel();

		if(pd.testInstanceFactory != null)
			pm.getLoadedTestObjects().addElement(pd.testInstanceFactory.getSetupClass());

		pm.setTestInstanceFactory(pd.testInstanceFactory);
		pm.getRecord().clear();
		pm.getRecord().addItems(pd.items);
		pm.getFileModel().setFile(f);

		PlainDocument comment = pm.getComment();
		comment.remove(0, comment.getLength());
		if(pd.comment != null)
			comment.insertString(0, pd.comment, null);
	}

	/** Creates a FileInputStream and calls read() on it. **/
	public PounderModel readModel(File f) throws Exception {
		PounderModel ret = readModel(new FileInputStream(f));
		return ret;
	}

	/** Reads PounderModel from XML data on given InputStream. **/
	public PounderModel readModel(InputStream in) throws Exception {
		PounderData pd = read(in);
		PounderModel ret = new PounderModel();

		if(pd.testInstanceFactory != null)
			ret.getLoadedTestObjects().addElement(pd.testInstanceFactory.getSetupClass());

		ret.setTestInstanceFactory(pd.testInstanceFactory);
		ret.getRecord().addItems(pd.items);

		PlainDocument comment = ret.getComment();
		comment.remove(0, comment.getLength());
		if(pd.comment != null)
			comment.insertString(0, pd.comment, null);

		return ret;
	}

	/** Returns data read from given File. **/
	public PounderData read(File f) throws Exception {
		return read(new FileInputStream(f));
	}

	/** Returns data read from given URL. **/
	public PounderData read(URL url) throws Exception {
		return read(url.openStream());
	}

	/** Make sure we are reading a supported version. **/
	protected void checkVersion(Element root) throws InvalidVersionException {
		if(! root.hasAttribute("version"))
			throw new InvalidVersionException(Strings.getString("UnknownVersion"));

		if(! root.getAttribute("version").equals(FILE_FORMAT_VERSION))
			throw new InvalidVersionException(Strings.getString("VersionInvalid: ") + root.getAttribute("version"));
	}

	/** Parse the comment and add it to the data. **/
	protected void parseComment(PounderData data, Element e) {
		String comment = e.getAttribute("text");
		data.comment = comment;
	}

	/** Returns data read from given InputStream. **/
	public PounderData read(InputStream in) throws Exception {
		PounderData ret = new PounderData();
		PounderPrefs prefs = new PounderPrefs();
		ComponentIdentifierFactory componentIdentifierFactory = new ComponentIdentifierFactory();

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(in);
		Element node = doc.getDocumentElement();
		checkVersion(node);
		NodeList l = node.getChildNodes();
		for(int i=0;i < l.getLength();i++) {
			Node n = l.item(i);
			if(n.getNodeType() == Node.ELEMENT_NODE) {
				if(n.getNodeName().equals("SetupInitializer")) { //a bit of cruft here, SetupInitializer was renamed to TestInstanceFactory
					ret.testInstanceFactory = new TestInstanceFactory((Element)n);
				}
				else if(n.getNodeName().equals("Comment")) {
					parseComment(ret, (Element)n);
				}
				else {
					RecordingItem item = RecordingItem.instantiate((Element)n, prefs, componentIdentifierFactory);
					ret.items.add(item);
				}
			}
		}

		return ret;
	}

	/** Little utility class for passing around retrieved data. **/
	public static class PounderData {

		public TestInstanceFactory testInstanceFactory;
		public List items;
		public String comment;

		public PounderData() {
			this.testInstanceFactory = null;
			this.items = new Vector();
			this.comment = null;
		}

		public PounderData(TestInstanceFactory tif, List items, String comment) {
			this.testInstanceFactory = tif;
			this.items = items;
			this.comment = comment;
		}
	}

}

