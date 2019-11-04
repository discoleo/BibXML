package sax;

import java.util.TreeMap;

public class JournalMap extends TreeMap<Integer, String> {

	private int id = -1;
	
	public void SetID(final int idJournal) {
		id = idJournal;
	}
	public void PutJournal(final String sJournalName) {
		if(id > 0) {
			this.put(id, sJournalName);
		}
	}
}
