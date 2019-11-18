package data;

import java.util.Vector;

public class AuthorObj {
	public String sName = null;
	public String sGivenName = null;
	
	// TODO: proper AffilObj
	public Vector<String> vAffiliations = new Vector<> ();
	
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append(sGivenName).append(' ').append(sName);
		
		if(vAffiliations.size() > 0) {
			sb.append('\n');
			for(final String sAffil : vAffiliations) {
				sb.append(" ").append(sAffil).append('\n');
			}
		}
		
		return sb.toString();
	}
}
