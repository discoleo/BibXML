package valid;

import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamReader;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;


public class SchemaErrorHandler implements ErrorHandler {
	
	private XMLStreamReader reader;
	
	private boolean isError = false;

	public SchemaErrorHandler(XMLStreamReader reader) {
		this.reader = reader;
	}
	
	// +++++++++ MEMBER FUNCTIONS ++++++++++
	
	public boolean IsError() {
		return isError;
	}

	@Override
	public void error(SAXParseException e) {
		warning(e);
	}

	@Override
	public void fatalError(SAXParseException e) throws SAXException {
		warning(e);
	}

	@Override
	public void warning(final SAXParseException e) {
		isError = true;
		final int type = reader.getEventType();
		// System.out.println(type);
		
		if(type == 4) {
			final Location loc = reader.getLocation();
			System.out.println("Err: Line = " + loc.getLineNumber() +
					"; Col = " + loc.getColumnNumber() +
					"; Type = " + e.getMessage());
		} else if(type == 2) {
			final Location loc = reader.getLocation();
			System.out.println("Err: " + reader.getLocalName()
					+ "; Line = " + loc.getLineNumber() +
					"; Col = " + loc.getColumnNumber() +
					"; Type = " + e.getMessage());
		} else {
			System.out.println(reader.getLocalName());
	        e.printStackTrace(System.out);
		}
	}
}
