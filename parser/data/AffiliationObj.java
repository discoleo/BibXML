package data;

public class AffiliationObj {
	public String sInstitution = null;
	public String sDepartment = null;
	
	@Override
	public String toString() {
		return sInstitution + "\n " + sDepartment;
	}
}
