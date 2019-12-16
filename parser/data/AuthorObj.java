package data;

import java.util.Vector;

public class AuthorObj {
	public Integer idAuthor = null;
	public String sName = null;
	public String sGivenName = null;
	
	public Vector<AffiliationObj> vAffiliations = new Vector<> ();
	
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append(sGivenName).append(' ').append(sName);
		
		if(vAffiliations.size() > 0) {
			sb.append('\n');
			int count = 1;
			for(final AffiliationObj affil : vAffiliations) {
				sb.append(count++).append(".) ").append(affil.toString()).append('\n');
			}
		}
		
		return sb.toString();
	}
}
