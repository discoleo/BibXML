package sax;

import java.util.TreeMap;

public class AuthorsDict extends TreeMap<Integer, AuthorObj> {
	
	private final AffiliationMap mapAffiliations = new AffiliationMap();
	private final JournalMap mapJournals = new JournalMap();
	
	private int id = -1;
	private AuthorObj author = null;
	
	// ++++ Authors ++++
	
	public void SetID(final int id) {
		this.id = id;
		this.author = new AuthorObj();
		this.put(id, author);
	}
	public void Put(final String sName, final boolean isName) {
		if(id > 0) {
			if(isName) {
				author.sName = sName;
			} else {
				author.sGivenName = sName;
			}
		}
	}
	public void AddAffil(final int idAffil) {
		// add affiliation to Author
		if(id > 0) {
			final AffiliationObj affil = mapAffiliations.get(idAffil);
			if(affil != null) {
				final int sz = author.vAffiliations.size() + 1;
				author.vAffiliations.add("" + sz + ". " + affil.toString());
			}
		}
	}

	public String GetJournal(final int id) {
		return mapJournals.get(id);
	}
	
	// ++++ Build Affiliations Map ++++
	
	public void BuildAffil(final int idAffil) {
		mapAffiliations.SetID(idAffil);
	}
	public void BuildInstitute(final int idInstit) {
		mapAffiliations.SetInstID(idInstit);
	}
	public void BuildInstitute(final String sInstitute) {
		mapAffiliations.PutInstitute(sInstitute);
	}
	// +++ Affiliations
	public void BuildAffilPutInstitute(final int idInstit) {
		mapAffiliations.PutInstitute(idInstit);
	}
	public void BuildAffilDepartment(final String sDepartment) {
		mapAffiliations.PutDepartment(sDepartment);
	}
	
	// ++++ Build Journals Map ++++

	public void BuildJournal(final int idJournal) {
		mapJournals.SetID(idJournal);
	}
	public void BuildJournal(final String sJournalName) {
		mapJournals.PutJournal(sJournalName);
	}
}
