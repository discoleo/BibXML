package sax;

import java.util.Vector;

public class ArticleObj {
	public String sTitle = null;
	protected final Vector<AuthorObj> vAuthors = new Vector<> ();
	public String sJournal = null;
	public int iYear = 0;
	
	// TODO: Date
	
	public void AddAuthor(AuthorObj author) {
		vAuthors.add(author);
	}
	
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("Title: ").append(sTitle).append('\n');
		if(iYear > 0) {
			sb.append("[").append(iYear).append("]\n");
		}
		
		int countA = 1;
		for(final AuthorObj author : vAuthors) {
			sb.append("Author ").append(countA++).append(": ");
			if(author != null) {
				sb.append(author.toString());
			}
		}
		sb.append("Journal: ").append(sJournal);
		
		return sb.toString();
	}
}
