package sax;

public enum BIB_NODES {
	ROOT("BibManagement", false, null),
	AFFILIATIONS_LIST("Affiliations", false, ROOT),
		INSTITUTIONS("Institutions", false, AFFILIATIONS_LIST),
		INSTITUTE("Institution", false, INSTITUTIONS),
		INSTITUTE_NAME("Name", true, INSTITUTE),
		INSTITUTE_TYPE("Type", true, INSTITUTE),
		INSTITUTE_ADRESS("Adress", true, INSTITUTE),

		AFFILIATION("Affiliation", false, AFFILIATIONS_LIST),
		INSTITUTE_REF("Institution", false, AFFILIATION),
		INSTITUTE_DEPT("Department", true, AFFILIATION),

	AUTHORS_LIST("Authors", false, ROOT),
		AUTHOR("Author", false, AUTHORS_LIST),
		AUTHOR_NAME("Name", true, AUTHOR),
		AUTHOR_GNAME("GivenName", true, AUTHOR),
		AUTHOR_AFFIL_LIST("Affiliations", false, AUTHOR),
		AUTHOR_AFFIL("Affiliation", false, AUTHOR_AFFIL_LIST),

	JOURNALS_LIST("Journals", false, ROOT),
		JOURNAL("Journal", false, JOURNALS_LIST),
		JOURNAL_NAME("Name", true, JOURNAL),
		JOURNAL_TYPE("Type", true, JOURNAL),
		JOURNAL_ISSN("ISSN", true, JOURNAL),

	ARTICLES_LIST("Articles", false, ROOT),
		ARTICLE("Article", false, ARTICLES_LIST),
		ARTICLE_AUTHORS_LIST("Authors", false, ARTICLE),
		ARTICLE_AUTHOR("Author", false, ARTICLE_AUTHORS_LIST),
		TITLE("Title", true, ARTICLE),
		ARTICLE_JOURNAL("ArticleJournal", false, ARTICLE),
		ARTICLE_JOURNAL_J("Journal", false, ARTICLE_JOURNAL),
		DATE("Date", false, ARTICLE),
		DATE_YEAR("Year", true, DATE),
	
	;

	// ++++++++++++++++++
	private final BIB_NODES parent;
	private final String sNode;
	private final boolean doRead;
	// ++++++++++++++++++
	private BIB_NODES(final String node, final BIB_NODES parent) {
		this.parent = parent;
		this.sNode  = node;
		this.doRead = true;
	}
	private BIB_NODES(final String node, final boolean readElement, final BIB_NODES parent) {
		this.parent = (parent == null) ? this : parent;
		this.sNode  = node;
		this.doRead = readElement;
	}
	// ++++++++++++++++++
	public static BIB_NODES GetNode(final String node, final BIB_NODES parent) {
		for(final BIB_NODES nodeE : BIB_NODES.values()) {
			if( ! parent.equals(nodeE.parent)) {
				continue;
			}
			if(node.equals(nodeE.sNode)) {
				return nodeE;
			}
		}
		return null;
	}
	
	public String GetNode() {
		return sNode;
	}
	public BIB_NODES GetParent() {
		return parent;
	}

	public boolean ReadElement() {
		return doRead;
	}
	public boolean IsNode(final String str) {
		return this.sNode.equalsIgnoreCase(str);
	}
}
