package data;

public class JournalObj {
	public String sName = null;
	public String sType = null;
	public String sISSN = null;
	
	@Override
	public String toString() {
		final String sOut = sName
				+ ((sType != null) ? "\nType: " + sType : "")
				+ ((sISSN != null) ? "\nDOI/ISSN: " + sISSN : "");
		return sOut;
	}
}
