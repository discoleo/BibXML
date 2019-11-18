package data;

import java.util.TreeMap;

public class JournalMap extends TreeMap<Integer, JournalObj> {

	private int id = -1;
	private JournalObj journal = null;
	
	public void SetID(final int idJournal) {
		id = idJournal;
		journal = new JournalObj();
		this.put(id, journal);
	}
	public void PutJournal(final String sJournalName) {
		if(id > 0) {
			journal.sName = sJournalName;
		}
	}
	public void PutType(final String sJournalType) {
		if(id > 0) {
			journal.sType = sJournalType;
		}
	}
	public void PutIssn(final String sJournalIssn) {
		if(id > 0) {
			journal.sISSN = sJournalIssn;
		}
	}
}
