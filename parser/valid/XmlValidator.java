package valid;

import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stax.StAXSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

public class XmlValidator {
	
	public boolean Validate(final File xmlFile, final File schemaFile) {
		final SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		
		try {
			final Schema schema = schemaFactory.newSchema(schemaFile);
			
			final Validator validator = schema.newValidator();
			
			final StreamSource stream = new StreamSource(xmlFile);
			final XMLStreamReader reader = XMLInputFactory.newFactory().createXMLStreamReader(stream);
			final SchemaErrorHandler handlerErr = new SchemaErrorHandler(reader);
			validator.setErrorHandler(handlerErr);
			
            validator.validate(new StAXSource(reader));
            return ! handlerErr.IsError();
		} catch (SAXException e) {
			final String sMsg = e.getMessage();
			System.out.println("SAX Ex: " + sMsg);
			
            e.printStackTrace();
            return false;
        } catch (IOException e) {
			e.printStackTrace();
            return false;
		} catch (XMLStreamException e) {
			e.printStackTrace();
            return false;
		} catch (FactoryConfigurationError e) {
			e.printStackTrace();
            return false;
		}
	}
}
