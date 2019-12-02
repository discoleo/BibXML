package dom;

import java.io.File;
import java.util.List;
import java.util.Vector;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import data.AffiliationMap;
import data.ArticleObj;
import data.AuthorObj;
import data.AuthorsDict;
import data.JournalMap;
import data.JournalObj;


public class Parser {
	
	// Affiliations
	protected static final String sXP_INSTITUTIONS = "/BibManagement/Affiliations/Institutions/Institution";
	protected static final String sXP_DEPARTMENTS  = "/BibManagement/Affiliations/Affiliation";
	
	// Authors
	protected static final String sXP_AUTHORS  = "/BibManagement/Authors/Author";
	// Journals
	protected static final String sXP_JOURNALS = "/BibManagement/Journals/Journal";
	// Articles
	protected static final String sXP_ARTICLES = "/BibManagement/Articles/Article";
	
	protected final AffiliationMap mapAffiliations = new AffiliationMap();
	protected final JournalMap mapJournals = new JournalMap();
	protected final AuthorsDict mapAuthors = new AuthorsDict(mapAffiliations, mapJournals);

	private final Vector<ArticleObj> vArticles = new Vector<> ();
	
	// +++++++++++ MEMBER FUNCTIONS +++++++++++++
	
	public Vector<ArticleObj> GetArticles() {
		return vArticles;
	}
	
	public void Parse(final File file) {
		final SAXReader reader = new SAXReader();
		try {
			final Document document = reader.read(file);
			
			// Institutions
			this.ExtractInstitutions(document);
			this.ExtractAffiliations(document);
			
			// Authors
			this.ExtractAuthors(document);
			
			// Journals
			this.ExtractJournals(document);
			
			// Articles
			this.ExtractArticles(document);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	protected void ExtractInstitutions(final Document document) {
		final List<Node> listInst = document.selectNodes(sXP_INSTITUTIONS);
		for(final Node nodeInst : listInst) {
			final int idInst = Integer.parseInt(nodeInst.valueOf("@idInst"));
			final String sInstitution = nodeInst.valueOf("Name");
			mapAffiliations.PutInstitute(idInst, sInstitution);
			
			// System.out.println("" + idInst + " = " + sInstitution);
		}
	}
	
	protected void ExtractAffiliations(final Document document) {
		final List<Node> listInst = document.selectNodes(sXP_DEPARTMENTS);
		for(final Node nodeInst : listInst) {
			final int idAffil = Integer.parseInt(nodeInst.valueOf("@idAffil"));
			final String sDepartment = nodeInst.valueOf("Department");
			final int idInst = Integer.parseInt(nodeInst.valueOf("Institution/@idInstRef"));
			
			mapAffiliations.PutDepartment(idAffil, idInst, sDepartment);
		}
	}
	
	protected void ExtractAuthors(final Document document) {
		final List<Node> listAuthors = document.selectNodes(sXP_AUTHORS);
		System.out.println("\nExtracting Authors:");
		
		for(final Node nodeAuthor : listAuthors) {
			final int idAuthor = Integer.parseInt(nodeAuthor.valueOf("@idAuthor"));
			final String sName = nodeAuthor.valueOf("Name");
			final String sGName = nodeAuthor.valueOf("GivenName");
			
			final AuthorObj author = new AuthorObj();
			author.sName = sName;
			author.sGivenName = sGName;
			mapAuthors.put(idAuthor, author);
			
			for(final Node nodeAffil : nodeAuthor.selectNodes("Affiliations/Affiliation")) {
				final int idAffil = Integer.parseInt(nodeAffil.valueOf("@idAffilRef"));
				mapAuthors.AddAffil(author, idAffil);
			}
			
			System.out.println("" + idAuthor + " = " + author.toString());
		}
	}
	
	protected void ExtractJournals(final Document document) {
		final List<Node> listJournals = document.selectNodes(sXP_JOURNALS);
		for(final Node nodeJournal : listJournals) {
			final int idJournal = Integer.parseInt(nodeJournal.valueOf("@idJ"));
			final String sName = nodeJournal.valueOf("Name");
			final String sType = nodeJournal.valueOf("Type");
			final String sISSN = nodeJournal.valueOf("ISSN");
			
			final JournalObj journal = new JournalObj();
			journal.sName = sName;
			journal.sType = sType;
			journal.sISSN = sISSN;
			
			mapJournals.put(idJournal, journal);
		}
	}
	
	protected void ExtractArticles(final Document document) {
		final List<Node> listArticles = document.selectNodes(sXP_ARTICLES);
		for(final Node nodeArticle : listArticles) {
			final int idJournal = Integer.parseInt(nodeArticle.valueOf("ArticleJournal/Journal/@idJRef"));
			final JournalObj journal = mapJournals.get(idJournal);
			
			final String sTitle = nodeArticle.valueOf("Title");
			final int iYear = Integer.parseInt(nodeArticle.valueOf("Date/Year"));
			
			final ArticleObj article = new ArticleObj();
			vArticles.add(article);
			article.sTitle = sTitle;
			article.iYear = iYear;
			article.journal = journal;
			
			for(final Node nodeAuthor : nodeArticle.selectNodes("Authors/Author/@idAuthorRef")) {
				final int idAuthor = Integer.parseInt(nodeAuthor.getStringValue());
				final AuthorObj author = mapAuthors.get(idAuthor);
				if(author != null) {
					article.AddAuthor(author);
				}
			}
		}
	}
}
