package data;

import java.util.Vector;

public class ArticleObj {
	public String sTitle = null;
	protected final Vector<AuthorObj> vAuthors = new Vector<> ();
	public JournalObj journal = null;
	public int iYear = 0;
	
	// TODO: full Date
	
	public void AddAuthor(final AuthorObj author) {
		vAuthors.add(author);
	}
	
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("Title: ").append(sTitle).append('\n');
		
		// Date
		if(iYear > 0) {
			sb.append("[").append(iYear).append("]\n");
		}
		
		// Authors
		int countA = 1;
		for(final AuthorObj author : vAuthors) {
			sb.append("Author ").append(countA++).append(": ");
			if(author != null) {
				sb.append(author.toString());
			}
		}
		
		// Journal
		sb.append("Journal: ").append(journal.toString());
		
		return sb.toString();
	}
}
