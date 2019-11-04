package gui;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import sax.BibSaxHandler;


public class MainGui {
	
	private final String sPath = "C:\\Users\\Leo Mada\\Desktop\\Practica\\MSc\\XML\\";
	private final String sFile = "Bib.xml";
	
	public void Process() {
		this.Process(sPath + sFile);
	}
	public void Process(final String sFile) {
		this.Process(new File(sFile));
	}
	public void Process(final File file) {
		try {
			final InputSource inSrc = new InputSource(new FileInputStream(file));
			
			final BibSaxHandler handler = new BibSaxHandler();
			final XMLReader reader = this.GetXMLSaxReader(handler);
			if(reader != null) {
				reader.parse(inSrc);
				// TODO: get parsed content
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
		System.out.println("Finished parsing!");
	}
	
	protected XMLReader GetXMLSaxReader(final BibSaxHandler handler) {
		final SAXParserFactory spf = SAXParserFactory.newInstance();
		final SAXParser sp;
		final XMLReader reader;
		
		try {
			sp = spf.newSAXParser();
			reader = sp.getXMLReader();
			reader.setContentHandler(handler);
			return reader;
		} catch(SAXException | ParserConfigurationException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// ++++++++++ MAIN +++++++++

	public static void main(String[] args) {
		final MainGui gui = new MainGui();
		
		gui.Process();
	}

}
