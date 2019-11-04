package sax;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class BibSaxHandler extends DefaultHandler {
	
	private BIB_NODES node = BIB_NODES.ROOT;
	
	private final AuthorsDict dictAuthors = new AuthorsDict();
	
	private ArticleObj article = null;

	@Override
	public void startElement(final String uri,
			String localName, String qName, final Attributes attributes) throws SAXException {
		final BIB_NODES nodeE = BIB_NODES.GetNode(qName, node);
		if(nodeE != null) {
			node = nodeE;
			
			switch(node) {
			// +++ Articles
			case ARTICLES_LIST : {
				System.out.println("\n\nArticle List:\n\n");
				break; }
			// +++ Author
			case AUTHOR: {
				final String sRef = attributes.getValue("idAut");
				dictAuthors.SetID(Integer.parseInt(sRef));
				// System.out.println("Institute: " + sRef);
				break; }
			case AUTHOR_AFFIL : {
				final String sRef = attributes.getValue("idAffilRef");
				final int idAffil = Integer.parseInt(sRef);
				dictAuthors.AddAffil(idAffil);
				break; }
			// +++ Institutes
			case INSTITUTE : {
				final String sID = attributes.getValue("idInst");
				dictAuthors.BuildInstitute(Integer.parseInt(sID));
				break;}
			// +++ Affiliations
			case AFFILIATION : {
				final String sID = attributes.getValue("idAffil");
				dictAuthors.BuildAffil(Integer.parseInt(sID));
				break;}
			case INSTITUTE_REF : {
				final String sRef = attributes.getValue("idInstRef");
				dictAuthors.BuildAffilPutInstitute(Integer.parseInt(sRef));
				// System.out.println("Institute: " + sRef);
				break;}
			// +++ Journals
			case JOURNAL : {
				final String sID = attributes.getValue("idJ");
				dictAuthors.BuildJournal(Integer.parseInt(sID));
				break;}
			// +++ Articles
			case ARTICLE : {
				if(article != null) {
					System.out.println(article.toString());
					System.out.println("\n");
				}
				article = new ArticleObj();
				break; }
			case ARTICLE_AUTHOR : {
				final String sRef = attributes.getValue("idAutRef");
				final int id = Integer.parseInt(sRef);
				article.AddAuthor(dictAuthors.get(id));
				// System.out.println("Author: " + dictAuthors.get(id).toString());
				break; }
			case ARTICLE_JOURNAL_J : {
				final String sRef = attributes.getValue("idJRef");
				final String sJ = dictAuthors.GetJournal(Integer.parseInt(sRef));
				article.sJournal = sJ;
				break; }
			}
		}
	}
	

	@Override
	public void endElement(final String uri, final String localName, final String qName) throws SAXException {
		// quick hack
		if(node.IsNode(qName)) {
			if(node.equals(BIB_NODES.ARTICLE)) {
				if(article != null) {
					System.out.println(article.toString());
					System.out.println("\n");
				}
				article = null;
			}
			
			node = node.GetParent();
		}
	}
	

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		if(node.ReadElement()) {
			// System.out.println("Can read: " + node);
			final String sVal = new String(ch, start, length);
			
			switch(node) {
			// +++ Authors
			case AUTHOR_NAME : {
				dictAuthors.Put(sVal, true);
				break; }
			case AUTHOR_GNAME : {
				dictAuthors.Put(sVal, false);
				break; }
			
			// +++ Institute
			case INSTITUTE_NAME : {
				dictAuthors.BuildInstitute(sVal);
				break; }
			case INSTITUTE_DEPT : {
				dictAuthors.BuildAffilDepartment(sVal);
				break; }

			// +++ Journals
			case JOURNAL_NAME : {
				dictAuthors.BuildJournal(sVal);
				break; }
			
			// +++ Title
			case TITLE : {
				article.sTitle = sVal;
				break; }
			}
			
			// System.out.println(sVal);
		}
	}
}
