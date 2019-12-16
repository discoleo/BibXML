package data;

public class AffiliationObj {
	public Integer idAffil = null;
	public String sInstitution = null;
	public String sDepartment = null;
	public String sType = null;
	public String sAdress = null;
	
	@Override
	public String toString() {
		return sInstitution + "\n " + sDepartment;
	}
}
