package com.mtp.pounder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

import org.w3c.dom.Node;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.swing.text.PlainDocument;
import javax.swing.text.BadLocationException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.stream.StreamResult;

import javax.xml.transform.dom.DOMSource;


public class PounderWriter implements PounderConstants {

	public PounderWriter() {
	}

	protected Node buildCommentXMLElement(PlainDocument comment, Document doc) throws IOException {
		try {
			Element ret = doc.createElement("Comment");
			ret.setAttribute("text", comment.getText(0, comment.getLength()));
			return ret;
		}
		catch(BadLocationException exc) {
			IOException ioexc = new IOException("Error building XML node for comment");
			throw (IOException)ioexc.initCause(exc);
		}
	}

	protected Element buildXML(PounderModel model) throws IOException, ParserConfigurationException {
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    DocumentBuilder db = dbf.newDocumentBuilder();
    Document doc = db.newDocument();
    Element xml =  doc.createElement("PounderRecording");
		xml.setAttribute("version", FILE_FORMAT_VERSION);

		TestInstanceFactory tif = model.getTestInstanceFactory();
		if(tif != null)
			xml.appendChild(tif.toXML(doc));

		xml.appendChild(buildCommentXMLElement(model.getComment(), doc));
		model.getRecord().addItemsAsXML(xml, doc);
		return xml;
	}

	public void write(PounderModel model, File f) throws IOException, TransformerException, ParserConfigurationException {
		Node xml = buildXML(model);

    FileOutputStream stream = null;
    try {
      TransformerFactory tf = TransformerFactory.newInstance();
      Transformer t = tf.newTransformer();
      t.setOutputProperty(OutputKeys.INDENT, "yes");
      //      t.setOutputProperty(OutputKeys.ENCODING, "UTF-16");
      DOMSource source = new DOMSource(xml);
      stream = new FileOutputStream(f);
      StreamResult result = new StreamResult(stream);
      t.transform(source, result);
    }
    finally {
      if(stream != null) {
        stream.flush();
        stream.close();
      }
    }
	}

}
