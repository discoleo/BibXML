package gui;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.dom4j.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import data.AffiliationObj;
import data.ArticleObj;
import data.AuthorObj;
import data.GetMapsINTF;
import dom.Parser;
import dom.Updater;
import sax.BibSaxHandler;
import valid.XmlValidator;


public class MainGui {
	
	// ++++++++++ version 3.0-very-pre-alfa +++++++++
	
	private final String sPath = "...\\";
	private final String sXmlFile = "Bib.xml";
	// do NOT override original
	private final String sXmlUpdateFile = "Bib.update.xml";
	private final String sSchemaFile = "Bib.Schema.xsd";
	
	private final Parser parserDom = new Parser();
	
	// +++ IO +++
	public File GetFile() {
		return new File(sPath + sXmlFile);
	}
	public File GetUpdateFile() {
		return new File(sPath + sXmlUpdateFile);
	}
	public File GetSchema() {
		return new File(sPath + sSchemaFile);
	}
	
	// Print Articles
	public void Print(final Vector<ArticleObj> vArticles) {
		for(final ArticleObj article : vArticles) {
			System.out.println(article.toString());
			System.out.println("\n");
		}
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
				this.Print(handler.GetArticles());
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
	public Document ParseDom() {
		return this.ParseDom(this.GetFile());
	}
	public Document ParseDom(final File file) {
		final Document doc = parserDom.Parse(file);
		//
		this.Print(parserDom.GetArticles());
		return doc;
	}
	
	// +++ Update XML +++
	// TODO: implement more update functions
	public boolean Update(final Document doc, final GetMapsINTF maps) {
		return this.Update(doc, maps, this.GetUpdateFile());
	}
	public boolean Update(final Document doc, final GetMapsINTF maps, final File fileXML) {
		final Updater updater = new Updater(doc, maps);
		// Test
		final AuthorObj author = new AuthorObj();
		author.idAuthor = 4;
		author.sGivenName = "_"; // TODO: escape?
		author.sName = "_";
		//
		final AffiliationObj affiliation = new AffiliationObj();
		affiliation.sInstitution = "Gottlob Farm";
		affiliation.sDepartment = "Dept. of Cardiac & Vascular Development in \"Large Mammals\"";
		affiliation.sType = "Galactic Research Institute for Pigs";
		affiliation.sAdress = "Branchial Arch 42";
		//
		updater.AddAffil(author, affiliation);
		updater.Write(fileXML);
		return false; // TODO
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
		final Document doc = gui.ParseDom();
		// Update xml file
		gui.Update(doc, gui.parserDom);
	}

}
