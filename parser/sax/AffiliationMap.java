package sax;

import java.util.TreeMap;

public class AffiliationMap extends TreeMap<Integer, AffiliationObj> {

	private final TreeMap<Integer, String> mapInstitutions = new TreeMap<> ();
	
	private int id = -1;
	private AffiliationObj affiliation = null;
	
	// +++ Institutions
	private int idInst = -1;
	
	// +++ Departments +++
	
	public void SetID(final int id) {
		this.id = id;
		this.affiliation = new AffiliationObj();
		this.put(id, affiliation);
	}
	public void PutInstitute(final int idInst) {
		if(id > 0) {
			final String sInstit = mapInstitutions.get(idInst);
			// System.out.println("sInstit: " + sInstit);
			// affiliation = this.get(id);
			affiliation.sInstitution = sInstit;
		}
	}
	public void PutDepartment(final String sDepartment) {
		if(id > 0) {
			affiliation.sDepartment = sDepartment;
		}
	}
	
	// +++ Build Institutions +++

	public void SetInstID(final int idInst) {
		this.idInst = idInst;
	}
	public void PutInstitute(final String sInstitution) {
		if(idInst > 0) {
			mapInstitutions.put(idInst, sInstitution);
		}
	}
}
