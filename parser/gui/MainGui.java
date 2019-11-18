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

import data.ArticleObj;
import dom.Parser;
import sax.BibSaxHandler;
import valid.XmlValidator;


public class MainGui {
	
	private final String sPath = "C:\\Users\\Leo Mada\\Desktop\\Practica\\MSc\\XML\\";
	private final String sXmlFile = "Bib.xml";
	private final String sSchemaFile = "Bib.Schema.xsd";
	
	// +++ IO +++
	public File GetFile() {
		return new File(sPath + sXmlFile);
	}
	public File GetSchema() {
		return new File(sPath + sSchemaFile);
	}
	
	// +++ Parse XML +++
	public void Process() {
		this.Process(this.GetFile());
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
				for(final ArticleObj article : handler.GetArticles()) {
					System.out.println(article.toString());
					System.out.println("\n");
				}
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
	
	// +++ Parse DOM +++
	public void ParseDom() {
		this.ParseDom(this.GetFile());
	}
	public void ParseDom(final File file) {
		final Parser parserDom = new Parser();
		parserDom.Parse(file);
	}
	
	// +++ Validate XML +++
	public boolean Validate() {
		return this.Validate(this.GetFile(), this.GetSchema());
	}
	public boolean Validate(final File fileXML, final File fileSchema) {
		final XmlValidator validator = new XmlValidator();
		return validator.Validate(fileXML, fileSchema);
	}
	
	// ++++++++++ MAIN +++++++++

	public static void main(String[] args) {
		final MainGui gui = new MainGui();
		
		// SAX Parser
		gui.Process();
		
		if(gui.Validate()) {
			System.out.println("Xml was validated");
		} else {
			System.out.println("Xml was NOT validated!");
		}
		
		// DOM Parser
		gui.ParseDom();
	}

}
